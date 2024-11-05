package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;


import java.util.function.DoubleSupplier;



public class Sensor {

    private final AnalogInput leftSensor = new AnalogInput(0);

    private final DoubleSupplier leftVoltage = () -> leftSensor.getVoltage();

    public boolean colorBlack(){
        if (leftVoltage.getAsDouble() >= 4.8 ){
            return true;
        }
        return false;
    }

    
 











    

    
}
