package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;

@TeleOp(group="test")
public class TestFlywheel extends CommandOpMode {

    private OuttakeSubsystem m_outtake;
    private GamepadEx m_driver;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_outtake = new OuttakeSubsystem(hardwareMap);

        register(m_outtake);

        m_driver.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new InstantCommand(() -> m_outtake.spin(true), m_outtake));

        m_driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> m_outtake.spin(false), m_outtake));
    }
}
