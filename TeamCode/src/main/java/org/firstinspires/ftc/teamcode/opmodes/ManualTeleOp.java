package org.firstinspires.ftc.teamcode.opmodes;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.KickerCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.sorter.ManualSorterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ManualTurretSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.RTPSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class ManualTeleOp extends CommandOpMode {

    private GamepadEx m_driver1;
    private GamepadEx m_driver2;
    private DriveSubsystem m_drive;

    private IntakeSubsystem m_intake;
    private OuttakeSubsystem m_outtake;

    private ManualTurretSubsystem m_turret;
    private ManualSorterSubsystem m_sorter;

    private RTPSubsystem m_rtp;

    @Override
    public void initialize() {
        m_driver1 = new GamepadEx(gamepad1);
        m_driver2 = new GamepadEx(gamepad2);

        m_drive = new DriveSubsystem(hardwareMap);
        m_intake = new IntakeSubsystem(hardwareMap);
        m_outtake = new OuttakeSubsystem(hardwareMap);
        m_rtp = new RTPSubsystem(hardwareMap);

        m_sorter = new ManualSorterSubsystem(hardwareMap, m_rtp);
        m_turret = new ManualTurretSubsystem(hardwareMap);

        register(m_drive, m_intake, m_outtake, m_turret, m_rtp, m_sorter);

        m_drive.setDefaultCommand(
                new DriveCommand(
                        m_drive,
                        () -> m_driver1.getLeftY(),
                        () -> -m_driver1.getLeftX(),
                        () -> -m_driver1.getRightX(),
                        () -> true,
                        () -> m_driver1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).get() ? 0.35 : 1.0
                )
        );

        m_turret.setDefaultCommand(
                new RunCommand(() -> m_turret.setPower(
                        () -> m_driver2.getRightX()), m_turret));

        m_driver1.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(() -> m_outtake.spin(true), m_outtake));

        m_driver1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(() -> m_outtake.spin(false), m_outtake));

        //Node Offset adjusting
        m_driver2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> m_sorter.leftOffset(), m_sorter));

        m_driver2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> m_sorter.rightOffset(), m_sorter));

        //Node adjust
        m_driver2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new InstantCommand(() -> m_sorter.rotateCC(), m_sorter));

        m_driver2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new InstantCommand(() -> m_sorter.rotateC(), m_sorter));

        //heh, but heres the kicker AHAHHAHAHAHAHAHAHAHAHAHAHA
        m_driver2.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new KickerCommand(m_sorter));
    }


}
