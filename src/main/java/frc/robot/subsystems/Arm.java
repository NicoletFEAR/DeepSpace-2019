
package frc.robot.subsystems;
import  frc.robot.commands.*;
import frc.robot.Robot;
import  frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.SensorCollection;

// import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *This subsystem is used for precise positioning of the arm of the robot,
 *and for extending/retracting the arm
 */
public class Arm extends Subsystem {
    double integral = 0;
    double previousError = 0;
    double previousDesiredtargetEncoderValue = 0;
    // int offset=0;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
        // RobotMap.armMotor1.setSelectedSensorPosition(0,0,10);
        setDefaultCommand(new Levels());
    }

    public void rotateToPosition(int desiredtargetEncoderValue){
        //checks if the target has changed
        //if it has changed, reset the base variables to 0;
        if(desiredtargetEncoderValue!=previousDesiredtargetEncoderValue){
            integral=0;
            previousError=0;
            previousDesiredtargetEncoderValue = desiredtargetEncoderValue;
        }

        // desiredtargetEncoderValue+=RobotMap.offset;

        //reflecting it across the center of the robot if it's reversed
        if(Robot.driveTrain.isReversed()){
            // desiredtargetEncoderValue+= 2*(RobotMap.ARM_MAX_TICK_VAL/2-desiredtargetEncoderValue);
            desiredtargetEncoderValue = (int)RobotMap.ARM_MAX_TICK_VAL-desiredtargetEncoderValue;
        }
        //SensorCollection sensor = RobotMap.armMotor1.getSensorCollection(); // Old encoder code
        double encoderPosition = -RobotMap.armMotor1.getSelectedSensorPosition();

        SmartDashboard.putNumber("Arm Encoder Value", encoderPosition);

        double error = desiredtargetEncoderValue - encoderPosition;
        integral += error*.02;
        double derivative = (error-previousError)/.02;
        double speed = RobotMap.ARM_kP*error + RobotMap.ARM_kI*integral + RobotMap.ARM_kD*derivative;

        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);
        RobotMap.armMotor2.set(ControlMode.PercentOutput, speed);
    }

    public void slamToEnd() {
        RobotMap.armMotor1.set(ControlMode.PercentOutput, 1); 
        RobotMap.armMotor2.set(ControlMode.PercentOutput, 1);
    }

    public void EncoderResetAtEnd() {
        RobotMap.armMotor1.set(ControlMode.PercentOutput, 0); 
        RobotMap.armMotor2.set(ControlMode.PercentOutput, 0);
		RobotMap.armMotor1.setSelectedSensorPosition(0, 0, 10); // 1st value is the value to reset to
    }
    

}
