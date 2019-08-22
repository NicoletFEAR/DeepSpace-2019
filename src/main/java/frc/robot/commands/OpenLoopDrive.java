/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID;

/**
 *  Command handling simple driving for the robot
 */
public class OpenLoopDrive extends Command {

    double forwardValue = 0;
    double reverseValue = 0;
    double turnAmount = 0;
    double robotOutput = 0;

    public OpenLoopDrive() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.player.endPlaying();
    }

    @Override
    protected void execute() {    	
    	forwardValue = Robot.oi.getXbox1().getTriggerAxis(GenericHID.Hand.kRight);   	
    	reverseValue = Robot.oi.getXbox1().getTriggerAxis(GenericHID.Hand.kLeft);
    	turnAmount = Robot.oi.getXbox1().getX(GenericHID.Hand.kLeft);
    
    	//Calculate an Arcade drive speed by taking forward speed and subtracting it by reverse speed
    	//So Cool! :D
        robotOutput = forwardValue - reverseValue;
        if (!Robot.player.playing) {
        Robot.driveTrain.RacingDrive(robotOutput, turnAmount * RobotMap.TURN_SCALING);
        }
     }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}
