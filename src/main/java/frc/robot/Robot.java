package frc.robot;

import java.util.List;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
// import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.loops.VisionProcessor;
// import frc.robot.commands.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GameMech;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.PressureSensor;
import frc.robot.subsystems.Shifter;
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

    public static DriverStation.Alliance alliance;
    public static String allianceColorVal = "";
    public static String teamSwitchSide = "";
    public static double y_val_target = 0.0;
    public static double z_val_target = 0.0;
    public static double x_val_target = 0.0;
    public static double angle_val_target = 0.0;
    public static boolean isTargetNull = true;

    public static OI oi;
    public static DriveTrain driveTrain;
    public static GameMech gameMech;
    public static Lifter lifter;
    public static Arm arm;
    public static PressureSensor pressureSensor;
    public static Shifter shifter;
    public static CameraServer camera;
    public static UsbCamera front;
    public static UsbCamera back;
    public static VideoSink serverFront, serverBack;
    public static AHRS navX;
    // public static ArduinoInterface arduinoLEDInterface;
    // public static ArduinoInterface arduinoCameraInterface;

    private static JadbConnection m_jadb = null;
    private static List<JadbDevice> m_devices = null;
    private static JadbDevice m_currentDevice = null;
    private static int m_nextLocalHostPort = 3800;

    public static String cameraMode = "back";

    public static VisionServer mVisionServer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init();
        mVisionServer = VisionServer.getInstance();

        driveTrain = new DriveTrain();
        // String[] args = {};
        // mVisionServer.main(args);

        mVisionServer.addVisionUpdateReceiver(VisionProcessor.getInstance());

        gameMech = new GameMech();
        lifter = new Lifter();
        arm = new Arm();
        pressureSensor = new PressureSensor();
        shifter = new Shifter();
        navX = new AHRS(Port.kMXP);
        // arduinoLEDInterface = new ArduinoInterface(7);
        // arduinoCameraInterface = new ArduinoInterface(6);

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.

        // camera = CameraServer.getInstance();
        front = CameraServer.getInstance().startAutomaticCapture("FRONT", 1);
        back = CameraServer.getInstance().startAutomaticCapture("BACK", 0);

        lifter.initDefaultCommand();
        serverFront = CameraServer.getInstance().getServer();
        serverBack = CameraServer.getInstance().getServer();
        front.setConnectionStrategy(edu.wpi.cscore.VideoSource.ConnectionStrategy.kKeepOpen);
        back.setConnectionStrategy(edu.wpi.cscore.VideoSource.ConnectionStrategy.kKeepOpen);
        // serverFront.setSource(front);
        // serverBack.setSource(back);
        oi = new OI();

    }

    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        VisionProcessor processor = (VisionProcessor) mVisionServer.receivers.get(0);
        processor.onLoop(System.currentTimeMillis());
        SmartDashboard.putNumber("NavX Angle: ", navX.getAngle());

        // VisionUpdate update = new VisionUpdate();
        // for (int i = 0; i < update.getTargets().size(); i++) {
        // TargetInfo target = update.getTargets().get(i);
        // System.out.println("Target: " + target.getY() + ", " + target.getZ());
        // }

        // System.out.print(update.getTargets());
        // update.targets; // PROBLEM HERE !!!!!!!!!!!!!!
        // boolean b = (targets_list != null);
        // System.out.println(b);
        /*
         * if (update.targets != null) { //TargetInfo target_Info = targets_list.get(0);
         * TargetInfo target_Info = update.targets.get(0); // Double Y_val =
         * targets_list.get(0).getY(); System.out.println(Y_val); }
         */

    }

    @Override
    public void autonomousInit() {
        if (disabledCommand != null)
            disabledCommand.cancel();
        if (autonomousCommand != null)
            autonomousCommand.start();
    }

    @Override

    public void autonomousPeriodic() {
        // double distanceLeft = RobotMap.ultraLeft.getAverageVoltage() * 300 / 293 * 1000 / 25.4;
        // SmartDashboard.putNumber("Distance from left ultrasonic (inches)", distanceLeft);
        // double distanceRight = RobotMap.ultraRight.getAverageVoltage() * 300 / 293 * 1000 / 25.4;
        // SmartDashboard.putNumber("Distance from right ultrasonic (inches)", distanceRight);
    }

    @Override
    public void teleopInit() {
        Robot.driveTrain.resetEncoders();
        double velocityRight = Robot.driveTrain.getRightEncoderVelocity();
        double velocityLeft = Robot.driveTrain.getLeftEncoderVelocity();
        SmartDashboard.putNumber("velR", velocityRight);
        SmartDashboard.putNumber("velL", velocityLeft);
        SmartDashboard.putNumber("Left Encoder: ", Robot.driveTrain.getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder: ", Robot.driveTrain.getRightEncoderPosition());
        lifter.initDefaultCommand();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        pressureSensor.getPressure();
        Scheduler.getInstance().run();
        double velocityRight = Robot.driveTrain.getRightEncoderVelocity();
        double velocityLeft = Robot.driveTrain.getLeftEncoderVelocity();
        SmartDashboard.putNumber("velR", velocityRight);
        SmartDashboard.putNumber("velL", velocityLeft);

        SmartDashboard.putNumber("Target", RobotMap.targetEncoderValue);

        SmartDashboard.putNumber("NavX", navX.getAngle());
        SmartDashboard.putNumber("Left Encoder: ", Robot.driveTrain.getLeftEncoderPosition());
        /*
         * VisionUpdate update = new VisionUpdate();
         * //System.out.print(update.getTargets()); List<TargetInfo> targets_list =
         * update.getTargets(); // PROBLEM HERE !!!!!!!!!!!!!! if (targets_list != null)
         * { TargetInfo target_Info = targets_list.get(0); Double Y_val =
         * targets_list.get(0).getY(); System.out.println(Y_val); }
         */

        VisionProcessor processor = (VisionProcessor) mVisionServer.receivers.get(0);

        processor.onLoop(System.currentTimeMillis());
        SmartDashboard.putNumber("y_val_target: ", y_val_target);
        SmartDashboard.putNumber("z_val_target: ", z_val_target);
        SmartDashboard.putNumber("x_val_target: ", x_val_target);
        SmartDashboard.putNumber("angle_val_target: ", angle_val_target);
        SmartDashboard.putBoolean("Target found: ", !isTargetNull);
        SmartDashboard.putString("Camera Mode: ", cameraMode);

        SmartDashboard.putNumber("Right Encoder: ", Robot.driveTrain.getRightEncoderPosition());
        SmartDashboard.putBoolean("Switch Front", Robot.driveTrain.isReversed());
        // double distanceLeft = RobotMap.ultraLeft.getAverageVoltage() * 300 / 293 * 1000 / 25.4;
        // SmartDashboard.putNumber("Distance from left ultrasonic (inches)", distanceLeft);
        // double distanceRight = RobotMap.ultraRight.getAverageVoltage() * 300 / 293 * 1000 / 25.4;
        // SmartDashboard.putNumber("Distance from right ultrasonic (inches)", distanceRight);

    }

    @Override
    public void robotPeriodic() { // maybe works Check!!!!!
        SmartDashboard.putNumber("Arm Encoder Value", arm.getArmEncoder());
    }
}
