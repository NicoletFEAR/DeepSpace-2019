/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Switches arm between manual and auto PID mode
 */
public class ToggleArmPID extends InstantCommand {
  public ToggleArmPID() {
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.arm.armIsManual = !Robot.arm.armIsManual;
  }
}
