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

    private final Trigger trg_finishFirstMovement = new Trigger(m_stateUpdateEventLoop, () -> Commands.waitSeconds(5).isFinished());

    private final Trigger trg_spin, trg_finishFinalMovement;

    public Superstructure(XRPDrivetrain xrpDrivetrain, Trigger noSpinButton){
        m_xrpDrivetrain = xrpDrivetrain;
        trg_spin = new Trigger(m_stateUpdateEventLoop, noSpinButton.and(stateTrig_spinning));
        trg_finishFinalMovement = new Trigger(m_stateUpdateEventLoop, noSpinButton.negate().and(stateTrig_spinning));

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
        trg_finishFirstMovement.onTrue(
            Commands.runOnce(() -> m_currentState = xrpState.SPINNING)
        );
        trg_spin.onTrue(
            Commands.runOnce(() -> m_currentState = xrpState.MOVE)
        );
        trg_finishFinalMovement.onTrue(
            Commands.runOnce(() -> m_currentState = xrpState.IDLE)
        );

        // below this point everything should actually get handled based on what
        // state the robot is in - everything about should just handle changing between states
        // TODO: CHECK IF THIS ACTUALLY CONSISTENTLY MOVES FOWARD
        stateTrig_idle.whileTrue(
            Commands.runOnce(() -> m_xrpDrivetrain.tankDrive(0, 0))
        );

        stateTrig_move.whileTrue(
            Commands.run(() -> m_xrpDrivetrain.tankDrive(1, 1))
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
    }

    // public Command printState() {
    //     return Commands.run(() -> {
    //         if (stateTrig_idle.getAsBoolean() && stateTrg_movingForwardAfterLine.getAsBoolean()) {
    //             System.out.println("we found the issue");
    //         }
    //         if (stateTrg_idle.getAsBoolean()) {
    //             System.out.println("idle");
    //         } else if (stateTrg_movingForwardBeforeLine.getAsBoolean()) {
    //             System.out.println("moving before line");
    //         } else if (stateTrg_spinning.getAsBoolean()) {
    //             System.out.println("spinning");
    //         } else if (stateTrg_movingForwardAfterLine.getAsBoolean()) {
    //             System.out.println("moving after line");
    //         } else {
    //             System.out.println("gang what the fuck");
    //         }
    //     });
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
