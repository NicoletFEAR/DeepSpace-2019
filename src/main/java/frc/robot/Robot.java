// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    Command disabledCommand;
    
	public static DriverStation.Alliance alliance;
	public static String allianceColorVal = "";
	public static String teamSwitchSide = "";

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Piston piston;
    public static Lifter lifter;
//    public static ArduinoInterface arduinoLEDInterface;
//    public static ArduinoInterface arduinoCameraInterface;
    


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init();

        driveTrain = new DriveTrain();
        

        //arduinoLEDInterface = new ArduinoInterface(7);
        //arduinoCameraInterface = new ArduinoInterface(6);
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        
        
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
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
        if (disabledCommand != null) disabledCommand.cancel();
        if (autonomousCommand != null) autonomousCommand.start();
    }
    
    @Override
    public void teleopInit()
    {
        Robot.driveTrain.resetEncoders();
        double velocityRight = Robot.driveTrain.getRightEncoderVelocity();
        double velocityLeft = Robot.driveTrain.getLeftEncoderVelocity();
		SmartDashboard.putNumber("velR", velocityRight);
		SmartDashboard.putNumber("velL", velocityLeft);
		SmartDashboard.putNumber("Left Encoder: ", Robot.driveTrain.getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder: ", Robot.driveTrain.getRightEncoderPosition());
       
    }
    
    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        double velocityRight = Robot.driveTrain.getRightEncoderVelocity();
        double velocityLeft = Robot.driveTrain.getLeftEncoderVelocity();
		SmartDashboard.putNumber("velR", velocityRight);
		SmartDashboard.putNumber("velL", velocityLeft);
		
		SmartDashboard.putNumber("Left Encoder: ", Robot.driveTrain.getLeftEncoderPosition());
		SmartDashboard.putNumber("Right Encoder: ", Robot.driveTrain.getRightEncoderPosition());
    }
}
