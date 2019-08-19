/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

    // get the file

    playing = true;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // read the line of the file using currentLine

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    playing = false;
    end();
  }
}
