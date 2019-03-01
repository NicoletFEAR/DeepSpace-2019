/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ButtonOrganizer extends InstantCommand {
  /**
   * Add your docs here.
   */
  private int buttonID;
  public ButtonOrganizer(int buttonID) {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.buttonID = buttonID;
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if (Robot.oi.getXbox1().getXButton()) {
      switch (buttonID) {
        case 1:
          Scheduler.getInstance().add(new PlaceCargo(1));
          break;
        case 2:
          Scheduler.getInstance().add(new PlaceCargo(2));
          break;
        case 3:
          Scheduler.getInstance().add(new PlaceCargo(3));
          break;
        case 4:
          Scheduler.getInstance().add(new PlaceHatch(4));
          break;
        case 5:
          Scheduler.getInstance().add(new PlaceHatch(5));
          break;
        case 6:
          Scheduler.getInstance().add(new PlaceHatch(6));
          break;
        case 7:
          Scheduler.getInstance().add(new PlaceCargo(7));
          break;
        case 8:
          Scheduler.getInstance().add(new LoadingStationHatch());
          break;
      }
    } else {
      switch (buttonID) {
        case 1:
          Scheduler.getInstance().add(new MoveToLevel(1));
          break;
        case 2:
          Scheduler.getInstance().add(new MoveToLevel(2));
          break;
        case 3:
          Scheduler.getInstance().add(new MoveToLevel(3));
          break;
        case 4:
          Scheduler.getInstance().add(new MoveToLevel(4));
          break;
        case 5:
          Scheduler.getInstance().add(new MoveToLevel(5));
          break;
        case 6:
          Scheduler.getInstance().add(new MoveToLevel(6));
          break;
        case 7:
          Scheduler.getInstance().add(new MoveToLevel(7));
          break;
        case 8:
          Scheduler.getInstance().add(new MoveToLevel(10));
          break;
      }
    }
  }

}
