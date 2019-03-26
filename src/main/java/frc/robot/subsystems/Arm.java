
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.Levels;

/**
 * This subsystem is used for precise positioning of the arm of the robot, and
 * for extending/retracting the arm
 */
public class Arm extends Subsystem {
    private double integral = 0;
    private double previousError = 0;
    private double previousDesiredTargetEncoderValue = 0;
    private double speed;
    private double derivative;

    private double encoderPosition;
    private double error;

    public boolean armIsManual = false;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    // make sure the pistons are closed at first
    public void initDefaultCommand() {
        setDefaultCommand(new Levels());
    }

    public void rotateToPosition(double desiredTargetEncoderValue) {
        // Checks if the target has changed.
        // If it has changed, reset the base variables to 0.
        if (desiredTargetEncoderValue != previousDesiredTargetEncoderValue) {
            integral = 0;
            previousError = 0;
            previousDesiredTargetEncoderValue = desiredTargetEncoderValue;
        }

        encoderPosition = getArm1Encoder();

        error = desiredTargetEncoderValue - encoderPosition;
        integral += error * .02;

        // Scale down the integral
        if (Math.abs(error) < 25 && !(Math.abs(error) > Math.abs(previousError))) {
            integral *= 0.1;
        }

        derivative = (error - previousError) / .02;

        // SmartDashboard.putNumber("total_P_arm", RobotMap.ARM_kP * error);
        // SmartDashboard.putNumber("total_I_arm", RobotMap.ARM_kI * integral);
        // SmartDashboard.putNumber("total_D_arm", RobotMap.ARM_kD * derivative);

        speed = RobotMap.ARM_kP * error + RobotMap.ARM_kI * integral + RobotMap.ARM_kD * derivative;
        speed *= -1;

        if (Math.abs(error) < RobotMap.ARM_DEAD_ZONE) {
            speed = 0;
        }

        if (speed > RobotMap.ARM_LIMITER) {
            speed = RobotMap.ARM_LIMITER;
        } else if (speed < -RobotMap.ARM_LIMITER) {
            speed = -RobotMap.ARM_LIMITER;
        }

        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);

        if (speed < 0) {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_BACKWARDS);
        } else {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_FORWARDS);
        }

        previousError = error;
    }

    public void rotateNoPID(double desiredTargetEncoderValue) {
        encoderPosition = getArm1Encoder();
        error = desiredTargetEncoderValue - encoderPosition;

        if (error > 200) {
            speed = -1.0;
        } else if (error < -200) {
            speed = 1.0;
        } else if (error < -50) {
            speed = 0.2;
        } else if (error > 50) {
            speed = -0.2;
        } else {
            speed = 0.0;
        }

        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);
        if (speed < 0) {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_BACKWARDS);
        } else {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_FORWARDS);
        }
    }

    public void manualControl(double joystickInput) {
        RobotMap.armMotor1.set(ControlMode.PercentOutput, joystickInput);
        RobotMap.armMotor2.set(ControlMode.PercentOutput, joystickInput);
        if (joystickInput < 0) {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, joystickInput * RobotMap.ARM_MOTOR_SLOW_BACKWARDS);
        } else {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, joystickInput * RobotMap.ARM_MOTOR_SLOW_FORWARDS);
        }
    }

    // Negative because encoder happens to be the other way
    public double getArm1Encoder() {
        return RobotMap.armMotor1.getSelectedSensorPosition();
    }

    // Negative because encoder happens to be the other way
    public double getArm2Encoder() {
        return RobotMap.armMotor2.getSelectedSensorPosition();
    }

    public double getSpeed() {
        return speed;
    }

    public double getLastEncoderTarget() {
        return previousDesiredTargetEncoderValue;
    }
}
