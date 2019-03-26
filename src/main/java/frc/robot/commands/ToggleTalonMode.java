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
      RobotMap.left1.setNeutralMode(NeutralMode.Brake);
      RobotMap.right1.setNeutralMode(NeutralMode.Brake);
      RobotMap.left2.setNeutralMode(NeutralMode.Brake);
      RobotMap.right2.setNeutralMode(NeutralMode.Brake);
      RobotMap.left3.setNeutralMode(NeutralMode.Brake);
      RobotMap.right3.setNeutralMode(NeutralMode.Brake);
      Robot.talonMode = "brake";
    } else {
      RobotMap.left1.setNeutralMode(NeutralMode.Coast);
      RobotMap.right1.setNeutralMode(NeutralMode.Coast);
      RobotMap.left2.setNeutralMode(NeutralMode.Coast);
      RobotMap.right2.setNeutralMode(NeutralMode.Coast);
      RobotMap.left3.setNeutralMode(NeutralMode.Coast);
      RobotMap.right3.setNeutralMode(NeutralMode.Coast);
      Robot.talonMode = "coast";
    }
  }
}
