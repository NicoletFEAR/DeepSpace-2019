// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;


/**
 *
 */
public class Levels extends Command {

    
    public Levels() {
        // Use requires() here to declare subsystem dependencies
       requires(Robot.arm);
       this.setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.arm.initDefaultCommand();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	  
        RobotMap.targetEncoderValue = RobotMap.targetEncoderValue+(int)(100*Robot.oi.getXbox2().getX(Hand.kLeft));

        // if(RobotMap.targetEncoderValue<RobotMap.ARM_MAX_TICK_VAL || RobotMap.targetEncoderValue>RobotMap.ARM_MIN_TICK_VAL){
        //     if(RobotMap.targetEncoderValue>(RobotMap.ARM_MAX_TICK_VAL+RobotMap.ARM_MIN_TICK_VAL)/2) RobotMap.targetEncoderValue = (int)RobotMap.ARM_MAX_TICK_VAL;
        //     else RobotMap.targetEncoderValue = (int)RobotMap.ARM_MIN_TICK_VAL;
        // }
        if(Robot.driveTrain.isReversed()) Robot.arm.rotateToPosition(-RobotMap.targetEncoderValue);
        else Robot.arm.rotateToPosition(RobotMap.targetEncoderValue);        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

