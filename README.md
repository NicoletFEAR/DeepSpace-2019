# DeepSpace-2019
Nicolet FEAR 2019 code for FIRST Deep Space
# Pathfinder
Pathfinder uses Waypoints to calculate an efficient curve for the robot to follow. It follows the path using encoders and the navX. Pathfinder is officially supported by wpilib and can be found at https://github.com/JacisNonsense/Pathfinder.

Pathfinder currently runs out of the command PathfinderRun. https://github.com/NicoletFEAR/DeepSpace-2019/blob/RecordPlayback/src/main/java/frc/robot/commands/PathfinderRun.java

It creates an array of waypoints. Waypoints are formatted x, y, angle. It is in relation to the starting position and orientation when the path is initiated, however, the robot may rotate as its first action to point into the curve. x is forward, y is right, and the angle is radians clockwise. Use Pathfinder.d2r() to easily convert degrees to radians.

In initialize() the program fills the array of waypoints and calculates the trajectory. The trajectory can be calculated with high samples, which takes up to 10 seconds, or fast sampling, which is almost instant. After generating a trajectory, a TankModifier with the width of the drive base calculates a left and right trajectory. A SwerveModifier can also be used. The swerve modifier lets you control both the orientation and movement of the robot smoothly and independently.

In the execute() of the command, Pathfinder uses the left and right encoders to determine how far the robot is through the path and calculate the speeds for each side of the drive base. It also factors in the gyro heading from a navX to add extra turning when necessary. 

The command requires driveTrain and is set to be interruptible so the driver can cancel the path at any time by pressing the B button, which starts the OpenLoopDrive command.

Right now there are just some test values in the Waypoint array in the command, but vision targets or other values can also be supplied to the array.

https://docs.google.com/document/d/1DZEHLV6P56jvX4aVpAD_uKAC9apg6VKxsEyatxCNWf0/edit?usp=sharing - Nicolet FEAR Member Documentation
