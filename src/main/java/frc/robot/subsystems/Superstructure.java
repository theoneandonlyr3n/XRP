package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.event.EventLoop;



public class Superstructure {

    private final XRPDrivetrain m_xrpDrivetrain;

    public xrpState m_currentState = xrpState.IDLE;

    private final EventLoop m_stateTrigEventLoop = new EventLoop();
    private final EventLoop m_stateUpdateEventLoop = new EventLoop();

    private final Trigger stateTrig_idle = new Trigger(m_stateTrigEventLoop, () -> m_currentState == xrpState.IDLE);
    private final Trigger stateTrig_move = new Trigger(m_stateTrigEventLoop, () -> m_currentState == xrpState.MOVE);
    private final Trigger stateTrig_spinning = new Trigger(m_stateTrigEventLoop, () -> m_currentState == xrpState.SPINNING);

    private final Trigger trg_noSpin;

    public Superstructure(XRPDrivetrain xrpDrivetrain, Trigger noSpinButton){
        m_xrpDrivetrain = xrpDrivetrain;
        trg_noSpin = new Trigger(m_stateUpdateEventLoop, noSpinButton.and(stateTrig_spinning));
        

        configureTriggerBindings();
        
    }

    public void start() {
        if (stateTrig_idle.getAsBoolean()) {
            m_currentState = xrpState.MOVE;
        }
    }

    private void configureTriggerBindings() {
        // these ones should always check that they are in the correct state
        // before activating as they will not check here - it will just
        // set state to their following state

        trg_noSpin.onTrue(
            Commands.runOnce(() -> m_currentState = xrpState.IDLE)
        );

        // below this point everything should actually get handled based on what
        // state the robot is in - everything about should just handle changing between states
        // TODO: CHECK IF THIS ACTUALLY CONSISTENTLY MOVES FOWARD
        stateTrig_idle.whileTrue(
            Commands.runOnce(() -> m_xrpDrivetrain.tankDrive(0, 0))
        );

        stateTrig_move.whileTrue(
            Commands.sequence(
                Commands.race(
                    Commands.run(() -> m_xrpDrivetrain.tankDrive(1, 1)),
                    Commands.waitSeconds(1)           
                ),
                Commands.runOnce(() -> m_currentState = xrpState.SPINNING)  
            )
            
        ).onFalse(
            Commands.runOnce(() -> m_xrpDrivetrain.tankDrive(0, 0))
        );

        // TODO: CHECK IF THIS ACTUALLY SPINS PROPERLY
        stateTrig_spinning.whileTrue(
            Commands.run(() -> m_xrpDrivetrain.tankDrive(1, -1))
        ).onFalse(
            Commands.runOnce(() -> m_xrpDrivetrain.tankDrive(0, 0))
        );
    }

    public void fastPeriodic() {
        // remember that this update order could cause issues!
        // if you see anything really weird it could be that
        // this isn't updated in an order that works
        m_stateUpdateEventLoop.poll();
        m_stateTrigEventLoop.poll();
    }


}



    enum xrpState {
        IDLE(0),
        MOVE(1),
        SPINNING(2);

        private final int m_idx;

        private xrpState(int idx){
            m_idx = idx;
        }

        public int getID() {
            return m_idx;
        }
}
