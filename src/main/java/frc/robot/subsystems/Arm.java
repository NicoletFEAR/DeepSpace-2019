
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Levels;

/**
 * This subsystem is used for precise positioning of the arm of the robot, and
 * for extending/retracting the arm.
 */
public class Arm extends Subsystem {
    private double integral = 0;
    private double previousError = 0;
    private double previousDesiredtargetEncoderValue = 0;
    // private int offset = 0;
    private double speed;
    private double derivative;

    private double encoderPosition;
    private double error;

    /**
     * Should the arm calculate the speed to get to its target using PID or set
     * values?
     */
    public boolean armIsManual = false;

    /**
     * Sets the method to run while this subsystem is running. This method runs upon
     * this subsystem's initialization. This default command checks to see if the
     * pistons are closed first.
     */
    public void initDefaultCommand() {
        // RobotMap.armMotor1.setSelectedSensorPosition(0,0,10);
        setDefaultCommand(new Levels());
    }

    /**
     * Sets a target for the robot's arm to rotate to based off of encoder values.
     * 
     * @param desiredtargetEncoderValue target encoder value to rotate the robot's
     *                                  arm to
     */
    public void rotateToPosition(double desiredtargetEncoderValue) {
        /*
         * We check to see if the target for arm rotation has changed. If the target has
         * changed, reset the base variables to 0.
         */
        if (desiredtargetEncoderValue != previousDesiredtargetEncoderValue) {
            integral = 0;
            previousError = 0;
            previousDesiredtargetEncoderValue = desiredtargetEncoderValue;
        }

        SmartDashboard.putNumber("armTarget", desiredtargetEncoderValue);
        encoderPosition = getArm1Encoder();

        /*
         * We calculate how far we are from our target and then increase our integral.
         * The integral will make sure that if the robot isn't giving enough power to
         * the motors, and gets stuck, the integral will keep increasing to tell the
         * robot to provide more power.
         */
        error = desiredtargetEncoderValue - encoderPosition;
        integral += error * .02;

        /* Scale down the integral */
        if (Math.abs(error) < 25 && !(Math.abs(error) > Math.abs(previousError))) {
            integral *= 0.1;
        }

        derivative = (error - previousError) / .02;

        SmartDashboard.putNumber("total_P_arm", RobotMap.ARM_kP * error);
        SmartDashboard.putNumber("total_I_arm", RobotMap.ARM_kI * integral);
        SmartDashboard.putNumber("total_D_arm", RobotMap.ARM_kD * derivative);

        /* The one and only PID calculation */
        speed = RobotMap.ARM_kP * error + RobotMap.ARM_kI * integral + RobotMap.ARM_kD * derivative;
        speed *= -1;

        /* Make sure our arm doesn't move beyond its bounds of movement */
        if (Math.abs(error) < RobotMap.ARM_DEAD_ZONE) {
            speed = 0;
        }

        /*
         * Make sure our arm doesn't go faster or slower than our defined limit in
         * RobotMap
         */
        if (speed > RobotMap.ARM_LIMITER) {
            speed = RobotMap.ARM_LIMITER;
            // RobotMap.armMotor1.set(ControlMode.PercentOutput, RobotMap.ARM_LIMITER);
            // RobotMap.armMotor2.set(ControlMode.PercentOutput, RobotMap.ARM_LIMITER);
        } else if (speed < -RobotMap.ARM_LIMITER) {
            speed = -RobotMap.ARM_LIMITER;
            // RobotMap.armMotor1.set(ControlMode.PercentOutput, -RobotMap.ARM_LIMITER);
            // RobotMap.armMotor2.set(ControlMode.PercentOutput, -RobotMap.ARM_LIMITER);
        }
        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);

        /*
         * One of our motors for our arm is faster than the other, so we scale our speed
         * to make sure the motors are running at a close to the same speed. (Mechanical
         * problems, am I right? :D)
         */
        // RobotMap.armMotor2.set(ControlMode.PercentOutput, speed);
        if (speed < 0) {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_BACKWARDS);
        } else {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_FORWARDS);
        }

        previousError = error;
    }

    /**
     * Sets a target for the robot to rotate to, calculating the rotation speed
     * without the use of PID. In this method, depending on the difference between
     * the arm encoder and a target rotation encoder value, we set the arm rotation
     * to different predefined speeds.
     * 
     * @param desiredtargetEncoderValue target encoder value to rotate the arm to
     */
    public void rotateNoPID(double desiredtargetEncoderValue) {
        encoderPosition = getArm1Encoder();
        error = desiredtargetEncoderValue - encoderPosition;

        /* Set speed depending on preset bounds for the error */
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

        /* Ready, set, ROTATE! (Also scale arm motors so they run at the same speed) */
        SmartDashboard.putNumber("SPD_ARM_NO_PID", 0);
        RobotMap.armMotor1.set(ControlMode.PercentOutput, speed);
        if (speed < 0) {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_BACKWARDS);
        } else {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, speed * RobotMap.ARM_MOTOR_SLOW_FORWARDS);
        }
        // RobotMap.armMotor2.set(ControlMode.PercentOutput, speed *
        // RobotMap.ARM_LIMITER);
    }

    /**
     * Method to handle simple joystick based control for the arm.
     * 
     * @param joystickInput value from a joystick to set the speed and direction of
     *                      the arm
     */
    public void manualControl(double joystickInput) {
        RobotMap.armMotor1.set(ControlMode.PercentOutput, joystickInput);
        RobotMap.armMotor2.set(ControlMode.PercentOutput, joystickInput);
        if (joystickInput < 0) {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, joystickInput * RobotMap.ARM_MOTOR_SLOW_BACKWARDS);
        } else {
            RobotMap.armMotor2.set(ControlMode.PercentOutput, joystickInput * RobotMap.ARM_MOTOR_SLOW_FORWARDS);
        }
    }

    /**
     * Simple convenience function to access arm 1's encoder value.
     * 
     * @return arm 1 encoder value
     */
    public double getArm1Encoder() {
        /* Negative because encoder happens to be the other way */
        return RobotMap.armMotor1.getSelectedSensorPosition();
    }

    /**
     * Simple convenience function to access arm 2's encoder value.
     * 
     * @return arm 2 encoder value
     */
    public double getArm2Encoder() {
        /* Negative because encoder happens to be the other way */
        return RobotMap.armMotor2.getSelectedSensorPosition();
    }

    /**
     * Simple convenience function to access the current target arm speed.
     * 
     * @return current target arm speed
     */
    public double getSpeed() {
        return speed;
    }
}
