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

/**
 * Add your docs here.
 */
public class Playback extends InstantCommand {
  /**
   * Add your docs here.
   */
  public Playback() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {

    // Robot.playerNotifier.startPeriodic(0.03);

    Robot.playerNotifier = new Notifier(() -> {
      // get the file, quit command if no file
      try { Robot.player.setupScanner(); } catch (Exception e) {
        System.out.println("could not play, no file found");
        Robot.player.endPlaying();
      }
    });

    Robot.playerNotifier.startSingle(0.0);

    while (!Robot.player.playing) {
    }
    
    Robot.shifter.isPlayingShift = true;

    Robot.playerNotifier = new Notifier(() -> {
      // get the file, quit command if no file
      if (Robot.player.scanner == null) { Robot.player.endPlaying(); } // stop if 

      // read the line of the file using scanner
      Robot.player.loadLine();

      // play back the line of that file
      Robot.player.playLine();
    });

    Robot.playerNotifier.startPeriodic(0.03);
    

  }

}
