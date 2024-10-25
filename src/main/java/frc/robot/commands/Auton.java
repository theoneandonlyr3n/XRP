package frc.robot.commands;
import frc.robot.subsystems.XRPDrivetrain;
import static edu.wpi.first.wpilibj2.command.Commands.*;
import edu.wpi.first.wpilibj2.command.Command;

public class Auton{

    private final XRPDrivetrain m_drive = new XRPDrivetrain();

    public Command basicAuton(){
         return sequence(
            runOnce(() -> m_drive.tankDrive(4,4))
         ).withName("Basic Auton");
    }
}
