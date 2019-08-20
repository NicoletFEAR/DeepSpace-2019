/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Playback extends Command {

  double thisLine[] = new double[28];
  int currentLine = 0;
  public boolean playing = false;

  Scanner scanner;
  long startTime;

  boolean onTime = true;
  double nextDouble;
  
  Command switchsFront;

  public Playback() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveTrain);
  }

  public Playback(String autoName) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    // get the file, quit command if no file
    try { setupScanner(); } catch (FileNotFoundException e) {
      System.out.println("could not play, no file found");
      end(); // if there is no file, stop playing
    }

    Robot.shifter.isPlayingShift = true;
    playing = true;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (!Robot.isAutonomous || scanner == null) { end(); } // stop if robot moves into teleop

    // read the line of the file using scanner
    loadLine();

    // play back the line of that file
    playLine();

    currentLine++;

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (scanner.hasNextDouble() && currentLine >= 2) {
      return false;
    } else {
    return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // stop all the motors
    Robot.driveTrain.stop();
    // INSERT STOPS HERE

    if (scanner != null){
			scanner.close();
    }
    playing = false;
    Robot.shifter.isPlayingShift = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  // insert new methods here: **************************************************************

  void setupScanner() throws FileNotFoundException {
    //create a scanner to read the file created during BTMacroRecord
		//scanner is able to read out the doubles recorded into recordedAuto.csv (as of 2015)
		scanner = new Scanner(new File(RobotMap.autoFileLocName));
		
		//let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
		scanner.useDelimiter(",|\\n");
		
		//lets set start time to the current time you begin autonomous
		startTime = System.currentTimeMillis();
  }

  void loadLine() {
    for (int y = 0; y < 28; y++) {

      if (!scanner.hasNextDouble()) { end(); } //if there is nothing more to read, stop

      try {
        thisLine[y] = scanner.nextDouble();
      } catch (Exception e) {
        System.out.println("couldn't get next double");
        end(); // though the if statement above should prevent this from ever happening, this is here just in case
      }
    }
  }

  void playLine() {
    // use thisLine[] to change motor and piston outputs (or whatever you want to do with recording)
    // refer to Record.java to figure out which position in the array corresponds to what:
    /* current layout:
    XBOX1 (0-13)
      joysticks 0-3
        0 = left x, 1 = left y
        2 = right x, 3 = right y
      4 = left trigger
      5 = right trigger
      6 = left bumper
      7 = right bumper
      coloured buttons 8-11
        8 = x
        9 = y
        10 = a
        11 = b
      12 = start
      13 = back
    */

    // DRIVE:
    Robot.driveTrain.RacingDrive(thisLine[5]-thisLine[4], thisLine[0] * RobotMap.TURN_SCALING);
    if (thisLine[8] == 1.0) { // x button on click
      switchsFront = new SwitchFront();
      switchsFront.start();
    }

    if (thisLine[12] != 0.0) {
      Robot.shifter.isLowGearButton = true;
    } else { Robot.shifter.isLowGearButton = false; }

    if (thisLine[10] != 0.0) {
      Robot.shifter.isHighGearButton = true;
    } else { Robot.shifter.isHighGearButton = false; }



  }

}
