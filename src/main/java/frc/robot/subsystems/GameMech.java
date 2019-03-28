
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class GameMech extends Subsystem {
	private DoubleSolenoid panelShooter = new DoubleSolenoid(RobotMap.gmForwardChannel, RobotMap.gmReverseChannel);

	public void initDefaultCommand() {
	}

	public void open() {
		panelShooter.set(DoubleSolenoid.Value.kReverse);
		Robot.hatchMechState = "release";
	}
	
	public void pull(){
		panelShooter.set(DoubleSolenoid.Value.kForward);
		Robot.hatchMechState = "grab";
	}

	public void toggleMechPiston(){
		if (panelShooter.get() == DoubleSolenoid.Value.kReverse) {
			pull();
		} else {
			open();
		}
	}
}
