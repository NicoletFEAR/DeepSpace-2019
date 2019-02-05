package frc.robot.vision;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.CrashTrackingRunnable;
import frc.robot.vision.messages.HeartbeatMessage;
import frc.robot.vision.messages.OffWireMessage;
import frc.robot.vision.messages.VisionMessage;
import frc.robot.vision.messages.SetCameraModeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This controls all vision actions, including vision updates, capture, and interfacing with the Android phone with
 * Android Debug Bridge. It also stores all VisionUpdates (from the Android phone) and contains methods to add to/prune
 * the VisionUpdate list. Much like the subsystems, outside methods get the VisionServer instance (there is only one
 * VisionServer) instead of creating new VisionServer instances.
 * 
 * @see VisionUpdate.java
 */

public class VisionServer extends CrashTrackingRunnable {

    private static VisionServer a_instance = null;
    private static VisionServer b_instance = null;
    private ServerSocket m_server_socket;
    private boolean m_running = true;
    private int local_port;
    private int remote_port;
    public ArrayList<VisionUpdateReceiver> receivers = new ArrayList<>();
    AdbBridge adb = new AdbBridge();
    double lastMessageReceivedTime = 0;
    private boolean m_use_java_time = false;

    private ArrayList<ServerThread> serverThreads = new ArrayList<>();
    private volatile boolean mWantsAppRestart = false;

    public static VisionServer getLeftInstance() {
        if (a_instance == null) {
            a_instance = new VisionServer(8254);
        }
        return a_instance;
    }

    public static VisionServer getRightInstance() {
        if (b_instance == null) {
            b_instance = new VisionServer(8253);
        }
        return b_instance;
    }


    private boolean mIsConnect = false;

    public boolean isConnected() {
        return mIsConnect;
    }

    public void requestAppRestart() {
        mWantsAppRestart = true;
    }

    protected class ServerThread extends CrashTrackingRunnable {
        private Socket m_socket;

        public ServerThread(Socket socket) {
            m_socket = socket;
        }

        public void send(VisionMessage message) {
            String toSend = message.toJson() + "\n";
            if (m_socket != null && m_socket.isConnected()) {
                try {
                    OutputStream os = m_socket.getOutputStream();
                    os.write(toSend.getBytes());
                } catch (IOException e) {
                    System.err.println("VisionServer: Could not send data to socket");
                }
            }
        }

        public void handleMessage(VisionMessage message, double timestamp) {
            if ("targets".equals(message.getType())) {
                VisionUpdate update = VisionUpdate.generateFromJsonString(timestamp, message.getMessage());
                receivers.removeAll(Collections.singleton(null));
                if (update.isValid()) {
                    for (VisionUpdateReceiver receiver : receivers) {
                        receiver.gotUpdate(update);
                    }
                }
            }
            if ("heartbeat".equals(message.getType())) {
                send(HeartbeatMessage.getInstance());
            }
            if ("camera_mode".equals(message.getType()) && "back".equals(message.getMessage())){
                send(SetCameraModeMessage.getBackCameraMessage());
            } else if ("camera_mode".equals(message.getType()) && "front".equals(message.getMessage())){
                send(SetCameraModeMessage.getFrontCameraMessage());
            }
        }

        public boolean isAlive() {
            return m_socket != null && m_socket.isConnected() && !m_socket.isClosed();
        }

        @Override
        public void runCrashTracked() {
            if (m_socket == null) {
                return;
            }
            try {
                InputStream is = m_socket.getInputStream();
                byte[] buffer = new byte[2048];
                int read;
                while (m_socket.isConnected() && (read = is.read(buffer)) != -1) {
                    double timestamp = getTimestamp();
                    lastMessageReceivedTime = timestamp;
                    String messageRaw = new String(buffer, 0, read);
                    String[] messages = messageRaw.split("\n");

                    for (String message : messages) {
                        OffWireMessage parsedMessage = new OffWireMessage(message);
                        if (parsedMessage.isValid()) {
                            handleMessage(parsedMessage, timestamp);
                        }
                    }
                }
                System.out.println("Socket disconnected");
            } catch (IOException e) {
                System.err.println("Could not talk to socket");
            }
            if (m_socket != null) {
                try {
                    m_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Instantializes the VisionServer and connects to ADB via the specified port.
     * 
     * @param Port
     */
    private VisionServer(int port) {
        try {
            adb = new AdbBridge();
            local_port = port;
            remote_port = port;
            if (local_port == 8253){
                remote_port = 8254;
            }
            m_server_socket = new ServerSocket(local_port);
            adb.start();
            adb.reversePortForward(remote_port, local_port);
            try {
                String useJavaTime = System.getenv("USE_JAVA_TIME");
                m_use_java_time = "true".equals(useJavaTime);
            } catch (NullPointerException e) {
                m_use_java_time = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
        new Thread(new AppMaintainanceThread()).start();
    }

    public void restartAdb() {
        adb.restartAdb();
        adb.reversePortForward(remote_port, local_port);
    }

    /**
     * If a VisionUpdate object (i.e. a target) is not in the list, add it.
     * 
     * @see VisionUpdate
     */
    public void addVisionUpdateReceiver(VisionUpdateReceiver receiver) {
        System.out.print("start of addVisionUpdateReceiver");
        if (!receivers.contains(receiver)) {
            receivers.add(receiver);
        }
        System.out.print("end of addVisionUpdateReceiver");
    }

    public void removeVisionUpdateReceiver(VisionUpdateReceiver receiver) {
        if (receivers.contains(receiver)) {
            receivers.remove(receiver);
        }
    }

    public void frontCamera(){
        for (int counter = 0; counter < serverThreads.size(); counter++) {
            ServerThread s = serverThreads.get(counter);
            s.send(SetCameraModeMessage.getFrontCameraMessage());
        }
    }

    public void backCamera(){
        for (int counter = 0; counter < serverThreads.size(); counter++) {
            ServerThread s = serverThreads.get(counter);
            s.send(SetCameraModeMessage.getBackCameraMessage());
        }
    }

    @Override
    public void runCrashTracked() {
        while (m_running) {
            try {
                Socket p = m_server_socket.accept();
                ServerThread s = new ServerThread(p);
                new Thread(s).start();
                serverThreads.add(s);
            } catch (IOException e) {
                System.err.println("Issue accepting socket connection!");
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class AppMaintainanceThread extends CrashTrackingRunnable {
        @Override
        public void runCrashTracked() {
            while (true) {
                if (getTimestamp() - lastMessageReceivedTime > .1) {
                    // camera disconnected
                    adb.reversePortForward(remote_port, local_port);
                    mIsConnect = false;
                } else {
                    mIsConnect = true;
                }
                if (mWantsAppRestart) {
                    adb.restartApp();
                    mWantsAppRestart = false;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private double getTimestamp() {
        if (m_use_java_time) {
            return System.currentTimeMillis();
        } else {
            return Timer.getFPGATimestamp();
        }
    }
}
