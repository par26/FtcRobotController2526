package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterConstants;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterServo;

import kotlin.time.Instant;

//lower intake & remove offset from shooting
public class IntakeStateCommand extends SequentialCommandGroup {

    private final Intake m_intake;
    private final SorterServo m_servo;

    public IntakeStateCommand(Intake intake, SorterServo servo) {
        this.m_intake = intake;
        this.m_servo = servo;

        addCommands(
                new InstantCommand(m_intake::lowerIntake, m_intake),
                new InstantCommand(m_servo::removeOffset, m_servo)
        );
    }

}
