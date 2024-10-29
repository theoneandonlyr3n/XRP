package frc.robot.commands;
import frc.robot.subsystems.XRPDrivetrain;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;

public class Auton extends Command{

    private final XRPDrivetrain m_drive;

    public Auton(XRPDrivetrain xrpDrivetrain){
        m_drive = xrpDrivetrain;
    }

    public void initialize(){

        Commands.sequence(
            Commands.race(
                Commands.run(() -> m_drive.tankDrive(0.7, 0.7), m_drive),
                Commands.waitSeconds(1.5)
            ),
            
            Commands.race(
                Commands.run(() -> m_drive.tankDrive(0.5,-0.5), m_drive),
                Commands.waitSeconds(3)
            ),

            Commands.race(
                Commands.run(() -> m_drive.tankDrive(0.7, 0.7), m_drive),
                Commands.waitSeconds(1.5)
            )

                
        ).schedule();
    }
}

