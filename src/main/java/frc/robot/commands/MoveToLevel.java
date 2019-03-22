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
 * Add your docs here.
 */
public class MoveToLevel extends InstantCommand {
  /**
   * Add your docs here.
   */
  int level;

  public MoveToLevel(int level) {
    super();
    this.level = level;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if (level == 1) {
      RobotMap.targetEncoderValue = RobotMap.CargoLoadingStation;
    } else if (level == 2) {
      RobotMap.targetEncoderValue = RobotMap.CargoShipDropPoint;
    } else if (level == 3) {
      RobotMap.targetEncoderValue = RobotMap.HatchHeight;
    } else if (level == 4) {
      RobotMap.targetEncoderValue = RobotMap.StraightUp;
    } else if (level == 5) {
      RobotMap.targetEncoderValue = RobotMap.BackToClimb;
    }
  }

}
