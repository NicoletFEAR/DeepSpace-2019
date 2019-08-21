package frc.robot;

import java.util.List;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.loops.VisionProcessor;
import frc.robot.subsystems.*;
import frc.robot.vision.VisionServer;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
    Command autonomousCommand;
    Command disabledCommand;
    Command nothingTest;

    public static double new_m_period = 0.02;

    public static String autoName = "testAuto";

    public static boolean isAutonomous = false;

    public static DriverStation.Alliance alliance;
    public static String allianceColorVal = "";
    public static String teamSwitchSide = "";
    public static double y_val_target = 0.0;
    public static double z_val_target = 0.0;
    public static double x_val_target = 0.0;
    public static double angle_val_target = 0.0;
    public static boolean isTargetNull = true;
    public static boolean doneArc = false;
    public static String talonMode = "brake";
    public static String hatchMechState = "grab";

    public static OI oi;
    public static DriveTrain driveTrain;
    public static PressureSensor pressureSensor;
    public static Shifter shifter;
    public static UsbCamera front;
    public static UsbCamera back;
    public static CompressAir compressorOAir;
    public static Player player;
    public static Recorder recorder;

    public static Notifier recorderNotifier = null;
    public static Notifier playerNotifier = null;
    // public static ArduinoInterface arduinoLEDInterface;
    // public static ArduinoInterface arduinoCameraInterface;

    public boolean compressorRunning = true;

    private static JadbConnection m_jadb = null;
    private static List<JadbDevice> m_devices = null;
    private static JadbDevice m_currentDevice = null;
    private static int m_nextLocalHostPort = 3800;

    public static String cameraMode = "back";

    public static VisionServer mVisionServer;
    public static VisionProcessor processor;
    public static boolean xPressed = false;

    public static final double versionNumber = 3.2;
    
    public static boolean DEBUG_TIME = false;


    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        // 
        this.m_period = 0.02;        
        System.out.println(" m_period is ");
        System.out.println(getPeriod());


        SmartDashboard.putString("autoName", autoName);

        RobotMap.init();
        mVisionServer = VisionServer.getInstance();
        mVisionServer.frontCamera();

        mVisionServer.addVisionUpdateReceiver(VisionProcessor.getInstance());

        driveTrain = new DriveTrain();

        pressureSensor = new PressureSensor();

        shifter = new Shifter();
        shifter.shiftdown();
        
        compressorOAir = new CompressAir();

        

        

        front = CameraServer.getInstance().startAutomaticCapture("FRONT", 0);
        front.setFPS(20);
        back = CameraServer.getInstance().startAutomaticCapture("BACK", 1);
        back.setFPS(20);

        // arduinoLEDInterface = new ArduinoInterface(7);
        // arduinoCameraInterface = new ArduinoInterface(6);

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.

        oi = new OI();

        SmartDashboard.putNumber("Version Number: ", versionNumber);
        SmartDashboard.putBoolean("Active: ", false);
    }

    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
        isAutonomous = false;
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        mVisionServer.backCamera();
        if (disabledCommand != null)
            disabledCommand.cancel();

        autonomousCommand = null;

        if (autonomousCommand != null)
            autonomousCommand.start();
        teleopInit();

        isAutonomous = true;

        shifter.shiftdown();

        Robot.driveTrain.resetEncoders();

        SmartDashboard.putString("autoName", autoName);

        //Playback playback = new Playback();
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void teleopInit() {   
        isAutonomous = false;
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void robotPeriodic() { // Always runs, good for printing - be carefull not to slow down too much

        // ADD IF STATEMENT HERE???
        processor = (VisionProcessor) mVisionServer.receivers.get(0);
        processor.onLoop(System.currentTimeMillis());

        if (true) {
            recorderNotifier.startPeriodic(0.03);     
        }
       

        /*
        if (new_m_period != this.m_period) {
            Robot.
            TimedRobot.getInstance().setPeriod(0.02);
            setPeriod(new_m_period);
            System.out.println(" m_period is ");
            System.out.println(this.m_period);
        }
        */

        /*
        SmartDashboard.putBoolean("Target found: ", !isTargetNull);
        SmartDashboard.putString("Camera Mode: ", cameraMode);
        SmartDashboard.putBoolean("Switch Front: ", Robot.driveTrain.isReversed());
        SmartDashboard.putNumber("Pressure: ", Robot.pressureSensor.getPressure());
        SmartDashboard.putString("Talon Mode: ", talonMode);
        SmartDashboard.putString("Hatch Mech State: ", hatchMechState);

        // PATHFINDER
        SmartDashboard.putBoolean("isOnPath", shifter.isOnPath);
        SmartDashboard.putNumber("R_Enc", Robot.driveTrain.getRightEncoderPosition());
        SmartDashboard.putNumber("L_Enc", Robot.driveTrain.getLeftEncoderPosition());
        */

        if (DEBUG_TIME) {
            

            SmartDashboard.putNumber("y_val_target: ", y_val_target);
            SmartDashboard.putNumber("z_val_target: ", z_val_target);
            SmartDashboard.putNumber("x_val_target: ", x_val_target);
            SmartDashboard.putNumber("angle_val_target: ", angle_val_target);

            // SmartDashboard.putNumber("velR: ", Robot.driveTrain.getRightEncoderVelocity());
            // SmartDashboard.putNumber("velL: ", Robot.driveTrain.getLeftEncoderVelocity());

            SmartDashboard.putNumber("Average Velocity: ", Robot.driveTrain.averageVelocity);

            SmartDashboard.putNumber("Right Encoder: ", Robot.driveTrain.getRightEncoderPosition());
            SmartDashboard.putNumber("Left Encoder: ", Robot.driveTrain.getLeftEncoderPosition());

            
            SmartDashboard.putNumber("Vol_left1: ", RobotMap.left1.getMotorOutputVoltage());
            SmartDashboard.putNumber("Vol_right1: ", RobotMap.right1.getMotorOutputVoltage());
                        

        }
    }
}
