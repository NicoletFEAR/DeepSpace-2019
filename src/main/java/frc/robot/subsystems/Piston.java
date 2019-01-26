
package frc.robot.subsystems;
import  frc.robot.commands.*;
import  frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Piston extends Subsystem {
	private final  DoubleSolenoid leftShifter = new DoubleSolenoid(RobotMap.aForwardChannel,RobotMap.aReverseChannel);
	
	public void shootOut() {
		leftShifter.set(DoubleSolenoid.Value.kForward);
	
	}
	
	public void takeIn(){
		leftShifter.set(DoubleSolenoid.Value.kReverse);
	
	}
	// shift the gearbox to the opposite state
	public void shoot(){
		if (leftShifter.get()==DoubleSolenoid.Value.kForward){
			takeIn();
		}else {
			shootOut();
		}
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
    	shootOut();
 
    }
}
