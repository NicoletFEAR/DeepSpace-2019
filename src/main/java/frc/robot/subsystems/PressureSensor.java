package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Provides a convenience method for getting robot pressure (Feel the burn!)
 */
public class PressureSensor extends Subsystem {
	public AnalogInput ai = new AnalogInput(0);

	public int getPressure() {
		double pressure = 250.0 * ai.getVoltage() / 5.0 - 25.0;
		return (int) pressure;
	}

	public void initDefaultCommand() {
	}
}