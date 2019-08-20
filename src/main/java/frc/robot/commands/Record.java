/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

// loosely based off of 2220 Blue Twilight 2015 code
// https://github.com/DennisMelamed/FRC-Play-Record-Macro/blob/master/FRC2220-Play-Record-Macro-DM/src/BTMacroRecord.java

public class Record extends Command {

  FileWriter writer;

  int currentLine;

  long startTime;

  public boolean isRecording;

  double thisLine[] = new double[28];


  public Record(){
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // can't depend on driveTrain because you have to drive with OpenLoopDrive to record!
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
    currentLine = 0;
    try { makeFileWriter(); } catch (IOException e) { end(); }
    isRecording = true;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    try { writeLine(); } catch (IOException e) {}

    if (Robot.oi.getXbox1().getBackButton() && currentLine >= 50) { // if you pressed the record button again (after 1s sdelay so it doesn't instantly end)
      end();
    }

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
    isRecording = false;
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
    writer = new FileWriter(RobotMap.autoFileLocName + Robot.autoName + ".csv");
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
      // start each "frame" with the elapsed time since we started recording<
      // writer.append("" + (System.currentTimeMillis() - startTime)); (>code from other team)

      // in this chunk, use writer.append to add each type of data you want to record
      // to the frame

      // GET THE DATA TO RECORD - for now using hoystick and button input, but you can also do encoders, motors, or whatever
      // if button pressed, return 1.0, if not pressed, return 0.0, if still pressed, return 2.0

      // XBOX1 *****************************************************
      //joysticks:
      thisLine[0] = Robot.oi.getXbox1().getX(GenericHID.Hand.kLeft);
      thisLine[1] = Robot.oi.getXbox1().getY(GenericHID.Hand.kLeft);
      thisLine[2] = Robot.oi.getXbox1().getX(GenericHID.Hand.kRight);
      thisLine[3] = Robot.oi.getXbox1().getY(GenericHID.Hand.kRight);
      //triggers:
      thisLine[4] = Robot.oi.getXbox1().getTriggerAxis(GenericHID.Hand.kLeft);
      thisLine[5] = Robot.oi.getXbox1().getTriggerAxis(GenericHID.Hand.kRight);
      //bumpers:
      thisLine[6] = (thisLine[6] == 0) ? (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0) : (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kLeft) ? 2.0 : 0.0) ; 
      thisLine[7] = (thisLine[7] == 0) ? (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0) : (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kRight) ? 2.0 : 0.0) ;
      //coloured buttons:
      thisLine[8] = (thisLine[8] == 0) ? (Robot.oi.getXbox1().getXButton() ? 1.0 : 0.0) : (Robot.oi.getXbox1().getXButton() ? 2.0 : 0.0) ; // X
      thisLine[9] = (thisLine[9] == 0) ? (Robot.oi.getXbox1().getYButton() ? 1.0 : 0.0) : (Robot.oi.getXbox1().getYButton() ? 2.0 : 0.0) ; // Y
      thisLine[10] = (thisLine[10] == 0) ? (Robot.oi.getXbox1().getAButton() ? 1.0 : 0.0) : (Robot.oi.getXbox1().getAButton() ? 2.0 : 0.0) ; // A
      thisLine[11] = (thisLine[11] == 0) ? (Robot.oi.getXbox1().getBButton() ? 1.0 : 0.0) : (Robot.oi.getXbox1().getBButton() ? 2.0 : 0.0) ; // B
      //middle small buttons:
      thisLine[12] = (thisLine[12] == 0) ? (Robot.oi.getXbox1().getStartButton() ? 1.0 : 0.0) : (Robot.oi.getXbox1().getStartButton() ? 2.0 : 0.0) ;
      thisLine[13] = (thisLine[13] == 0) ? (Robot.oi.getXbox1().getBackButton() ? 1.0 : 0.0) : (Robot.oi.getXbox1().getBackButton() ? 2.0 : 0.0) ;
      // XBOX2 *******************************************************
      thisLine[14]= 0.0;
      thisLine[15] = 0.0;
      thisLine[16] = 0.0;
      thisLine[17] = 0.0;
      thisLine[18] = 0.0;
      thisLine[19] = 0.0;
      thisLine[20] = 0.0;
      thisLine[21] = 0.0;
      thisLine[22] = 0.0;
      thisLine[23]= 0.0;
      thisLine[24] = 0.0;
      thisLine[25] = 0.0;
      thisLine[26] = 0.0;
      thisLine[27] = 0.0;
      
      writer.append("" + thisLine[0]); // FIRST DOUBLE IN LINE MUST NOT HAVE COMMA BEFORE IT

      for (int q = 1; q < 27; q++) {
              writer.append("," + thisLine[q]); // MIDDLE LINES SEPARATED BY COMMA
      }
      /*
       * THE LAST ENTRY OF THINGS YOU RECORD NEEDS TO HAVE A DELIMITER CONCATENATED TO
       * THE STRING AT THE END. OTHERWISE GIVES NOSUCHELEMENTEXCEPTION
       */
      writer.append("," + thisLine[27] + "\n");
      /*
       * CAREFUL. KEEP THE LAST THING YOU RECORD BETWEEN THESE TWO COMMENTS AS A
       * REMINDER TO APPEND THE DELIMITER - 2220 wrote that not me
       */
    }
  }

}
