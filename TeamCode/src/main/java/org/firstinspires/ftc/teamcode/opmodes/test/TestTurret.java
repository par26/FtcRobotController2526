package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.ManualTurretSubsystem;

@TeleOp(group="test")
public class TestTurret extends CommandOpMode {

    private ManualTurretSubsystem m_turret;
    private GamepadEx m_driver;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_turret = new ManualTurretSubsystem(hardwareMap);

        register(m_turret);

        m_turret.setDefaultCommand(
                new RunCommand(() -> m_turret.setPower(
                        () -> m_driver.getRightX()), m_turret));

    }
}
