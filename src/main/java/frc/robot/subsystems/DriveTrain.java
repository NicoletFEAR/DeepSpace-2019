// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.*;
import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private boolean reversed;

	// SpeedControllerGroup leftSide = new
	// SpeedControllerGroup(RobotMap.left1, RobotMap.left2,
	// RobotMap.left3);
	// SpeedControllerGroup rightSide = new
	// SpeedControllerGroup(RobotMap.right1, RobotMap.right2,
	// RobotMap.right3);
	//
	// public DifferentialDrive robotDrive = new DifferentialDrive(leftSide,
	// rightSide);

	public double leftSideSwitchSide;
	public double rightSideSwitchSide;
	public double integral = 0;
	public double previousError = 0;
	public double previousDesiredAngle = 0;
	public double previousDesiredDistance = 0;

	// Setup our timed drive
	double currentTime = 0.0;
	double endTime = 0.0;

	public void testCommand() {
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new OpenLoopDrive());
		// initPID();
	}

	public void initPID() {
		// RobotMap.left1.configAllowableClosedloopError(RobotMap.PIDSLOT,
		// RobotMap.ALLOWABLE_ERROR_CONSTANT_LEFT, 10);
		// RobotMap.right1.configAllowableClosedloopError(RobotMap.PIDSLOT,
		// RobotMap.ALLOWABLE_ERROR_CONSTANT_RIGHT,
		// 10);
		//
		// // Make sure the CANTalons are looking at the right stored PID values
		// // with the Profile
		// // Set our PID Values
		//
		// RobotMap.left1.config_kP(RobotMap.PIDSLOT, RobotMap.LeftP, 10);
		// RobotMap.left1.config_kI(RobotMap.PIDSLOT, RobotMap.LeftI, 10);
		// RobotMap.left1.config_kD(RobotMap.PIDSLOT, RobotMap.LeftD, 10);
		// RobotMap.left1.config_kF(RobotMap.PIDSLOT, RobotMap.LeftF, 10);
		// RobotMap.left1.config_IntegralZone(RobotMap.PIDSLOT, RobotMap.IZONE, 10);
		// /*
		// * Set how fast of a rate the robot will accelerate Do not remove or you
		// * get a fabulous prize of a Flipping robot - CLOSED_LOOP_RAMP_RATE
		// */
		// RobotMap.left1.configClosedloopRamp(RobotMap.CLOSED_LOOP_RAMP_RATE, 10);
		//
		// RobotMap.right1.config_kP(RobotMap.PIDSLOT, RobotMap.RightP, 10);
		// RobotMap.right1.config_kI(RobotMap.PIDSLOT, RobotMap.RightI, 10);
		// RobotMap.right1.config_kD(RobotMap.PIDSLOT, RobotMap.RightD, 10);
		// RobotMap.right1.config_kF(RobotMap.PIDSLOT, RobotMap.RightF, 10);
		// RobotMap.right1.config_IntegralZone(RobotMap.PIDSLOT, RobotMap.IZONE, 10);
		// /*
		// * Set how fast of a rate the robot will accelerate Do not remove or you
		// * get a fabulous prize of a Flipping robot - CLOSED_LOOP_RAMP_RATE
		// */
		// RobotMap.right1.configClosedloopRamp(RobotMap.CLOSED_LOOP_RAMP_RATE, 10);
	}

	public void takeJoystickInputs(Joystick left, Joystick right) { // tank drive
		RobotMap.left1.set(ControlMode.PercentOutput, left.getY());
		RobotMap.right1.set(ControlMode.PercentOutput, right.getY());
	}

	public void takeStickInputValues(double leftStickV, double rightStickV) { // arcade drive
		if (!reversed) {
			RobotMap.left1.set(ControlMode.PercentOutput, -leftStickV);
			RobotMap.right1.set(ControlMode.PercentOutput, rightStickV);
		} else {
			RobotMap.left1.set(ControlMode.PercentOutput, -rightStickV);
			RobotMap.right1.set(ControlMode.PercentOutput, leftStickV);
		}

		// SmartDashboard.putData("Drive Train", robotDrive);
		// SmartDashboard.putNumber("Left Side", leftSide.get());
		// SmartDashboard.putNumber("Right Side", rightSide.get());

		/*
		SensorCollection sensor = RobotMap.right1.getSensorCollection();

		SmartDashboard.putNumber("sensor analogin", sensor.getAnalogIn());
		SmartDashboard.putNumber("sensor analoginraw", sensor.getAnalogInRaw());
		SmartDashboard.putNumber("sensor analongvel", sensor.getAnalogInVel());
		SmartDashboard.putNumber("sensor widthpos", sensor.getPulseWidthPosition());
		SmartDashboard.putNumber("sensor velocity", sensor.getQuadratureVelocity());
		*/
	}

	public void ArcadeDrive(double robotOutput, double turnAmount) {
		if (!reversed) {
			SmartDashboard.putNumber("turnamount", turnAmount);
			RobotMap.left1.set(ControlMode.PercentOutput, (-robotOutput) + turnAmount);
			RobotMap.right1.set(ControlMode.PercentOutput, robotOutput + turnAmount);
		} else {
			RobotMap.left1.set(ControlMode.PercentOutput, (robotOutput) + turnAmount);
			RobotMap.right1.set(ControlMode.PercentOutput, -robotOutput + turnAmount);
		}

		// SmartDashboard.putData("Drive Train", robotDrive);
		// SmartDashboard.putNumber("Left Side", leftSide.get());
		// SmartDashboard.putNumber("Right Side", rightSide.get());

		SensorCollection sensorLeft = RobotMap.left1.getSensorCollection();
		SensorCollection sensorRight = RobotMap.right1.getSensorCollection();

		SmartDashboard.putNumber("sensor analogin", sensorRight.getAnalogIn());
		SmartDashboard.putNumber("sensor analoginraw", sensorRight.getAnalogInRaw());
		SmartDashboard.putNumber("sensor analongvel", sensorRight.getAnalogInVel());
		SmartDashboard.putNumber("sensor widthpos", sensorRight.getPulseWidthPosition());
		SmartDashboard.putNumber("sensor velocity", sensorRight.getQuadratureVelocity());

		// shifting
		double averageVelocity = (Math.abs(sensorLeft.getQuadratureVelocity())
				+ Math.abs(sensorRight.getQuadratureVelocity())) / 2;

		SmartDashboard.putNumber("averageVelocity", averageVelocity);

		if (!(Robot.oi.xbox1.getStartButton())) {
			if (averageVelocity < 1900) { // if not in low, switch to low
				if (Robot.shifter.shifty.get() != DoubleSolenoid.Value.kForward) {
					Robot.shifter.shiftdown();
				}
			} else if (averageVelocity < 2100) {
				// DO NOTHING
			} else { // if in low, switch to high
				if (Robot.shifter.shifty.get() == DoubleSolenoid.Value.kForward) {
					Robot.shifter.shiftup();
				}
			}
		} else {
			if (Robot.shifter.shifty.get() != DoubleSolenoid.Value.kForward) {
				Robot.shifter.shiftdown();
			}
		}
	}

	public void ArcadeDriveVer2(double robotOutput, double turnAmount) {
		double outputLeft = -robotOutput + turnAmount;
		double outputRight = robotOutput + turnAmount;
		double multiplier = RobotMap.DRIVE_LIMITER / (outputLeft < outputRight ? outputRight : outputLeft);

		outputLeft *= multiplier;
		outputRight *= multiplier;

		if (reversed) {
			double temp = outputLeft;
			outputLeft = outputRight;
			outputRight = temp;
		}

		RobotMap.left1.set(ControlMode.PercentOutput, outputLeft);
		RobotMap.right1.set(ControlMode.PercentOutput, outputRight);

		// SmartDashboard.putData("Drive Train", robotDrive);
		// SmartDashboard.putNumber("Left Side", leftSide.get());
		// SmartDashboard.putNumber("Right Side", rightSide.get());

		SensorCollection sensorLeft = RobotMap.left1.getSensorCollection();
		SensorCollection sensorRight = RobotMap.right1.getSensorCollection();

		// SmartDashboard.putNumber("sensor analogin", sensorRight.getAnalogIn());
		// SmartDashboard.putNumber("sensor analoginraw", sensorRight.getAnalogInRaw());
		// SmartDashboard.putNumber("sensor analongvel", sensorRight.getAnalogInVel());
		// SmartDashboard.putNumber("sensor widthpos",
		// sensorRight.getPulseWidthPosition());
		// SmartDashboard.putNumber("sensor velocity",
		// sensorRight.getQuadratureVelocity());

		// Shifting Logic Ahead
		double averageVelocity = (Math.abs(sensorLeft.getQuadratureVelocity())
				+ Math.abs(sensorRight.getQuadratureVelocity())) / 2;

		// SmartDashboard.putNumber("averageVelocity", averageVelocity);

		if (!(Robot.oi.xbox1.getStartButton())) {
			if (averageVelocity < 1900) { // if not in low, switch to low
				if (Robot.shifter.shifty.get() != DoubleSolenoid.Value.kForward) {
					Robot.shifter.shiftdown();
				}
			} else if (averageVelocity > 2100) { // if in low, switch to high
				if (Robot.shifter.shifty.get() == DoubleSolenoid.Value.kForward) {
					Robot.shifter.shiftup();
				}
			}
		} else {
			if (Robot.shifter.shifty.get() != DoubleSolenoid.Value.kForward) {
				Robot.shifter.shiftdown();
			}
		}
	}

	// Welcome to the Amazing World of PID! (Population: 3, just P, I, and D)

	private double convertToRotations(double distanceInFeet) {
		return (distanceInFeet) / (Math.PI * (RobotMap.WHEEL_RADIUS * 2));
	}

	public void driveForSeconds(double seconds, double leftInput, double rightInput) {
		currentTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis() + (seconds * 1000);
		while (currentTime < endTime) {
			currentTime = System.currentTimeMillis();
			// robotDrive.tankDrive(leftInput, rightInput);
			RobotMap.left1.set(ControlMode.PercentOutput, leftInput);
			RobotMap.right1.set(ControlMode.PercentOutput, rightInput);
		}
		stop();
	}

	// public void driveArcInit(double horizontalDist, double theta) {
	// // Set Encoder Position to 0
	// RobotMap.left1.setSelectedSensorPosition(0, 0, 10);
	// RobotMap.right1.setSelectedSensorPosition(0, 0, 10);
	// try {
	// Thread.sleep(10);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// RobotMap.left1.setSelectedSensorPosition(0, 0, 10);
	// RobotMap.right1.setSelectedSensorPosition(0, 0, 10);

	// // Calculate arc lengths
	// theta = Math.toRadians(theta);
	// double radius = horizontalDist / (1 - Math.cos(theta));
	// double leftArcLength = theta * (radius + RobotMap.WHEEL_SEPARATION / 2);
	// double rightArcLength = theta * (radius - RobotMap.WHEEL_SEPARATION / 2);
	// if (horizontalDist < 0) {
	// leftArcLength *= -1;
	// rightArcLength *= -1;
	// }

	// // Run convertToRotations functions
	// double leftRot = convertToRotations(leftArcLength);
	// double rightRot = convertToRotations(rightArcLength);

	// // Make motors drive number of rotations
	// // calculated before by convertToRotations()
	// // RobotMap.left1.set(leftRot/* * RobotMap.turnFudgeFactor*/);
	// // //Make sure we inverse this right side,
	// // //otherwise, you have a spinning robot on your hands
	// // RobotMap.right1.set(-rightRot/* * RobotMap.turnFudgeFactor*/);
	// }

	public void driveArcSpeedInit(double leftSpeed, double rightSpeed) {
		// Set Encoder Position to 0
		RobotMap.left1.setSelectedSensorPosition(0, 0, 10);
		RobotMap.right1.setSelectedSensorPosition(0, 0, 10);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RobotMap.left1.setSelectedSensorPosition(0, 0, 10);
		RobotMap.right1.setSelectedSensorPosition(0, 0, 10);

		RobotMap.left1.set(ControlMode.PercentOutput, -leftSpeed);
		RobotMap.right1.set(ControlMode.PercentOutput, -rightSpeed);
	}

	public void driveArcSpeedEnd() {
		// RobotMap.left1.set(0);
		// RobotMap.right1.set(0);
	}

	// Some special isFinished() command stuff to not stop before the robot has
	// even moved

	public double ticksToRot(double ticks) {
		return ticks / 7610;
	}

	public double ticksToIn(double ticks) {
		double circumf = Math.PI * 7.5;
		return ticksToRot(ticks) * circumf;
	}

	public boolean driveToPosition(double desiredDistance) {
		// checks if the target has changed
		// if it has changed, reset the base variables to 0;
		if (desiredDistance != previousDesiredDistance) {
			integral = 0;
			previousError = 0;
			previousDesiredDistance = desiredDistance;
		}
		double currentDistance = ticksToIn(RobotMap.left1.getSelectedSensorPosition());

		double error = desiredDistance - currentDistance;
		integral += error * .02;
		double derivative = (error - previousError) / .02;
		double speed = RobotMap.DRIVE_kP * error + RobotMap.DRIVE_kI * integral + RobotMap.DRIVE_kD * derivative;

		RobotMap.left1.set(ControlMode.PercentOutput, speed);
		RobotMap.right1.set(ControlMode.PercentOutput, speed);

		if (error < RobotMap.DRIVE_ERROR_CONSTANT && error > -RobotMap.DRIVE_ERROR_CONSTANT) {
			return true;
		}
		return false;
	}

	public void driveToPositionEnd() {
		RobotMap.left1.setSelectedSensorPosition(0, 0, 10);
		RobotMap.right1.setSelectedSensorPosition(0, 0, 10);
	}

	public boolean turnToAngle(double desiredAngle) {
		// checks if the target has changed
		// if it has changed, reset the base variables to 0
		if (desiredAngle != previousDesiredAngle) {
			integral = 0;
			previousError = 0;
			previousDesiredAngle = desiredAngle;
		}
		double currentAngle = Robot.navX.getAngle();

		double error = desiredAngle - currentAngle;
		integral += error * .02;
		double derivative = (error - previousError) / .02;
		double speed = RobotMap.TURN_kP * error + RobotMap.TURN_kI * integral + RobotMap.TURN_kD * derivative;

		if (desiredAngle > 0) {
			RobotMap.right1.set(ControlMode.PercentOutput, -speed);
			RobotMap.left1.set(ControlMode.PercentOutput, speed);
		} else {
			RobotMap.right1.set(ControlMode.PercentOutput, speed);
			RobotMap.left1.set(ControlMode.PercentOutput, -speed);
		}

		if (error < RobotMap.TURN_ERROR_CONSTANT && error > -RobotMap.TURN_ERROR_CONSTANT) {
			return true;
		}
		return false;
	}

	public double getLeftEncoderPosition() {
		return (RobotMap.left1.getSelectedSensorPosition(0));
	}

	public double getRightEncoderPosition() {
		// Make sure graph isn't upside down (The stocks are going into the
		// toilet!!)
		return (RobotMap.right1.getSelectedSensorPosition(0));
	}

	public double getLeftEncoderVelocity() {
		return -RobotMap.left1.getSelectedSensorVelocity(0);
	}

	public double getRightEncoderVelocity() {
		// Make sure graph isn't upside down (The stocks are going into the
		// toilet!!)
		return (RobotMap.right1.getSelectedSensorVelocity(0));
	}

	@Override
	public void periodic() {
		// Put code here to be run every loop
	}

	public void stop() {
		RobotMap.left1.set(ControlMode.PercentOutput, 0);
		RobotMap.right1.set(ControlMode.PercentOutput, 0);
	}

	public boolean isReversed() {
		return reversed;
	}

	public void switchFront() {
		// RobotMap.left1.setInverted(!RobotMap.left1.getInverted());
		// RobotMap.left2.setInverted(!RobotMap.left2.getInverted());
		// RobotMap.left3.setInverted(!RobotMap.left3.getInverted());
		// RobotMap.right1.setInverted(!RobotMap.right1.getInverted());
		// RobotMap.right2.setInverted(!RobotMap.right2.getInverted());
		// RobotMap.right3.setInverted(!RobotMap.right3.getInverted());
		reversed = !reversed;

	}

	public void resetEncoders() {
		RobotMap.left1.setSelectedSensorPosition(0, 0, 10);
		RobotMap.right1.setSelectedSensorPosition(0, 0, 10);
	}
}
