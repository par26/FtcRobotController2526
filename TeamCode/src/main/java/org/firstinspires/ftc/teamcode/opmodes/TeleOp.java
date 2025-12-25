package org.firstinspires.ftc.teamcode.opmodes;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends CommandOpMode {

    private GamepadEx m_driver;
    private DriveSubsystem m_drive;

    private IntakeSubsystem m_intake;
    private OuttakeSubsystem m_outtake;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_drive = new DriveSubsystem(hardwareMap);
        m_intake = new IntakeSubsystem(hardwareMap);
        m_outtake = new OuttakeSubsystem(hardwareMap);

        register(m_drive, m_intake, m_outtake);

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

        m_driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new InstantCommand(() -> m_intake.reverse1(), m_intake))
                .whenReleased(new RunCommand(() -> m_intake.forward1(), m_intake));

        m_driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileHeld(new InstantCommand(() -> m_intake.set2(true), m_intake))
                .whenReleased(new RunCommand(() -> m_intake.set2(false), m_intake));

        m_driver.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(() -> m_outtake.spin(true), m_outtake));

        m_driver.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(() -> m_outtake.spin(false), m_outtake));

    }


}
