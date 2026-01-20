package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.turret.ManualTurretSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.turret.TurretSubsystem;

@TeleOp(group="test")
public class TestTurret extends CommandOpMode {

    private TurretSubsystem m_turret;
    private GamepadEx m_driver;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_turret = new TurretSubsystem(hardwareMap, true, telemetry);

        register(m_turret);

    }
}
