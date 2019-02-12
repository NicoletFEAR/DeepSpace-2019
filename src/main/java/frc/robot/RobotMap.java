// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.subsystems.AirCompressor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {


	//Talons for Drive Base
    public static TalonSRX left1;
	public static TalonSRX left2;
	public static TalonSRX left3;
	
    public static TalonSRX right1;
	public static TalonSRX right2;
	public static TalonSRX right3;
	
	//Talons for Game Mech
	public static TalonSRX flywheel1;
	public static TalonSRX flywheel2;
	
	//Talons for Arm
    public static TalonSRX armMotor1;
	public static TalonSRX armMotor2;
	
	//Talons for Lift System
    public static TalonSRX supportWheel1;
	public static TalonSRX supportWheel2;

    public static DifferentialDrive robotDrive;
	
	//PID Constants
	public static double ERROR_CONSTANT_LEFT = 0.0;
	public static double ERROR_CONSTANT_RIGHT = 0.0;
	public static double WHEEL_RADIUS = 3.125; //Inches
	public static double WHEEL_SEPARATION = 5.0; //Inches
	public static double DISTANCE_BETWEEN_TRACKS = 20.5; //Inches
	public static double fudgeFactor = 1.0;
	public static double WHEEL_TICKS_PER_REVOLUTION = 7610;
    public static final double DRIVE_kP = 0;
    public static final double DRIVE_kI = 0;
	public static final double DRIVE_kD = 0;
	public static final double DRIVE_ERROR_CONSTANT = 0;
	public static final double TURN_kP = 0.02;
	public static final double TURN_kI = 0.0001;
	public static final double TURN_kD = 0;
	public static final double TURN_ERROR_CONSTANT = 0;
	public static final double PERFECT_ARC_RANGE = 2;
	
	//variables for arm movement
	public static int maxChangeAmt = 100;
	public static int targetEncoderValue;
	public static final int CargoShipDropPoint = 100;
	public static final int CargoLoadingStation = 100;
	public static final int CargoLevel1TargetValue = -100;
	public static final int CargoLevel2TargetValue = 200;
	public static final int CargoLevel3TargetValue = 300;
	public static final int HatchLevel1TargetValue = 400;
	public static final int HatchLevel2TargetValue = 500;
	public static final int HatchLevel3TargetValue = 600;
	public static final double ARM_kP=0.0005;
    public static final double ARM_kI=0.0005;
    public static final double ARM_kD=0.0000000;
	public static double ARM_TICKS_PER_REVOLUTION = 4096.0; //Needs to be updated on final bot!!!!!!!!!!!!!!!!!!!!!!!!
	public static double ARM_MAX_TICK_VAL = 2000;
	public static double ARM_MIN_TICK_VAL = -2000;
	
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static AnalogPotentiometer playKnob;
	public static AnalogPotentiometer positionKnob;

	// VISION CONSTANTS:
	public static double angleConstant = 50;
	public static double distanceConstant = 1.5;
	public static DigitalInput limitSwitch1;
	public static DigitalInput limitSwitch2;
	// ultrasonic sensors
	public static AnalogInput ultraLeft;
	public static AnalogInput ultraRight;
	// Pneumatics
	public 	static AirCompressor airCompressor;
    public  static int aModuleNumber = 0;
	public  static int aForwardChannel = 2;
	public  static int aReverseChannel = 1;
	
	public  static int bModuleNumber = 0;
	public  static int bForwardChannel = 5;
	public  static int bReverseChannel = 0;
	
	public  static int cModuleNumber = 0;
	public  static int cForwardChannel = 4;
	public  static int cReverseChannel = 3;
	
	public  static int dModuleNumber = 0;
	public  static int dForwardChannel = 6;
	public  static int dReverseChannel = 7;
	
	public  static int compressormodule =0;
	
	public static void init() {
		targetEncoderValue=0;
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		left1 = new TalonSRX(15);
		left2 = new TalonSRX(13);
		left3 = new TalonSRX(17);

		left1.setInverted(false);
		left2.follow(left1);
		left3.follow(left1);

		right1 = new TalonSRX(16);
		right2 = new TalonSRX(14);
		right3 = new TalonSRX(18);

		right1.setInverted(false);
		right2.follow(right1);
		right3.follow(right1);
		
		flywheel1 = new TalonSRX(25); // cargo handlers
		flywheel2 = new TalonSRX(26);
		
		flywheel1.setInverted(true); // two flywheels must be opposite to intake and shoot cargo
		flywheel2.setInverted(false);

		//next 2 need to be switched back to 21/22, 
		//using them for PID testing
		armMotor1 = new TalonSRX(24);
		armMotor1.setInverted(false);
		armMotor1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10); // new frame ever X miliseconds
		armMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		armMotor1.setSelectedSensorPosition(0);
		armMotor2 = new TalonSRX(23);
		armMotor2.setInverted(true);
		// armMotor2.setInverted(InvertType.FollowMaster);
		// armMotor2.setInverted(false);

		supportWheel1 = new TalonSRX(21);
		supportWheel2 = new TalonSRX(22);	

		airCompressor= new AirCompressor(compressormodule);

		limitSwitch1 = new DigitalInput(0);
		limitSwitch2 = new DigitalInput(1);

		ultraRight= new AnalogInput(3);
		ultraLeft = new AnalogInput(2);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
