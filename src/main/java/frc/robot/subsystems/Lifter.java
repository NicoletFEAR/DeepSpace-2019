
package frc.robot.subsystems;
import  frc.robot.commands.*;
import  frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lifter extends Subsystem {
	private final  DoubleSolenoid liftingMechFront = new DoubleSolenoid(RobotMap.aForwardChannel,RobotMap.aReverseChannel);
	private final  DoubleSolenoid liftingMechBack  =  new DoubleSolenoid(RobotMap.bForwardChannel,RobotMap.bReverseChannel);
	public void liftFrontUp() {
		liftingMechFront.set(DoubleSolenoid.Value.kForward);
	
	}
	
	public void liftFrontDown(){
		liftingMechFront.set(DoubleSolenoid.Value.kReverse);
	
	}
	// shift the gearbox to the opposite state
	public void liftFront(){
		if (liftingMechFront.get()==DoubleSolenoid.Value.kForward){
			liftFrontDown();
		}else {
			liftFrontUp();
		}
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
    	liftFrontDown();
 
    }
}
