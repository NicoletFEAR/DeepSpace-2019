/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.vision.VisionServer;

/**
 * Add your docs here.
 */
public class SwitchPhones extends InstantCommand {
  /**
   * Add your docs here.
   */
  public SwitchPhones() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    System.out.println("inside switchPhone method");
    if (Robot.deviceNumber.equals("ZY22387SPX")) {
       Robot.deviceNumber = "ba8ed155";
       VisionServer.adb.reversePortForward(8254, 8111, "ZY22387SPX");
        VisionServer.adb.reversePortForward(8111, 8254, "ba8ed155");
        System.out.println("ZY on " + "8111");


    }
    else { 
      
      Robot.deviceNumber = "ZY22387SPX";
      VisionServer.adb.reversePortForward(8254, 8254, "ZY22387SPX");
      VisionServer.adb.reversePortForward(8111, 8111, "ba8ed155");
      System.out.println("ZY on " + "8254");


    }
  //  Robot.mVisionServer.switchPort(Robot.visionPort);
  }

}