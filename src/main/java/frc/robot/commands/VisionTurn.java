/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;



public class VisionTurn extends Command {
  boolean isFin;
  public VisionTurn() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);

    

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isFin = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (Robot.isTargetNull) {
      Robot.driveTrain.stop();
    } else {
      Robot.driveTrain.ArcadeDrive(-0.5*(0.7-Robot.z_val_target),2*(Robot.y_val_target));
    }

    if (Robot.z_val_target > 0.28) {
      isFin = true;
    } else {
      isFin = false;
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    
    if (isFin) {
      return true;
    } else {
      return false;
    }
    
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    isFin = true;
  }
}
