
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.SensorCollection;

// import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Levels;

/**
 * This subsystem is used for precise positioning of the arm of the robot, and
 * for extending/retracting the arm
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

    public void rotateToPosition(double desiredtargetEncoderValue) {
        // checks if the target has changed
        // if it has changed, reset the base variables to 0;
        if (desiredtargetEncoderValue != previousDesiredtargetEncoderValue) {
            integral = 0;
            previousError = 0;
            previousDesiredtargetEncoderValue = desiredtargetEncoderValue;
        }
        SmartDashboard.putNumber("armTarget", desiredtargetEncoderValue);
        double encoderPosition = getArmEncoder();

        double error = desiredtargetEncoderValue - encoderPosition;
        integral += error * .02;
        if (Math.abs(error) < 40) {
            integral = 0;
        }
        double derivative = (error - previousError) / .02;
        double speed = RobotMap.ARM_kP * error + RobotMap.ARM_kI * integral - RobotMap.ARM_kD * derivative;

        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);
        RobotMap.armMotor2.set(ControlMode.PercentOutput, speed);
    }

    public double getArmEncoder() {
        return RobotMap.armMotor1.getSelectedSensorPosition(); // negative because enoder happens to be the poother way
    }

}
