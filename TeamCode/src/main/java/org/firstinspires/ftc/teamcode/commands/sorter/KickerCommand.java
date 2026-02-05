package org.firstinspires.ftc.teamcode.commands.sorter;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.sorter.ManualSorter;

public class KickerCommand extends SequentialCommandGroup {

    private final ManualSorter m_sorter;

    public KickerCommand(ManualSorter sorter) {
        m_sorter = sorter;

        addCommands(
                new InstantCommand(m_sorter::kickerActivate, m_sorter),
                new WaitCommand(1150),
                new InstantCommand(m_sorter::kickerReset, m_sorter)
        );
    }
}
