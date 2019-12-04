package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SwitchFront extends InstantCommand {
	public SwitchFront() {
        super();
    	//requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	//Flip direction of travel
        Robot.driveTrain.switchFront();

    }
}
