/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Toggles speed controllers between coast and brake mode
 */
public class ToggleTalonMode extends InstantCommand {
  public ToggleTalonMode() {
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if (Robot.talonMode.equals("coast"))
    {
      
      RobotMap.setTalonMode("brake");
    } else {
      RobotMap.setTalonMode("coast");
    }
  }
}