package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@TeleOp(group="test")
public class TestDriveTrain extends CommandOpMode {

    private GamepadEx m_driver;
    private DriveSubsystem m_drive;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_drive = new DriveSubsystem(hardwareMap);

        register(m_drive);

        //goofy ahh supplier system
        m_drive.setDefaultCommand(
                new DriveCommand(
                        m_drive,
                        () -> m_driver.getLeftY(),
                        () -> -m_driver.getLeftX(),
                        () -> -m_driver.getRightX(),
                        () -> true,
                        () -> m_driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).get() ? 0.35 : 1.0
                )
        );

    }
}
