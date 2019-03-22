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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ButtonOrganizer;
import frc.robot.commands.DecrementCap;
import frc.robot.commands.DriveArc;
import frc.robot.commands.FlyWheelSetSpeed;
import frc.robot.commands.GameMechClose;
import frc.robot.commands.GameMechOpen;
import frc.robot.commands.IncrementCap;
import frc.robot.commands.MoveToLevel;
import frc.robot.commands.SwitchFront;
import frc.robot.commands.ToggleArmPID;
import frc.robot.commands.ToggleCompressor;
import frc.robot.commands.ToggleGameMech;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // private JoystickButton leftJoystickButton1;
    // private Joystick leftJoystick;
    // private JoystickButton rightJoystickButton1;
    // private Joystick rightJoystick;
    // private Button xbox1StartButton;

    public XboxController xbox1; // the drive controller // input 0 on driver station
    private Button xbox1LeftStick;
    private Button xbox1RightStick;
    private Button xbox1LBumper;
    private Button xbox1RBumper;
    private Button xbox1Start;
    private Button xbox1Back;
    private Button xbox1X;
    private Button xbox1Y;
    private Button xbox1B;
    private Button xbox1A;

    private XboxController xbox2; // the game mech controller // input 1 on driver station
    private Button xbox2LeftStick;
    private Button xbox2RightStick;
    private Button xbox2LBumper;
    private Button xbox2RBumper;
    private Button xbox2Start;
    private Button xbox2Back;
    private Button xbox2X;
    private Button xbox2Y;
    private Button xbox2B;
    private Button xbox2A;

    public static boolean visionOn;

    private Joystick armLevelController; // the box button controller // input 2 on driver station
    private Button CargoLevel1;
    private Button CargoLevel2;
    private Button CargoLevel3;
    private Button HatchLevel1;
    private Button HatchLevel2;
    private Button HatchLevel3;
    private Button Vertical;
    private Button CargoShipCargo;
    private Button LoadingStation;
    private Button CargoFloor;

    public OI() {
        visionOn = false;

        // rightJoystick = new Joystick(1);
        // rightJoystickButton1 = new JoystickButton(rightJoystick, 1);
        // rightJoystickButton1.whileHeld(new SwitchFront());

        // leftJoystick = new Joystick(0);
        // leftJoystickButton1 = new JoystickButton(leftJoystick, 1);
        // leftJoystickButton1.whileHeld(new SwitchFront());

        // create our buttons
        xbox1 = new XboxController(0);

        xbox1A = new JoystickButton(xbox1, 1);
        xbox1B = new JoystickButton(xbox1, 2);
        xbox1X = new JoystickButton(xbox1, 3);
        xbox1Y = new JoystickButton(xbox1, 4);
        xbox1LBumper = new JoystickButton(xbox1, 5);
        xbox1RBumper = new JoystickButton(xbox1, 6);
        xbox1Back = new JoystickButton(xbox1, 7);
        xbox1Start = new JoystickButton(xbox1, 8);
        xbox1LeftStick = new JoystickButton(xbox1, 9);
        xbox1RightStick = new JoystickButton(xbox1, 10);

        xbox2 = new XboxController(1);

        xbox2A = new JoystickButton(xbox2, 1);
        xbox2B = new JoystickButton(xbox2, 2);
        xbox2X = new JoystickButton(xbox2, 3);
        xbox2Y = new JoystickButton(xbox2, 4);
        xbox2LBumper = new JoystickButton(xbox2, 5);
        xbox2RBumper = new JoystickButton(xbox2, 6);
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
        LoadingStation = new JoystickButton(armLevelController, 9);
        CargoFloor = new JoystickButton(armLevelController, 10);

        // Drive Controller Command Mapping
        xbox1X.whenPressed(new SwitchFront());
        xbox1Back.whenPressed(new ToggleArmPID());
        CargoLevel1.whileHeld(new ButtonOrganizer(1));
        CargoLevel2.whileHeld(new ButtonOrganizer(2));
        CargoLevel3.whileHeld(new ButtonOrganizer(3));
        HatchLevel1.whileHeld(new ButtonOrganizer(4));
        HatchLevel2.whileHeld(new ButtonOrganizer(5));
        HatchLevel3.whileHeld(new ButtonOrganizer(6));
        CargoShipCargo.whileHeld(new ButtonOrganizer(7));
        LoadingStation.whileHeld(new ButtonOrganizer(9));
        CargoFloor.whileHeld(new ButtonOrganizer(8));
        // xbox1X.whenPressed(new Shift());
        // xbox1Y.whenPressed(new TurnToAngle(90));
        //xbox1Y.whenPressed(new DriveArc(-24, 48, 60));

        // xbox1A.whenPressed(new DriveArc());
        // xbox1LeftStick.whenPressed(new SwitchFront());
        // xbox1LeftStick.whenPressed(new SwitchAndroidCamera());
        // xbox1RightStick.whenPressed(new SwitchFront());
        // xbox1RightStick.whenPressed(new SwitchAndroidCamera());

        // xbox1X.whenPressed(new Shoot());
        // xbox1B.whenPressed(new DriveArc());

        // xbox1A.whenPressed(new CargoLevel1());

        // Mech Controller Command Mapping

        /* Old Game Mech Mapping */
        // xbox2Y.whenPressed(new GameMechClose());
        // xbox2B.whenPressed(new GameMechOpen());
        // xbox2A.whenPressed(new FlyWheelSetSpeed(.5));
        // xbox2RBumper.whenPressed(new IncrementCap());
        // xbox2LBumper.whenPressed(new DecrementCap());
        // xbox2Start.whenPressed(new ToggleCompressor());

        xbox2A.whenPressed(new MoveToLevel(7)); // Move to 45 degrees
        xbox2B.whenPressed(new ToggleGameMech());
        xbox2X.whenPressed(new MoveToLevel(1)); // Should be LS Cargo, instead Cargo Level 1
        xbox2RBumper.whenPressed(new IncrementCap());
        xbox2LBumper.whenPressed(new DecrementCap());
        xbox2Start.whenPressed(new MoveToLevel(8)); // Move straight up

        // xbox2B.whenPressed(new MoveToLevel(1));
        // xbox2A.whenPressed(new MoveToLevel(4));

        // Box Controller Command Mapping
       
        Vertical.whenPressed(new MoveToLevel(8));
        // CargoFloor.whenPressed(new MoveToLevel(9));

        xbox1Y.whenPressed(new MoveToLevel(13));
    }

    public XboxController getXbox1() {
        return xbox1;
    }

    public XboxController getXbox2() {
        return xbox2;
    }
}
