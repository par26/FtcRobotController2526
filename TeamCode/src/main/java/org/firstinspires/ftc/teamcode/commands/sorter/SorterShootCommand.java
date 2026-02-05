package org.firstinspires.ftc.teamcode.commands.sorter;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commands.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterConstants;
import org.firstinspires.ftc.teamcode.subsystems.sorter.Sorter;
import org.firstinspires.ftc.teamcode.util.SorterNode;


/** state handler for shooting */
public class SorterShootCommand extends CommandBase {

    private final Sorter m_sorter;

    private enum State {
        TRANSFER,
        SHOOT,
        HOLD1,
        HOLD2
    }

    public SorterShootCommand(Sorter sorter) {
        this.m_sorter = sorter;
    }

    private final ElapsedTime kickerTimer = new ElapsedTime();
    private State state;

    @Override
    public void initialize() {
        state = State.TRANSFER;
        addRequirements(m_sorter);
    }

    @Override
    public void execute() {

        //order recalced each loop, only use index 0
        SorterNode[] order = m_sorter.getNodeOrder();

        switch (state) {
            case TRANSFER:
                m_sorter.changeAngle(m_sorter.nodeToShoot(order[0]));
                state = State.SHOOT;
                break;
            case SHOOT:
                if (m_sorter.isAtTarget()) {
                    m_sorter.kickerActivate();
                    kickerTimer.reset();
                    state = State.HOLD1;
                }
                break;
            case HOLD1:
                if (kickerTimer.milliseconds() >= SorterConstants.Kicker.HOLD_TIME_MS) {
                    m_sorter.kickerReset();
                    kickerTimer.reset();
                    state = State.HOLD2;
                }

                break;
            case HOLD2:
                if (kickerTimer.milliseconds() >= SorterConstants.Kicker.HOLD_TIME_MS) {
                    state = State.TRANSFER;
                }
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_sorter.isEmpty();
    }
}
