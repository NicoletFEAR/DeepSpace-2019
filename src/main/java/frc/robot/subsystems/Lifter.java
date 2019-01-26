
package frc.robot.subsystems;
import  frc.robot.commands.*;
import  frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lifter extends Subsystem {
	private final  DoubleSolenoid liftingMech = new DoubleSolenoid(RobotMap.aForwardChannel,RobotMap.aReverseChannel);
	
	public void liftUp() {
		liftingMech.set(DoubleSolenoid.Value.kForward);
	
	}
	
	public void liftDown(){
		liftingMech.set(DoubleSolenoid.Value.kReverse);
	
	}
	// shift the gearbox to the opposite state
	public void lift(){
		if (liftingMech.get()==DoubleSolenoid.Value.kForward){
			liftDown();
		}else {
			liftUp();
		}
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
    	liftDown();
 
    }
}
