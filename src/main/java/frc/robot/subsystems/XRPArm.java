package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class XRPArm extends SubsystemBase{

    private final XRPServo m_armAServo;

    public XRPArm(){
        m_armAServo = new XRPServo(4);
    }

    public void setAngle(double angle){
        m_armAServo.setAngle(angle);
    }

}
