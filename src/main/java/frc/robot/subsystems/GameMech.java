
package frc.robot.subsystems;
import  frc.robot.commands.*;
import  frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;


public class GameMech extends Subsystem {
	private final  DoubleSolenoid panelShooter = new DoubleSolenoid(RobotMap.cForwardChannel,RobotMap.cReverseChannel);
	
	public void initDefaultCommand() {
		setDefaultCommand(new FlyWheelDrive());
		open();
	}

	public void open() {
		panelShooter.set(DoubleSolenoid.Value.kForward);
	}
	
	public void pull(){
		panelShooter.set(DoubleSolenoid.Value.kReverse);
	}
	// shift the gearbox to the opposite state
	public void toggleMechPiston(){
		if (panelShooter.get()==DoubleSolenoid.Value.kForward){
			pull();
		}else {
			open();
		}
	}

	public void spinFlyWheels(double speed){
		RobotMap.flywheel1.set(ControlMode.PercentOutput, speed);
		RobotMap.flywheel2.set(ControlMode.PercentOutput, speed);
	}

	public boolean getCargoLimitSwitch(){
		boolean cargoIn;
		cargoIn = RobotMap.cargoIntakeLimitSwitch.get();
		return cargoIn;
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
}
