/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Playback extends Command {

  double thisLine[] = new double[28];
  int currentLine = 0;
  boolean playing = false;

  Scanner scanner;
  long startTime;

  boolean onTime = true;
	double nextDouble;

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

    playing = true;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (!Robot.isAutonomous || scanner == null) { end(); } // stop if robot moves into teleop

    // read the line of the file using

    currentLine++;

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    playing = false;
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

}
