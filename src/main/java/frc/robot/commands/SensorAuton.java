package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Sensor;
//import frc.robot.subsystems.SensorTwo;
import frc.robot.subsystems.XRPDrivetrain;

public class SensorAuton extends Command {
    
    private final XRPDrivetrain m_drivetrain;
    private double m_leftMotorSpeed = 1;
    private double m_rightMotorSpeed = 1;

    public SensorAuton(XRPDrivetrain drivetrain){
        m_drivetrain = drivetrain;
        
    }

    public void initialize(){

        Commands.sequence(
            Commands.race(
                Commands.run(() -> m_drivetrain.tankDrive(m_rightMotorSpeed, m_leftMotorSpeed), m_drivetrain)
            )
        ).schedule();

    }
}
