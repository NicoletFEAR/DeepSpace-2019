package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Drive Base:

	public static double wheel_diameter = 0.19; // in metres
	public static double max_velocity = 1.1; // in metres/s

	public static TalonSRX left1;
	public static TalonSRX left2;
	public static TalonSRX left3;

	public static TalonSRX right1;
	public static TalonSRX right2;
	public static TalonSRX right3;

	public static final double DRIVE_LIMITER = 0.75; // not 775 motors

	public static final double SHIFT_UP_THRESHOLD = 1750; // speed at which drive base shifts up //1600
	public static final double SHIFT_DOWN_THRESHOLD = 1750; // speed at which drive base shifts down //1700

	public static final double DRIVE_RAMP_RATE = 10; // 10 is recomended by our friends from 2015, need to test


	// PID Constants:
	public static double ERROR_CONSTANT_LEFT = 0.0;
	public static double ERROR_CONSTANT_RIGHT = 0.0;
	public static double WHEEL_RADIUS = 2.0; // Inches // UDB:2 // FCB:4
	public static double WHEEL_SEPARATION = 5.0; // Inches
	public static double DISTANCE_BETWEEN_TRACKS = 20; // Inches // FCB:20.5 // UDB:20
	public static double fudgeFactor = 1.0;
	public static double WHEEL_TICKS_PER_REVOLUTION = 7610;
	public static final double DRIVE_kP = 0.03;
	public static final double DRIVE_kI = 0;
	public static final double DRIVE_kD = 0;
	public static final double DRIVE_ERROR_CONSTANT = 0;
	public static final double TURN_kP = 0.04;
	public static final double TURN_kI = 0.001;
	public static final double TURN_kD = 0.001;
	public static final double TURN_ERROR_CONSTANT = 2; // degrees the turn can be off
	public static final double PERFECT_ARC_RANGE = 2;
    public static final double TURN_SCALING = 0.8; // was 0.6


	// Vision Constants:
	public static double angleConstant = 2400;
	public static double distanceConstant = 1.5;
	public static double x_val_constant = .95;
	public static double visionXAllowance = 14;

	// Arc Drive 2.0 Constants:
	public static double y_multiplier = 0.005; // multiplies the target output by the Android vision to robot driving output
	public static double x_multiplier = 0.025;
	public static double adjustmentAllowance = 1.5;
	public static double xMaxTurnSpeed = 0.25;

	public static int gmModuleNumber = 0; // GAME MECH
	public static int gmForwardChannel = 3;
	public static int gmReverseChannel = 4;

	public static int shiftModuleNumber = 0; // SHIFTER
	public static int shiftForwardChannel = 0;
	public static int shiftReverseChannel = 7;

	public static int compressormodule = 0;

	// Distance offsets for arc + angle drive
	public static double distOffset = 24;
	
	public static double speedMultiplier = 1;

	public static void init() {
		left1 = new TalonSRX(16);
		left1.setNeutralMode(NeutralMode.Brake);
		left2 = new TalonSRX(14);
		left2.setNeutralMode(NeutralMode.Brake);
		left3 = new TalonSRX(18);
		left3.setNeutralMode(NeutralMode.Brake);

		// Set how fast of a rate the robot will accelerate DO NOT remove or you
		// get a fabulous prize of a flipping robot - 2015
		left1.configClosedloopRamp(DRIVE_RAMP_RATE);
		left2.configClosedloopRamp(DRIVE_RAMP_RATE);
		left3.configClosedloopRamp(DRIVE_RAMP_RATE);

		left1.setInverted(true);
		left2.setInverted(true);
		left3.setInverted(true);
		left2.follow(left1);
		left3.follow(left1);

		right1 = new TalonSRX(13);
		right1.setNeutralMode(NeutralMode.Brake);
		right2 = new TalonSRX(15);
		right2.setNeutralMode(NeutralMode.Brake);
		right3 = new TalonSRX(17);
		right3.setNeutralMode(NeutralMode.Brake);

		// Set how fast of a rate the robot will accelerate DO NOT remove or you
		// get a fabulous prize of a flipping robot - 2015
		right1.configClosedloopRamp(DRIVE_RAMP_RATE);
		right2.configClosedloopRamp(DRIVE_RAMP_RATE);
		right3.configClosedloopRamp(DRIVE_RAMP_RATE);

		right1.setInverted(false);
		right2.setInverted(false);
		right3.setInverted(false);
		right2.follow(right1);
		right3.follow(right1);


		// next 2 need to be switched back to 21/22,
		// using them for PID testing
	
	}

	public static void setTalonMode(String mode) {
		if (mode.equalsIgnoreCase("coast")) {
			RobotMap.left1.setNeutralMode(NeutralMode.Coast);
			RobotMap.right1.setNeutralMode(NeutralMode.Coast);
			RobotMap.left2.setNeutralMode(NeutralMode.Coast);
			RobotMap.right2.setNeutralMode(NeutralMode.Coast);
			RobotMap.left3.setNeutralMode(NeutralMode.Coast);
			RobotMap.right3.setNeutralMode(NeutralMode.Coast);
			Robot.talonMode = "coast";
		} else if (mode.equalsIgnoreCase("brake")) {
			RobotMap.left1.setNeutralMode(NeutralMode.Brake);
			RobotMap.right1.setNeutralMode(NeutralMode.Brake);
			RobotMap.left2.setNeutralMode(NeutralMode.Brake);
			RobotMap.right2.setNeutralMode(NeutralMode.Brake);
			RobotMap.left3.setNeutralMode(NeutralMode.Brake);
			RobotMap.right3.setNeutralMode(NeutralMode.Brake);
			Robot.talonMode = "brake";
		}
	}
}
