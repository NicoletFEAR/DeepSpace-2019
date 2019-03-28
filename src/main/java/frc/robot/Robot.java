package frc.robot;

import java.util.List;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.loops.VisionProcessor;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.CompressAir;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GameMech;
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
    public static boolean doneArc = false;
    public static String talonMode = "brake";
    public static String hatchMechState = "grab";

    public static OI oi;
    public static DriveTrain driveTrain;
    public static GameMech gameMech;
    public static Arm arm;
    public static PressureSensor pressureSensor;
    public static Shifter shifter;
    public static UsbCamera front;
    public static UsbCamera back;
    public static CompressAir compressorOAir;
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

    public static final double versionNumber = 1.4;
    
    public static boolean DEBUG_TIME = false;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init();
        mVisionServer = VisionServer.getInstance();
        mVisionServer.frontCamera();

        mVisionServer.addVisionUpdateReceiver(VisionProcessor.getInstance());

        driveTrain = new DriveTrain();

        gameMech = new GameMech();
        gameMech.pull();

        arm = new Arm();

        pressureSensor = new PressureSensor();

        shifter = new Shifter();
        shifter.shiftdown();
        
        compressorOAir = new CompressAir();

        front = CameraServer.getInstance().startAutomaticCapture("FRONT", 0);
        back = CameraServer.getInstance().startAutomaticCapture("BACK", 1);

        // arduinoLEDInterface = new ArduinoInterface(7);
        // arduinoCameraInterface = new ArduinoInterface(6);

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.

        oi = new OI();

        RobotMap.armMotor1.setSelectedSensorPosition(0);
        RobotMap.armMotor2.setSelectedSensorPosition(0);
        RobotMap.targetEncoderValue = 0;
        RobotMap.offset = 0;
        RobotMap.ARM_MAX_TICK_VAL = 2750;
        RobotMap.ARM_MIN_TICK_VAL = -2750;

        SmartDashboard.putNumber("Version Number: ", versionNumber);
        SmartDashboard.putBoolean("Active: ", false);
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
    }

    @Override
    public void autonomousInit() {
        mVisionServer.backCamera();
        if (disabledCommand != null)
            disabledCommand.cancel();
        if (autonomousCommand != null)
            autonomousCommand.start();
        teleopInit();
        

        shifter.shiftdown();

        Robot.driveTrain.resetEncoders();
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void teleopInit() {   
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void robotPeriodic() { // Always runs, good for printing
        processor = (VisionProcessor) mVisionServer.receivers.get(0);
        processor.onLoop(System.currentTimeMillis());
        
        SmartDashboard.putBoolean("Target found: ", !isTargetNull);
        SmartDashboard.putString("Camera Mode: ", cameraMode);
        SmartDashboard.putBoolean("Switch Front: ", Robot.driveTrain.isReversed());
        SmartDashboard.putNumber("Pressure: ", Robot.pressureSensor.getPressure());
        SmartDashboard.putString("Talon Mode: ", talonMode);
        SmartDashboard.putString("Hatch Mech State: ", hatchMechState);

        if (DEBUG_TIME) {
            SmartDashboard.putNumber("Arm1 Encoder Value: ", arm.getArm1Encoder());
            SmartDashboard.putNumber("Arm2 Encoder Value: ", arm.getArm2Encoder());

            SmartDashboard.putNumber("y_val_target: ", y_val_target);
            SmartDashboard.putNumber("z_val_target: ", z_val_target);
            SmartDashboard.putNumber("x_val_target: ", x_val_target);
            SmartDashboard.putNumber("angle_val_target: ", angle_val_target);

            // SmartDashboard.putNumber("velR: ", Robot.driveTrain.getRightEncoderVelocity());
            // SmartDashboard.putNumber("velL: ", Robot.driveTrain.getLeftEncoderVelocity());

            SmartDashboard.putNumber("Average Velocity: ", Robot.driveTrain.averageVelocity);

            SmartDashboard.putNumber("Right Encoder: ", Robot.driveTrain.getRightEncoderPosition());
            SmartDashboard.putNumber("Left Encoder: ", Robot.driveTrain.getLeftEncoderPosition());

            SmartDashboard.putNumber("ArmySpeedyBoi: ", arm.getSpeed());
            
            SmartDashboard.putNumber("Vol_armMotor1: ", RobotMap.armMotor1.getMotorOutputVoltage());
            SmartDashboard.putNumber("Vol_armMotor2: ", RobotMap.armMotor2.getMotorOutputVoltage());
            SmartDashboard.putNumber("Vol_left1: ", RobotMap.left1.getMotorOutputVoltage());
            SmartDashboard.putNumber("Vol_right1: ", RobotMap.right1.getMotorOutputVoltage());
            SmartDashboard.putBoolean("armIsManual: ",Robot.arm.armIsManual);
            SmartDashboard.putNumber("LastArmTarget: ", Robot.arm.getLastEncoderTarget());

            SmartDashboard.putNumber("Target Arm Encoder", RobotMap.targetEncoderValue);
            SmartDashboard.putNumber("Arm Offset", RobotMap.offset);
        }
    }
}
