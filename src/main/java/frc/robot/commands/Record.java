/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

// https://github.com/DennisMelamed/FRC-Play-Record-Macro/blob/master/FRC2220-Play-Record-Macro-DM/src/BTMacroRecord.java

public class Record extends Command {

  FileWriter writer;

  int currentLine;

  long startTime;

  public Record() throws IOException {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
    currentLine = 0;
    try { makeFileWriter(); } catch (IOException e) {}
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    

    currentLine++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // detect if record button pressed a second time to stop
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end(){
    // tidy up and end
    try { endRecording(); } catch (IOException e) {}
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // do not interrupt recording
  }

  // CUSTOM METHODS: **************************************************************

  // makes the java FileWriter that puts data in the file
  public void makeFileWriter() throws IOException {
    writer = new FileWriter(RobotMap.autoFileLocName);
  }

  // this method closes the writer and makes sure that all the data you recorded
  // makes it into the file
  public void endRecording() throws IOException {
    
    if (writer != null) {
      writer.flush();
      writer.close();
    }
  }

  public void writeLine() throws IOException {
    if (writer != null) {
      // start each "frame" with the elapsed time since we started recording
      // writer.append("" + (System.currentTimeMillis() - startTime));

      // in this chunk, use writer.append to add each type of data you want to record
      // to the frame
      // the 2015 robot used the following motors during auto

      // drive motors
      writer.append("," + storage.robot.getFrontLeftMotor().get());
      writer.append("," + storage.robot.getFrontRightMotor().get());
      writer.append("," + storage.robot.getBackRightMotor().get());
      writer.append("," + storage.robot.getBackLeftMotor().get());

      // barrel motors
      writer.append("," + storage.robot.getBarrelMotorLeft().get());
      writer.append("," + storage.robot.getBarrelMotorRight().get());

      // fork motors
      writer.append("," + storage.robot.getLeftForkLeft().get());
      writer.append("," + storage.robot.getLeftForkRight().get());
      writer.append("," + storage.robot.getRightForkLeft().get());
      writer.append("," + storage.robot.getRightForkRight().get());
      /*
       * THE LAST ENTRY OF THINGS YOU RECORD NEEDS TO HAVE A DELIMITER CONCATENATED TO
       * THE STRING AT THE END. OTHERWISE GIVES NOSUCHELEMENTEXCEPTION
       */

      // this records a true/false value from a piston
      writer.append("," + storage.robot.getToteClamp().isExtended() + "\n");

      /*
       * CAREFUL. KEEP THE LAST THING YOU RECORD BETWEEN THESE TWO COMMENTS AS A
       * REMINDER TO APPEND THE DELIMITER
       */
    }
  }

}
