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
 * Closes hatch latch, holds hatch in place
 */
public class GameMechClose extends InstantCommand {
  public GameMechClose() {
    super();
    requires(Robot.gameMech);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.gameMech.pull();
  }
}
