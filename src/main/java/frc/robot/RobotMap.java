package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import frc.robot.subsystems.AirCompressor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// DRIVE BASE:
	public static TalonSRX left1;
	public static TalonSRX left2;
	public static TalonSRX left3;

	public static TalonSRX right1;
	public static TalonSRX right2;
	public static TalonSRX right3;

	public static final double DRIVE_LIMITER = 1.0; // not 775 motors

	public static final double SHIFT_UP_THRESHOLD = 2300; // speed at which drive base shifts up
	public static final double SHIFT_DOWN_THRESHOLD = 2100; // speed at which drive base shifts down

	// CARGO HATCH GAME MECH:
	public static TalonSRX flywheel1;
	public static TalonSRX flywheel2;

	public static DigitalInput cargoIntakeLimitSwitch; // intake limit switch
	public static int CARGO_LIMIT_SWITCH_INPUT;

	public static final double FLYWHEEL_LIMITER = 1.0; // 775 motors

	// ARM:
	public static TalonSRX armMotor1;
	public static TalonSRX armMotor2;

	public static final double ARM_LIMITER = 0.4; // not 775 motors

	public static double ARM_MOTOR_SLOW_FORWARDS = 0.928; // slow the faster motor to match the slower 
	public static double ARM_MOTOR_SLOW_BACKWARDS = 0.967; // slow the faster motor to match the slower

	// LIFT SYSTEM:
	public static TalonSRX supportWheel1;
	public static TalonSRX supportWheel2;

	public static final double SUPPORT_LIMITER = 1.0; // not 775 motors

	public static DifferentialDrive robotDrive;

	// COMPRESSOR:
	public static Integer PRESSURE_TOO_LOW_VALUE = 60;
	public static Integer PRESSURE_TOO_HIGH_VALUE = 100;

	// PID CONSTANTS:
	public static double ERROR_CONSTANT_LEFT = 0.0;
	public static double ERROR_CONSTANT_RIGHT = 0.0;
	public static double WHEEL_RADIUS = 2.0; // Inches // UDB:2 // FCB:4
	public static double WHEEL_SEPARATION = 5.0; // Inches
	public static double DISTANCE_BETWEEN_TRACKS = 20.0; // Inches // FCB:20.5 // UDB:20
	public static double fudgeFactor = 1.0;
	public static double WHEEL_TICKS_PER_REVOLUTION = 7610;
	public static double DRIVE_kP = 0.03;
	public static final double DRIVE_kI = 0;
	public static final double DRIVE_kD = 0;
	public static final double DRIVE_ERROR_CONSTANT = 0;
	public static final double TURN_kP = 0.04;
	public static final double TURN_kI = 0;
	public static final double TURN_kD = 0.001;
	public static final double TURN_ERROR_CONSTANT = 1; // degrees the turn can be off
	public static final double PERFECT_ARC_RANGE = 2;

	// ARM MOVEMENT CONSTANTS:
	public static int maxChangeAmt = 100; // how much the arm moves by
	public static int targetEncoderValue;
	public static final int CargoShipDropPoint = -100;
	public static final int CargoLevel1TargetValue = -1200; //-2000
	public static final int CargoLevel2TargetValue = -720; //-1200;
	public static final int CargoLevel3TargetValue = -540; //-900;
	public static final int HatchLevel1TargetValue = -1320; //-2200;
	public static final int HatchLevel2TargetValue = -840; //-1400;
	public static final int HatchLevel3TargetValue = -660; //-1100;
	public static final int CargoLoadingStation = -720; //-1200;
	public static final int CargoFloor = -2100; //-3500;
	public static final int StraightUp = 0;
	public static final double ARM_kP = 0.003;
	public static final double ARM_kI = 0.0000;// 5; //0.0005 too fast
	public static final double ARM_kD = 0.00000;// 75; // 0.000015 too fast
	public static double ARM_TICKS_PER_REVOLUTION = 4096.0; // Needs to be updated on final bot!!!!!!!!!!!!!!!!!!!!!!!!
	public static int ARM_MAX_TICK_VAL = 2750;
	public static int ARM_MIN_TICK_VAL = -2750;
	public static int offset = 0;
	//public static double armSpeedLimit = 1; // max speed we want arm motors to go to

	// VISION CONSTANTS:
	public static double angleConstant = 50;
	public static double distanceConstant = 1.5;
	// // ultrasonic sensors
	// public static AnalogInput ultraLeft;
	// public static AnalogInput ultraRight;
	// Pneumatics
	// public static AirCompressor airCompressor;

	public static int aModuleNumber = 0; // GAME MECH
	public static int aForwardChannel = 6;
	public static int aReverseChannel = 1;

	public static int bModuleNumber = 0; // IDK
	public static int bForwardChannel = 7;
	public static int bReverseChannel = 0;

	public static int cModuleNumber = 0; // IDK
	public static int cForwardChannel = 4;
	public static int cReverseChannel = 3;

	public static int dModuleNumber = 0; // SHIFTER
	public static int dForwardChannel = 5;
	public static int dReverseChannel = 2;

	public static int compressormodule = 0;

	public static void init() {
		targetEncoderValue = 0;
		left1 = new TalonSRX(13);
		left1.setNeutralMode(NeutralMode.Brake);
		left2 = new TalonSRX(15);
		left2.setNeutralMode(NeutralMode.Brake);
		left3 = new TalonSRX(17);
		left3.setNeutralMode(NeutralMode.Brake);

		left1.setInverted(true);
		left2.setInverted(true);
		left3.setInverted(true);
		left2.follow(left1);
		left3.follow(left1);

		right1 = new TalonSRX(14);
		right1.setNeutralMode(NeutralMode.Brake);
		right2 = new TalonSRX(16);
		right2.setNeutralMode(NeutralMode.Brake);
		right3 = new TalonSRX(18);
		right3.setNeutralMode(NeutralMode.Brake);

		right1.setInverted(true);
		right2.setInverted(true);
		right3.setInverted(true);
		right2.follow(right1);
		right3.follow(right1);

		flywheel1 = new TalonSRX(25); // cargo handlers
		flywheel2 = new TalonSRX(26);

		flywheel1.setInverted(false); // two flywheels must be opposite to intake and shoot cargo
		flywheel2.setInverted(true);
		flywheel1.setNeutralMode(NeutralMode.Brake);
		flywheel2.setNeutralMode(NeutralMode.Brake);

		cargoIntakeLimitSwitch = new DigitalInput(CARGO_LIMIT_SWITCH_INPUT);

		// next 2 need to be switched back to 21/22,
		// using them for PID testing
		armMotor1 = new TalonSRX(24);
		armMotor1.setNeutralMode(NeutralMode.Brake);
		armMotor1.setInverted(false);
		armMotor1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10); // new frame ever X miliseconds
		armMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		armMotor1.setSelectedSensorPosition(0);
		armMotor2 = new TalonSRX(22); // SHOULD BE 22
		armMotor2.setNeutralMode(NeutralMode.Brake);
		armMotor2.setInverted(true);
		// armMotor2.setInverted(InvertType.FollowMaster);
		// armMotor2.setInverted(false);

		supportWheel1 = new TalonSRX(21);
		supportWheel1.setNeutralMode(NeutralMode.Coast);
		supportWheel2 = new TalonSRX(23);
		supportWheel2.setNeutralMode(NeutralMode.Coast);

		// airCompressor = new AirCompressor(compressormodule);

		// ultraRight = new AnalogInput(3);
		// ultraLeft = new AnalogInput(2);
	}
}
