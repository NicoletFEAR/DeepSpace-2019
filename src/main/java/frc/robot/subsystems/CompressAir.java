/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Subsystem representing the compressor on the robot
 */
public class CompressAir extends Subsystem {
  private Compressor theCompressor;

  /**
   * Initialize the compressor on the robot
   */
  public CompressAir() {
    theCompressor = new Compressor(RobotMap.compressormodule);
  }

  /**
   * Toggles the compressor on and forced off. If forced off, the compressor will
   * not run no matter what. (Obviously! :D) If on, the compressor will turn on
   * and off depending on the reading of a pressure sensor connected to the
   * Pneumatic Control Module.
   */
  public void toggleCompressor() {
    if (theCompressor.enabled() == false) {
      theCompressor.start();
    } else {
      theCompressor.stop();
    }
  }

  /**
   * Reports whether the compressor is on or off.
   * 
   * @return is the compressor enabled
   */
  public boolean isEnabled() {
    return theCompressor.enabled();
  }

  /**
   * Usually species a default command to run when the subsystem starts up, but
   * does nothing in this subsystem.
   */
  @Override
  public void initDefaultCommand() {
  }
}