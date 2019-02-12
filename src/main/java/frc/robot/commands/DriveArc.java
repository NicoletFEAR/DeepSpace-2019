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
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import frc.robot.RobotMap;
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
    int count;
    double x;
    double y;
    double theta;
    double circR;
    double circL;
    double integralL = 0;
    double integralR = 0;
    double previousErrorL = 0;
    double previousErrorR = 0;
    double previousDesiredtargetEncoderValue = -10;
    boolean completeL;
    boolean completeR;
    

    public DriveArc() {
        // requires(Robot.driveTrain);
        this(48,48,45);
    }

    public DriveArc(double x, double y, double theta){
        requires(Robot.driveTrain);
        this.x=x;
        this.y=y;
        this.theta=theta;
    }

    public double TicksToRevolution(double numberOfTicks){
        //System.out.println("TicksToRevolution()");
        double PercentRotation = numberOfTicks / RobotMap.WHEEL_TICKS_PER_REVOLUTION;
        return PercentRotation;
    } 

    public double RevolutionsToInches(double PercentRotation){
        //System.out.println("RevolutionToInches()");
        double DistanceTraveled = (2 * Math.PI * RobotMap.WHEEL_RADIUS) * PercentRotation;
        return DistanceTraveled;
    } 

    //Arc length should be in inches
    public boolean arcDrive(double arcLength, TalonSRX talon){
        //System.out.println("arcDrive()");
        // SmartDashboard.putNumber("", );  
        double error; 
        double derivative;
        double integral;
        if(count%2==0){
            error = arcLength - RevolutionsToInches(TicksToRevolution(Robot.driveTrain.getLeftEncoderPosition()));
            integralL+=error*.02;
            integral = integralL;
            derivative =  (error-previousErrorL)/.02;
            previousErrorL=error;
        }else{
            error = arcLength - RevolutionsToInches(TicksToRevolution(Robot.driveTrain.getRightEncoderPosition()));
            integralR+=error*.02;
            integral = integralR;
            derivative =  (error-previousErrorR)/.02;
            previousErrorR=error;
        }
        count++;
        double speed = RobotMap.DRIVE_kP*error + RobotMap.DRIVE_kI*integral + RobotMap.DRIVE_kD*derivative;
        talon.set(ControlMode.PercentOutput, speed);
        
        if(error == 0) return true;
		return false;
    }
    
    @Override
    protected void initialize() {
        // System.out.println(" initialize()");
        count = 0;
        Robot.driveTrain.resetEncoders();
        //calculate the distance that the different sides of 
        //the robot have to travel during the arc.
        double w = 20.5;
        double r0 = y/Math.sin(theta);
        double cLeft = 0;
        double cRight = 0;
        double rLeft = 0;
        double rRight = 0;
        //this is saying that you are turning right
        //cricR is the radius of the inner circl
        if (x > 0){
            rRight = r0 - (1/2)*w;
            rLeft = r0 + (1/2)*w;
        }else{
            rRight = r0 + (1/2)*w;
            rLeft = r0 - (1/2)*w;
        }
        
        cRight = rRight*Math.PI*2;
        cLeft = rLeft*Math.PI*2;

        circR = cRight*(theta/360);
        circL = cLeft*(theta/360);

        completeL=false;
        completeR=false;        
    }

    @Override
    protected void execute() {	
        // //System.out.println("execute()");
        if(!completeL) completeL = arcDrive(circL, RobotMap.left1);
        if(!completeR) completeR = arcDrive(circR, RobotMap.right1);
        // completeL = Robot.driveTrain.DriveArc(circL, RobotMap.frontLeft);
        // completeR = Robot.driveTrain.DriveArc(circR, RobotMap.frontRight);
    }

    @Override
    protected boolean isFinished() {
        return completeL && completeR;
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
