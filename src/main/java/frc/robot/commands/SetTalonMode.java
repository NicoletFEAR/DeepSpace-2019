/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.RobotMap;

/**
 * Sets all drive talons to coast or brake mode
 */
public class SetTalonMode extends InstantCommand {
  private String desiredMode;

  public SetTalonMode(String mode) {
    this.desiredMode = mode;
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    RobotMap.setTalonMode(desiredMode);
  }

}
