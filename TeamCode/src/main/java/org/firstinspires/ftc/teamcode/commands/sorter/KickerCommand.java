package org.firstinspires.ftc.teamcode.commands.sorter;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.sorter.ManualSorterSubsystem;

public class KickerCommand extends SequentialCommandGroup {

    private final ManualSorterSubsystem m_sorter;

    public KickerCommand(ManualSorterSubsystem sorter) {
        m_sorter = sorter;

        addCommands(
                new InstantCommand(m_sorter::kickerActivate, m_sorter),
                new WaitCommand(1150),
                new InstantCommand(m_sorter::kickerReset, m_sorter)
        );
    }
}
