
package frc.robot.subsystems;
import  frc.robot.commands.*;
import frc.robot.Robot;
import  frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *This subsystem is used for precise positioning of the arm of the robot,
 *and for extending/retracting the arm
 */
public class Arm extends Subsystem {
    double p=.001;
    double i=0;
    double d=0;
    double integral = 0;
    double previousError = 0;
    double previousDesiredEncoderValue = -10;
    int offset=0;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	// make sure the pistons are closed at first
    public void initDefaultCommand() {
        // RobotMap.armMotor1.setSelectedSensorPosition(0,0,10);
        offset=0;
    }

    public int rotateToPosition(int desiredEncoderValue){
        //checks if the target has changed
        //if it has changed, reset the base variables to 0;
        if(desiredEncoderValue!=previousDesiredEncoderValue){
            integral=0;
            previousError=0;
            previousDesiredEncoderValue = desiredEncoderValue;
            // RobotMap.armMotor1.setSelectedSensorPosition(0,0,10);
            offset=0;
        }
        desiredEncoderValue+=offset;
        //reflecting it across the center of the robot if it's reversed
        if(Robot.driveTrain.isReversed()){
            // desiredEncoderValue+= 2*(RobotMap.ARM_MAX_TICK_VAL/2-desiredEncoderValue);
            desiredEncoderValue = (int)RobotMap.ARM_MAX_TICK_VAL-desiredEncoderValue;
        }
        SensorCollection sensor = RobotMap.armMotor1.getSensorCollection();

        SmartDashboard.putNumber("Arm Encoder Value", sensor.getQuadraturePosition());

        double error = desiredEncoderValue + sensor.getQuadraturePosition();
        integral += error*.02;
        double derivative = (error-previousError)/.02;
        double speed = p*error + i*integral + d*derivative;

        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);
        RobotMap.armMotor2.set(ControlMode.PercentOutput, -speed);

        // if(Robot.oi.getXbox2().getYButton()) RobotMap.armMotor1.setSelectedSensorPosition(0,0,10);
        if(Robot.oi.getXbox2().getBumper(Hand.kLeft)) offset-=100;
        if(Robot.oi.getXbox2().getBumper(Hand.kRight)) offset+=100;

        return offset;
        // return (0!=Robot.oi.getXbox1().getTriggerAxis(Hand.kLeft)) || (0!=Robot.oi.getXbox1().getTriggerAxis(Hand.kRight));
    }
}
