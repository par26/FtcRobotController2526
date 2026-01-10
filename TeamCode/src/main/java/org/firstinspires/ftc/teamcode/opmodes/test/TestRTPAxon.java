package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.AxonSubsystem;
import org.firstinspires.ftc.teamcode.util.RTPAxon;

import kotlin.time.Instant;

@TeleOp(name = "Cont. Rotation Axon Test", group = "test")
public class TestRTPAxon extends CommandOpMode {

    private AxonSubsystem axon;
    private GamepadEx m_driver;

    @Override
    public void initialize() {

        m_driver = new GamepadEx(gamepad1);
        axon = new AxonSubsystem(hardwareMap, telemetry);

        register(axon);

        m_driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> axon.changeAngle(30)));

        m_driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> axon.changeAngle(-30)));

        m_driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> axon.setAngle(0)));
    }

}
