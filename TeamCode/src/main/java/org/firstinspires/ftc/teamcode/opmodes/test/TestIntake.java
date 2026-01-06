package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;


@TeleOp
public class TestIntake extends CommandOpMode {

    private GamepadEx m_driver;
    private IntakeSubsystem m_intake;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_intake = new IntakeSubsystem(hardwareMap);

        register(m_intake);

        m_intake.setDefaultCommand(
                new RunCommand(() -> m_intake.intake(), m_intake)
        );

        m_driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileHeld(new InstantCommand(() -> m_intake.reverse(), m_intake));

    }
}
