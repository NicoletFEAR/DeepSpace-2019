/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArcDrive2 extends InstantCommand {
  double x;
  double y;
  double distOffset;
  int level;

  public ArcDrive2() {
    // Uses input from the Android vision system to drive to targets and place game
    // pieces
    x = Robot.x_val_target;
    y = Robot.y_val_target;
    distOffset = RobotMap.distOffset;

    requires(Robot.driveTrain);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    RobotMap.setTalonMode("coast");
    setDriving();
    setHeight();
  }

  public void setDriving() {
    x = Robot.x_val_target;
    y = Robot.y_val_target;
    if (((y < (distOffset + RobotMap.adjustmentAllowance)) && (Math.abs(x) < RobotMap.adjustmentAllowance)) || Robot.isTargetNull) {
      Robot.doneArc = true;
    } else {
      Robot.doneArc = false;
    }

    SmartDashboard.putBoolean("DoneArc", Robot.doneArc);

    double output = (y - distOffset) * RobotMap.y_multiplier;
    if (!(y < (distOffset + RobotMap.adjustmentAllowance))) {
      output += .15;
    }
    double turn = (x * RobotMap.x_multiplier) / (1 + y * .01);

    if (turn > RobotMap.xMaxTurnSpeed)
      turn = RobotMap.xMaxTurnSpeed;
    else if (turn < -RobotMap.xMaxTurnSpeed)
      turn = -RobotMap.xMaxTurnSpeed;

    if (Robot.doneArc) {
      Robot.driveTrain.stop();
    } else {
      Robot.driveTrain.RacingDrive(output, turn);
    }
  }

  public void setHeight() {
    if (y < distOffset + 12) {
    } else {
    }
  }
}
