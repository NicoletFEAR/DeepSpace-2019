/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Command creating and following a Path using Pathfinder
 */
public class PathfinderRun extends Command {


    double LOutput;
    double ROutput;

    EncoderFollower left;
    EncoderFollower right;
    Trajectory trajectory;
    int i;
    Trajectory.Segment counterSegment;

    public PathfinderRun() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {

        Robot.driveTrain.navX.reset();
        Robot.shifter.isOnPath = true;
        Robot.driveTrain.resetEncoders();

        i = 0;

        Waypoint[] points = new Waypoint[] {
            new Waypoint(-2, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
            new Waypoint(-1, 0, Pathfinder.d2r(-20)),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
            new Waypoint(0, 0, Pathfinder.d2r(20))                           // Waypoint @ x=0, y=0,   exit angle=0 radians
        };
        
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.02, RobotMap.max_velocity, 2.0, 60.0);
        trajectory = Pathfinder.generate(points, config);
      
        TankModifier modifier = new TankModifier(trajectory).modify(0.53);
      
      
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
      
        // Encoder Position is the current, cumulative position of your encoder. If you're using an SRX, this will be the
        // 'getEncPosition' function.
        // 7610 is the amount of encoder ticks per full revolution
        // Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters
        left.configureEncoder(RobotMap.left1.getSelectedSensorPosition(), 7610, RobotMap.wheel_diameter);
      
        // The first argument is the proportional gain. Usually this will be quite high
        // The second argument is the integral gain. This is unused for motion profiling
        // The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
        // The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
        //      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
        // The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
        left.configurePIDVA(0.1, 0.0, 0.0, 1 / RobotMap.max_velocity, 0);

        right.configureEncoder(RobotMap.right1.getSelectedSensorPosition(), 7610, RobotMap.wheel_diameter);
        right.configurePIDVA(0.1, 0.0, 0.0, 1 / RobotMap.max_velocity, 0);

        //prints:
        SmartDashboard.putNumber("traj leng", trajectory.length());

    }

    @Override
    protected void execute() {

        LOutput = left.calculate(-((int)(Robot.driveTrain.getLeftEncoderPosition())));
        ROutput = right.calculate((int)(Robot.driveTrain.getRightEncoderPosition()));

        double gyro_heading = -Robot.driveTrain.navX.getAngle();    // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

        // This allows the angle difference to respect 'wrapping', where 360 and 0 are the same value
        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        angleDifference = angleDifference % 360.0;
        if (Math.abs(angleDifference) > 180.0) {
            angleDifference = (angleDifference > 0) ? angleDifference - 360 : angleDifference + 360;
        }

        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        RobotMap.left1.set(ControlMode.PercentOutput, -(LOutput + turn));
        RobotMap.right1.set(ControlMode.PercentOutput,(ROutput - turn));

        i++;
        //counterSegment = left.getSegment();
        // can print seg details

        // prints:
        SmartDashboard.putNumber("traj step", i);

        SmartDashboard.putNumber("gyro head", gyro_heading);
        SmartDashboard.putNumber("gyro want", desired_heading);
        
        SmartDashboard.putNumber("ang dif", angleDifference);

        SmartDashboard.putNumber("turn", turn);
        SmartDashboard.putNumber("LOutput", LOutput);
        SmartDashboard.putNumber("ROutput", ROutput);


     }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        
        /* 
        if (i >= trajectory.length()) {
            Robot.shifter.isOnPath = false;
            return true;
        }
        */
        if (LOutput == 0 && ROutput == 0) {
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
        Robot.shifter.isOnPath = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.shifter.isOnPath = false;
    	end();
    }
}
//https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java