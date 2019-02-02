
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
	private static int count = 0;
	private void liftFrontUp() {
		liftingMechFront.set(DoubleSolenoid.Value.kForward);
	
	}
	
	private void liftFrontDown(){
		liftingMechFront.set(DoubleSolenoid.Value.kReverse);
	
	}

	private void liftBackUp() {
		liftingMechBack.set(DoubleSolenoid.Value.kForward);
	
	}
	
	private void liftBackDown(){
		liftingMechBack.set(DoubleSolenoid.Value.kReverse);
	
	}
	
	private void liftFront(){
		if (liftingMechFront.get()==DoubleSolenoid.Value.kForward){
			liftFrontDown();
		}else {
			liftFrontUp();
		}
	}

	private void liftBack(){
		if (liftingMechBack.get()==DoubleSolenoid.Value.kForward){
			liftBackDown();
		}else {
			liftBackUp();
		}
	}

	private void liftBoth(){
		liftFront();
		liftBack();
	}

	

	public void controllingLifters(){
		if(count==0){
			liftBoth();
		}else if (count==1){
			liftFrontDown();
		}else if (count==2){
			liftBackDown();
		}
		count++;
		count = count%3;
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
    	liftFrontDown();
		liftBackDown();
    }
}
