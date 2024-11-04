package frc.robot.subsystems;
import edu.wpi.first.wpilibj.xrp.XRPReflectanceSensor;



public class SensorTwo {
    XRPReflectanceSensor m_sensor = new XRPReflectanceSensor();

    public boolean colorBlack(){
        if (m_sensor.getRightReflectanceValue() > 0.7){
            return true;
        }
        return false;
    }
 
    
}
