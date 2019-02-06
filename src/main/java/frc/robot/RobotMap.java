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

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
    public static TalonSRX frontLeft;
	public static TalonSRX midLeft;
	public static TalonSRX backLeft;
	
    public static TalonSRX frontRight;
	public static TalonSRX midRight;
	public static TalonSRX backRight;
	
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
	public static double fudgeFactor = 1.0;
	public static double TICKS_PER_REVOLUTION = 7000.0; //Needs to be updated on final bot!!!!!!!!!!!!!!!!!!!!!!!!
	public static double ARM_MAX_TICK_VAL = 4000;
	
	//variables for arm movement
	public static int encoderValue;
	public static int offset;
	
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static AnalogPotentiometer playKnob;
	public static AnalogPotentiometer positionKnob;
	
	// Pneumatics
	public 	static AirCompressor airCompressor;
    public  static int aModuleNumber = 0;
	public  static int aForwardChannel = 2;
	public  static int aReverseChannel = 1;
	
	public  static int bModuleNumber = 0;
	public  static int bForwardChannel = 5;
	public  static int bReverseChannel = 0;
	
	public  static int cModuleNumber = 0;
	public  static int cForwardChannel = 3;
	public  static int cReverseChannel = 6;
	
	public  static int dModuleNumber = 0;
	public  static int dForwardChannel = 7;
	public  static int dReverseChannel = 4;
	
	public  static int compressormodule =0;
	
	public static void init() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		frontLeft = new TalonSRX(14);
		midLeft = new TalonSRX(16);
		backLeft = new TalonSRX(18);

		frontLeft.setInverted(false);
		midLeft.follow(frontLeft);
		backLeft.follow(frontLeft);

		frontRight = new TalonSRX(13);
		midRight = new TalonSRX(15);
		backRight = new TalonSRX(17);

		frontRight.setInverted(true);
		midRight.follow(frontRight);
		backRight.follow(frontRight);
		
		flywheel1 = new TalonSRX(19);
		flywheel2 = new TalonSRX(20);

		armMotor1 = new TalonSRX(13);
		armMotor2 = new TalonSRX(14);

		supportWheel1 = new TalonSRX(23);
		supportWheel2 = new TalonSRX(24);	

		airCompressor= new AirCompressor(compressormodule);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
