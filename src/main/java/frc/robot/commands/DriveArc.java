// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import frc.robot.sub//Systems.DriveTrain;
import frc.robot.RobotMap;

// import com.ctre.phoenix.motorcontrol.ControlMode;

// import frc.robot.OI;
//import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.GenericHID.Hand;
/**
 *
 */
public class DriveArc extends Command {
    double x;
    double y;
    double theta;

    double z;
    double r;

    double circR;
    double circL;

    double error;
    double speed;
    double derivative;
    double integral;
    double previousError;

    boolean complete;
    boolean left;

    public DriveArc() {
        // requires(Robot.driveTrain);
        this(-24, 48, 60);
    }

    public DriveArc(double x, double y, double theta) {
        requires(Robot.driveTrain);
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public double TicksToRevolution(double numberOfTicks) {
        // System.out.println("TicksToRevolution()");
        double percentRotation = numberOfTicks / RobotMap.WHEEL_TICKS_PER_REVOLUTION;
        return percentRotation;
    }

    public double RevolutionsToInches(double percentRotation) {
        // System.out.println("RevolutionToInches()");
        double distanceTraveled = (2 * Math.PI * RobotMap.WHEEL_RADIUS) * percentRotation;
        return distanceTraveled;
    }

    // Arc length should be in inches
    public boolean arcDrive(double arcLength, TalonSRX talon) {
        // SmartDashboard.putNumber("", );
        // System.out.print("HI");

        double currentLocation = RevolutionsToInches(TicksToRevolution(talon.getSelectedSensorPosition()));

        SmartDashboard.putNumber("Target", arcLength);
        SmartDashboard.putNumber("currentDistance", currentLocation);

        error = arcLength - currentLocation;
        integral += error * .02;
        derivative = (error - previousError) / .02;
        previousError = error;

        speed = RobotMap.DRIVE_kP * error + RobotMap.DRIVE_kI * integral + RobotMap.DRIVE_kD * derivative;
        if (left)
            speed = -speed;
        talon.set(ControlMode.PercentOutput, speed);

        if (Math.abs(currentLocation) > Math.abs(arcLength))
            return true;
        return false;
    }

    // protected void rotateDegrees(double degrees){
    // double error;
    // //
    // // SmartDashboard.putNumber("original angle", degrees);
    // degrees = degrees<0?(degrees%360):-(degrees%360);
    // // SmartDashboard.putNumber("Desired angle", degrees);
    // do{
    // error = degrees-Robot.navX.getAngle();
    // double speed = RobotMap.TURN_kP*error;
    // RobotMap.left1.set(ControlMode.PercentOutput, -speed);
    // RobotMap.right1.set(ControlMode.PercentOutput, -speed);
    // // SmartDashboard.putNumber("CurrentAngle", Robot.navX.getAngle());
    // }while(error<-RobotMap.PERFECT_ARC_RANGE ||
    // error>RobotMap.PERFECT_ARC_RANGE);
    // }

    @Override
    protected void initialize() {
        complete = false;
        Robot.navX.reset();
        double copyOfX = x;
        if (x < 0) {
            x = -x;
            // theta = -theta;
        }
        double prefferedLength = y / Math.cos(Math.toRadians(theta));
        double currentLength = Math.sqrt(x * x + y * y);
        /*
         * double radius1 = y/Math.sin(Math.toRadians(theta)); double radius2 =
         * x+y/Math.tan(Math.toRadians(theta));
         * if(radius1>radius2-RobotMap.PERFECT_ARC_RANGE &&
         * radius1<radius2+RobotMap.PERFECT_ARC_RANGE){ //already in position //execute
         * already perfect arc
         * 
         * }else
         */
        boolean turnBool = false;
        if (currentLength > prefferedLength) {
            // rotate to the right, arcing to the left
            z = -2 * (Math.toDegrees(Math.tanh(x / y)) - theta);
            double degreesToRotate = theta + z;

            while (!turnBool) {
                turnBool = Robot.driveTrain.turnToAngle(degreesToRotate);

            }

            double radius = (1.0 / 2 * Math.sqrt(x * x + y * y)) / (Math.sin(Math.toRadians(.5 * z)));
            double leftRadius = radius - 1.0 / 2 * RobotMap.DISTANCE_BETWEEN_TRACKS;
            double rightRadius = radius + 1.0 / 2 * RobotMap.DISTANCE_BETWEEN_TRACKS;

            circL = leftRadius * 2 * Math.PI * z / 360;
            circR = rightRadius * 2 * Math.PI * z / 360;
        } else {
            // rotate to the left, arcing to the right
            z = 2 * (theta - Math.toDegrees(Math.tanh(x / y)));
            double degreesToRotate = theta - z;

            while (!turnBool) {
                turnBool = Robot.driveTrain.turnToAngle(degreesToRotate);
            }

            double radius = (1.0 / 2 * Math.sqrt(x * x + y * y)) / (Math.sin(Math.toRadians(.5 * z)));
            double leftRadius = radius + 1.0 / 2 * RobotMap.DISTANCE_BETWEEN_TRACKS;
            double rightRadius = radius - 1.0 / 2 * RobotMap.DISTANCE_BETWEEN_TRACKS;

            circL = leftRadius * 2 * Math.PI * z / 360;
            circR = rightRadius * 2 * Math.PI * z / 360;
        }
        // System.out.println("Done turning");
        if (copyOfX != x) {
            double tmp = circL;
            circL = circR;
            circR = tmp;
        }

        Robot.driveTrain.resetEncoders();
        // SmartDashboard.putBoolean("Turning complete", true);

        if (Robot.driveTrain.isReversed()){
            circL = -circL;
            circR = -circR;
        }
    }

    double currentLocation;

    // Arc length should be in inches
    public void arcDriveRacing(double arcLength) {
        // SmartDashboard.putNumber("", );
        // System.out.print("HI");
        if (left)
            currentLocation = RevolutionsToInches(TicksToRevolution(Robot.driveTrain.getLeftEncoderPosition()));
        else
            currentLocation = RevolutionsToInches(TicksToRevolution(Robot.driveTrain.getRightEncoderPosition()));
        SmartDashboard.putNumber("Arc target", arcLength);
        SmartDashboard.putNumber("currentDistance", currentLocation);

        error = arcLength - currentLocation;
        integral += error * .02;
        derivative = (error - previousError) / .02;
        previousError = error;

        speed = RobotMap.DRIVE_kP * error + RobotMap.DRIVE_kI * integral + RobotMap.DRIVE_kD * derivative;
        if (Math.abs(error) < 10)
            complete = true;
        if (left)
            speed = -speed;
        // return speed;

        // if (Math.abs(currentLocation) > Math.abs(arcLength))
        // return true;
        // return false;
    }

    @Override
    protected void execute() {
        // //System.out.println("execute()");
        // Needs to be configured for switch front
        // if(Robot.oi.getXbox1().getBumperPressed(Hand.kRight))RobotMap.DRIVE_kP+=.01;
        // SmartDashboard.putNumber("P Val", RobotMap.DRIVE_kP);

        SmartDashboard.putNumber("CircL", circL);
        SmartDashboard.putNumber("CircR", circR);
        SmartDashboard.putNumber("Speed", speed);

        // if (circL > circR) {
        // left = true;
        // complete = arcDrive(circL, RobotMap.left1);
        // RobotMap.right1.set(ControlMode.PercentOutput, -speed * circR / circL);
        // } else {
        // left = false;
        // complete = arcDrive(circR, RobotMap.right1);
        // RobotMap.left1.set(ControlMode.PercentOutput, -speed* circL / circR);
        // }

        if (circL > circR) {
            left = true;
            arcDriveRacing(-circL);
            // double tmp = arcDriveRacing(circL);
            // complete = .05 > Math.abs(speed);
            // RobotMap.right1.set(ControlMode.PercentOutput, -speed * circR / circL);
            double leftSpeed = speed;
            double rightSpeed = speed*circR/circL;
            double average = (leftSpeed+rightSpeed)/2;
            Robot.driveTrain.RacingDrive(average, leftSpeed-average);
        } else {
            left = false;
            arcDriveRacing(circR);
            double rightSpeed = speed;
            double leftSpeed = speed*circR/circL;
            double average = (leftSpeed+rightSpeed)/2;
            Robot.driveTrain.RacingDrive(average, rightSpeed-average);
        }

    }

    @Override
    protected boolean isFinished() {
        return complete;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // sub//Systems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
