package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shift extends Command {

    public Shift() {
        // Use requires() here to declare subsystem dependencies
       requires(Robot.shifter);
       this.setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.pressureSensor.getPressure()>40 || Robot.shifter.getNoMatterWhat()==true){
    		Robot.shifter.shift();}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}