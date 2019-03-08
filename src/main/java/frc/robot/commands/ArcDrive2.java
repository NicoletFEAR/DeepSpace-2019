/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArcDrive2 extends InstantCommand {

  double x;
  double y;
  double distOffset;

  int level;

  public ArcDrive2(int level) {

    super();

    // Uses input from the Android vision system to drive to targets and place game
    // pieces
    this.x = Robot.x_val_target;
    this.y = Robot.y_val_target;
    this.distOffset = RobotMap.getDistanceOffset(level);

    this.level = level;

    requires(Robot.driveTrain);

  }

  // Called once when the command executes
  @Override
  protected void initialize() {

    setDriving();

    setHeight();

    if (Robot.doneArc) {
      operateMech();
    }

  }

  public void setDriving() {

    if (this.y < distOffset) {
      Robot.doneArc = true;
    } else {
      Robot.doneArc = false;
    }

    if (Robot.doneArc) {
      // stop
    } else {
      Robot.driveTrain.RacingDrive(this.y * RobotMap.y_multiplier, this.x * RobotMap.x_multiplier);
    }
  }

  public void setHeight() {

    if (this.y < distOffset + 1) {
      new MoveToLevel(level);
    } else {
      new MoveToLevel(8);
    }

  }

  public void operateMech() {
    if (level == 1 || level == 2 || level == 3 || level == 7) { // if we want to shoot a cargo
      new FlyWheelSetSpeed(0.3);
    } else if (level == 4 || level == 5 || level == 6) { // we want to place a hatch
      new GameMechClose();
    } else if (level == 12) { // we want to get from the loading station
      new GameMechOpen();
    }
  }

}
