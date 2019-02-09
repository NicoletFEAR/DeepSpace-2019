package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemTestBase extends Subsystem
{
    public static int subsystemCount;
    public static List<SubsystemTestBase> subsystems;

    public SubsystemTestBase()
    {
        subsystems.add(this);
        subsystemCount++;
    }

    public abstract boolean subsystemTest(boolean advanced);
}