package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class SubsystemTestBase extends Subsystem implements Sendable
{
    private static int subsystemCount = 0;
    private static ArrayList<SubsystemTestBase> subsystems = new ArrayList<SubsystemTestBase>();

    private boolean failWithPassion;

    public SubsystemTestBase()
    {
        failWithPassion = false;
        subsystems.add(this);
        subsystemCount++;
    }

    public static SubsystemTestBase getSubsystem(int index)
    {
        return subsystems.get(index);
    }

    public static int getSubsystemCount()
    {
        return subsystemCount;
    }

    public void initSendable(SendableBuilder builder)
    {
        super.initSendable(builder);
        builder.addStringProperty("Subsystem Name: ", this::toString, null);
        builder.addBooleanProperty("Test fail stops all tests",
                                    this::getFailWithPassion,
                                    this::setFailWithPassion);
    }

    public abstract boolean subsystemTest(boolean advanced);

    private boolean getFailWithPassion()
    {
        return failWithPassion;
    }

    private void setFailWithPassion(boolean fail)
    {
        failWithPassion = fail;
    }
}