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

//import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
// import frc.robot.subsystems.Lifter;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;//import edu.wpi.first.wpilibj.interfaces.Potentiometer;

//import org.usfirst.frc4786.RobotBuilderTest1.subsystems.*;
//import org.usfirst.frc4786.RobotBuilderTest1.subsystems.DriveTrain;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // private JoystickButton leftJoystickButton1;
    // private Joystick leftJoystick;
    // private JoystickButton rightJoystickButton1;
    // private Joystick rightJoystick;
    // private Button xbox1StartButton;

    public XboxController xbox1; // the drive controller // input 0 on driver station
    private Button xbox1LeftStick;
    private Button xbox1RightStick;
    private Button xbox1LTrig;
    private Button xbox1RTrig;
    private Button xbox1Start;
    private Button xbox1Back;
    private Button xbox1X;
    private Button xbox1Y;
    private Button xbox1B;
    private Button xbox1A;

    private XboxController xbox2; // the game mech controller // input 1 on driver station
    private Button xbox2LeftStick;
    private Button xbox2RightStick;
    private Button xbox2Start;
    private Button xbox2Back;
    private Button xbox2X;
    private Button xbox2Y;
    private Button xbox2B;
    private Button xbox2A;
    private Button xbox2LTrig;
    private Button xbox2RTrig;
    public static boolean visionOn;

    private Joystick armLevelController; // input 2 on driver station
    private Button CargoLevel1;
    private Button CargoLevel2;
    private Button CargoLevel3;
    private Button HatchLevel1;
    private Button HatchLevel2;
    private Button HatchLevel3;
    private Button Vertical;
    private Button CargoShipCargo;
    private Button CargoLoadingStation;
    private Button CargoFloor;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // rightJoystick = new Joystick(1);
        // rightJoystickButton1 = new JoystickButton(rightJoystick, 1);
        // rightJoystickButton1.whileHeld(new SwitchFront());

        // leftJoystick = new Joystick(0);
        // leftJoystickButton1 = new JoystickButton(leftJoystick, 1);
        // leftJoystickButton1.whileHeld(new SwitchFront());
        // xbox1StartButton = new JoystickButton(xbox1,8);

        // create our buttons
        xbox1 = new XboxController(0);

        xbox1A = new JoystickButton(xbox1, 1);
        xbox1B = new JoystickButton(xbox1, 2);
        xbox1X = new JoystickButton(xbox1, 3);
        xbox1Y = new JoystickButton(xbox1, 4);
        xbox1LTrig = new JoystickButton(xbox1, 5);
        xbox1RTrig = new JoystickButton(xbox1, 6);
        xbox1Back = new JoystickButton(xbox1, 7);
        xbox1Start = new JoystickButton(xbox1, 8);
        xbox1LeftStick = new JoystickButton(xbox1, 9);
        xbox1RightStick = new JoystickButton(xbox1, 10);

        xbox2 = new XboxController(1);

        xbox2A = new JoystickButton(xbox2, 1);
        xbox2B = new JoystickButton(xbox2, 2);
        xbox2X = new JoystickButton(xbox2, 3);
        xbox2Y = new JoystickButton(xbox2, 4);
        xbox2LTrig = new JoystickButton(xbox2, 5);
        xbox2RTrig = new JoystickButton(xbox2, 6);
        xbox2Back = new JoystickButton(xbox2, 7);
        xbox2Start = new JoystickButton(xbox2, 8);
        xbox2LeftStick = new JoystickButton(xbox2, 9);
        xbox2RightStick = new JoystickButton(xbox2, 10);

        armLevelController = new Joystick(2);
        CargoLevel1 = new JoystickButton(armLevelController, 1);
        CargoLevel2 = new JoystickButton(armLevelController, 2);
        CargoLevel3 = new JoystickButton(armLevelController, 3);
        HatchLevel1 = new JoystickButton(armLevelController, 4);
        HatchLevel2 = new JoystickButton(armLevelController, 5);
        HatchLevel3 = new JoystickButton(armLevelController, 6);
        Vertical = new JoystickButton(armLevelController, 7);
        CargoShipCargo = new JoystickButton(armLevelController, 8);
        CargoLoadingStation = new JoystickButton(armLevelController, 9);
        CargoFloor = new JoystickButton(armLevelController, 10);
        // tie commands to buttons
        // xbox1ButtonX.whenPressed(new Shift());

        xbox1X.whenPressed(new SwitchFront());
        xbox1X.whenPressed(new SwitchAndroidCamera());
        xbox1Y.whenPressed(new DriveArc(-24, 48, 45));

        // xbox1A.whenPressed(new DriveArc());
        // xbox1LeftStick.whenPressed(new SwitchFront());
        // xbox1LeftStick.whenPressed(new SwitchAndroidCamera());
        // xbox1RightStick.whenPressed(new SwitchFront());
        // xbox1RightStick.whenPressed(new SwitchAndroidCamera());
        visionOn = false;

        // xbox1X.whenPressed(new Shoot());
        // xbox1Y.whenPressed(new Lifting());
        // xbox1B.whenPressed(new DriveArc());

        // xbox1A.whenPressed(new CargoLevel1());

        xbox2Y.whenPressed(new GameMechClose());
        xbox2B.whenPressed(new GameMechOpen());
        xbox2A.whenPressed(new FlyWheelSetSpeed());
        // xbox2X.whenPressed(new DriveArc());

        xbox2RTrig.whenPressed(new IncrementCap());
        xbox2LTrig.whenPressed(new DecrementCap());
        // xbox2B.whenPressed(new MoveToLevel(1));
        // xbox2A.whenPressed(new MoveToLevel(4));

        
        CargoLevel1.whenPressed(new MoveToLevel(1));
        CargoLevel2.whenPressed(new MoveToLevel(2));
        CargoLevel3.whenPressed(new MoveToLevel(3));
        HatchLevel1.whenPressed(new MoveToLevel(4));
        HatchLevel2.whenPressed(new MoveToLevel(5));
        HatchLevel3.whenPressed(new MoveToLevel(6));
        Vertical.whenPressed(new MoveToLevel(8));
        CargoShipCargo.whenPressed(new MoveToLevel(7));
        CargoLoadingStation.whenPressed(new MoveToLevel(10));
        CargoFloor.whenPressed(new MoveToLevel(9));
    }

    public XboxController getXbox1() {
        return xbox1;
    }

    public XboxController getXbox2() {
        return xbox2;
    }
}
