package frc.robot.commands;
import frc.robot.subsystems.XRPDrivetrain;
import static edu.wpi.first.wpilibj2.command.Commands.*;
import edu.wpi.first.wpilibj2.command.Command;

public class Auton extends Command{

    private final XRPDrivetrain m_drive = new XRPDrivetrain();

    public Command basicAuton(){
         return sequence(
            race(
                new TankDrive(m_drive, () -> 4.0, () -> 4.0),
                waitSeconds(4)
            )
         ).withName("Basic Auton");
    }
}