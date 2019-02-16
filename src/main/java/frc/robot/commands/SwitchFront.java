package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class SwitchFront extends InstantCommand {

	public SwitchFront() {
        super();
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	//Flip direction of travel
        Robot.driveTrain.switchFront();
    	//Flip left and right
        //Robot.oi.switchJoystickIDs();
    }
}
