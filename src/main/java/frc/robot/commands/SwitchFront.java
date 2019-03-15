package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;

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

        // if (Robot.cameraMode.equalsIgnoreCase("front")) {
        //     Robot.mVisionServer.backCamera();
        //     Robot.cameraMode = "back";
        //   } else {
        //     Robot.mVisionServer.frontCamera();
        //     Robot.cameraMode = "front";
        //   }

        if (Robot.driveTrain.isReversed()) {
            Robot.mVisionServer.frontCamera();
            Robot.cameraMode = "front";
        } else {
            Robot.mVisionServer.backCamera();
            Robot.cameraMode = "back";
        }

       // RobotMap.targetEncoderValue = -RobotMap.targetEncoderValue; // makes the arm stay at its current height when switching front

    	//Flip left and right
        //Robot.oi.switchJoystickIDs();
    }
}
