package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.SubsystemTestBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class AirCompressor extends SubsystemTestBase {
	public static final  Compressor c = new Compressor(RobotMap.compressormodule);
	public AirCompressor (int CompressorModule) {
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
	}

	public void periodic() {
		// Put code here to be run every loop
	}

	public Compressor getC() {
		return c;
	}
	
	public boolean subsystemTest(boolean advanced) {
		return true;
	}
}