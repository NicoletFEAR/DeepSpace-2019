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
import frc.robot.subsystems.*;
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

    public static boolean xPressed = false;

    public static final double versionNumber = 3.2;
    
    public static boolean DEBUG_TIME = false;


    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        SmartDashboard.putString("autoName", autoName);

        RobotMap.init();

        driveTrain = new DriveTrain();

        pressureSensor = new PressureSensor();

        shifter = new Shifter();
        shifter.shiftdown();
        
        compressorOAir = new CompressAir();
        player = new Player();
        recorder = new Recorder();

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.

        oi = new OI();

        SmartDashboard.putNumber("Version Number: ", versionNumber);
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
    }
}
