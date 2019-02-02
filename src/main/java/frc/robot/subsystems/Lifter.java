
package frc.robot.subsystems;
import  frc.robot.commands.*;
import  frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lifter extends Subsystem {
	private final  DoubleSolenoid liftingMechFront = new DoubleSolenoid(RobotMap.aForwardChannel,RobotMap.aReverseChannel);
	private final  DoubleSolenoid liftingMechBack  =  new DoubleSolenoid(RobotMap.bForwardChannel,RobotMap.bReverseChannel);
	private static int count = 0;
	private void liftFrontUp() {
		liftingMechFront.set(DoubleSolenoid.Value.kReverse);
	
	}
	
	private void liftFrontDown(){
		liftingMechFront.set(DoubleSolenoid.Value.kForward);
	
	}

	private void liftBackUp() {
		liftingMechBack.set(DoubleSolenoid.Value.kReverse);
	
	}
	
	private void liftBackDown(){
		liftingMechBack.set(DoubleSolenoid.Value.kForward);
	
	}
	
	private void liftFront(){
		if (liftingMechFront.get()==DoubleSolenoid.Value.kReverse){
			liftFrontDown();
		}else {
			liftFrontUp();
		}
	}

	private void liftBack(){
		if (liftingMechBack.get()==DoubleSolenoid.Value.kReverse){
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
		//liftBack();
		if(count==0){
			liftFrontUp();
			liftBackUp();
		}else if (count==1){
			liftFront();
		}else if (count==2){
			liftBack();
		}
		count++;
		count = count%3;
		SmartDashboard.putNumber("Count: ", count);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
		liftingMechFront.set(DoubleSolenoid.Value.kReverse);
		liftingMechBack.set(DoubleSolenoid.Value.kReverse);
    }
}
