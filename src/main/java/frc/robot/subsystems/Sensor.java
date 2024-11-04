package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.function.DoubleSupplier;



public class Sensor {

    private final AnalogInput leftSensor = new AnalogInput(0);
    private final AnalogInput rightSensor = new AnalogInput(1);

    private final XRPDrivetrain m_drive = new XRPDrivetrain();

    private final DoubleSupplier leftVoltage = () -> leftSensor.getVoltage();
    private final DoubleSupplier rightVoltage = () -> rightSensor.getVoltage();

    


    











    

    
}
