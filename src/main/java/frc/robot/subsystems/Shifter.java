package frc.robot.subsystems;
import  frc.robot.RobotMap;
import frc.robot.commands.AutoShift;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem handling drivetrain shifting
 */
public class Shifter extends Subsystem {
	public DoubleSolenoid shifty = new DoubleSolenoid(RobotMap.shiftForwardChannel, RobotMap.shiftReverseChannel);
	public boolean isOnPath;


	public void shiftup() {
		shifty.set(DoubleSolenoid.Value.kReverse);
		RobotMap.speedMultiplier = 1;
	}

	public void shiftdown() {
		shifty.set(DoubleSolenoid.Value.kForward); //Low = forward
		RobotMap.speedMultiplier = 1;
	}

	// shift the gearbox to the opposite state
	public void shift() {
		if (shifty.get() == DoubleSolenoid.Value.kReverse) {
			shiftdown();
		} else {
			shiftup();
		}
	}

	public void stop() {
		shifty.set(DoubleSolenoid.Value.kOff);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// make sure the pistons are closed at first
	public void initDefaultCommand() {
		setDefaultCommand(new AutoShift());
	}
}