/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ToggleCompressor extends InstantCommand {
  public ToggleCompressor() {
    requires(Robot.compressorOAir);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.compressorOAir.toggleCompressor();
  }
}