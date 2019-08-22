/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Record extends InstantCommand {
  /**
   * Add your docs here.
   */
  public Record() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {

    Robot.recorderNotifier = new Notifier(() -> {

      // startTime = System.currentTimeMillis();
      Robot.recorder.currentLine = 0;
      try {
        Robot.recorder.makeFileWriter();
      } catch (IOException e) {

        try {
          Robot.recorder.endRecording();
        } catch (Exception exc) {
          System.out.println("exception endRecording");
        }

        System.out.println("inside Record IOException");
      }

      Robot.recorder.isRecording = true;
      // System.out.println("inside Record init");

    });

    Robot.recorderNotifier.startSingle(0.005);

    Robot.recorderNotifier = new Notifier(() -> {

      try {
        Robot.recorder.writeLine();
      } catch (IOException e) {
        System.out.println("IOException WriteLine");
      }
      Robot.recorder.currentLine++;

      if (Robot.oi.getXbox1().getBumper(Hand.kRight) && Robot.recorder.currentLine >= 50) { // if you pressed the record
                                                                                            // button again (after 1s
                                                                                            // delay so it doesn't
                                                                                            // instantly end)
        System.out.println("finished!");
        try {
          Robot.recorder.endRecording();
        } catch (Exception excep) {
          System.out.println("exceptio endRecording()");
        }
      }

    });

    Robot.recorderNotifier.startPeriodic(0.03);

  }

}
