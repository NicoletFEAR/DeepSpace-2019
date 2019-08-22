/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.File;
import java.util.Scanner;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.SwitchFront;

/**
 * Add your docs here.
 */
public class Player extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  double thisLine[] = new double[14];
  // double thisLine[] = new double[28]; // two controllers

  int currentLine = 0;
  public boolean playing = false;

  public Scanner scanner = null;
  long startTime;

  boolean onTime = true;
  double nextDouble;
  
  Command switchsFront;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  // CUSTOM METHODS:

  public void setupScanner() throws Exception { //FileNotFoundException
    //create a scanner to read the file created during BTMacroRecord
		//scanner is able to read out the doubles recorded into recordedAuto.csv (as of 2015)
		scanner = new Scanner(new File(RobotMap.autoFileLocName + Robot.autoName + ".csv"));
		
		//let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
    scanner.useDelimiter(",|\\n");
    
    playing = true;
		
		//lets set start time to the current time you begin autonomous
		//startTime = System.currentTimeMillis();
  }

  public void loadLine() throws IllegalStateException{
    // for (int y = 0; y < 28; y++) {
    for (int y = 0; y < 14; y++) {

      try {
        if (!scanner.hasNextDouble()) {
           endPlaying(); 
          } //if there is nothing more to read, stop
      } catch (IllegalStateException cept) {
        System.out.println(cept);
      }

      try {
        thisLine[y] = scanner.nextDouble();
      } catch (Exception e) {
        System.out.println("couldn't get next double");
        endPlaying(); // though the if statement above should prevent this from ever happening, this is here just in case
      }
    }
  }

  public void playLine() {
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

  public void endPlaying() {
    // stop all the motors
    Robot.driveTrain.stop();
    // INSERT STOPS HERE

    playing = false;
    // SmartDashboard.putBoolean("playing", playing);

    if (scanner != null){
			scanner.close();
    }
    Robot.shifter.isPlayingShift = false;
    
    if (Robot.playerNotifier != null) {
      Robot.playerNotifier.stop();
      Robot.playerNotifier = null;
    }

  }

}
