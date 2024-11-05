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
    private final Sensor m_sensor;

    public SensorAuton(XRPDrivetrain drivetrain, Sensor sensor){
        m_drivetrain = drivetrain;
        m_sensor = sensor;
    }

    public void initialize(){

        BooleanSupplier color = () -> m_sensor.colorBlack();
        Trigger isBlack = new Trigger(color);
        isBlack
                .onTrue(
                    Commands.sequence(
                        Commands.runOnce(() -> m_leftMotorSpeed = 0),
                        Commands.runOnce(() -> m_rightMotorSpeed = 0)
                    )
            );

        Commands.sequence(
            Commands.race(
                Commands.run(() -> m_drivetrain.tankDrive(m_rightMotorSpeed, m_leftMotorSpeed), m_drivetrain),
                Commands.waitUntil(isBlack)
            )
        ).schedule();

    }
}
