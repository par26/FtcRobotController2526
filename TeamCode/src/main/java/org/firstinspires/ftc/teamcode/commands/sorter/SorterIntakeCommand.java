package org.firstinspires.ftc.teamcode.commands.sorter;


import org.firstinspires.ftc.teamcode.commands.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterSubsystem;

public class SorterIntakeCommand extends CommandBase {

    private final SorterSubsystem m_sorter;

    private enum State {
        INTAKING,
        ALTERNATING
    }

    private State state;

    public SorterIntakeCommand(SorterSubsystem sorter) {
        this.m_sorter = sorter;
        addRequirements(sorter);
    }

    @Override
    public void initialize() {
        state = State.INTAKING;
    }

    @Override
    public void execute() {
        switch (state) {
            case INTAKING:
                if (m_sorter.isOccupied(m_sorter.sorterNode1)) {
                    m_sorter.rotateCC();
                    state = State.ALTERNATING;
                }
                break;
            case ALTERNATING:
                if (m_sorter.isAtTarget()) {
                    state = State.INTAKING;
                }
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_sorter.isOccupied(m_sorter.sorterNode1) && m_sorter.isOccupied(m_sorter.sorterNode3);
    }
}