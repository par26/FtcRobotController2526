package org.firstinspires.ftc.teamcode.opmodes;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TrollSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends CommandOpMode {

    private GamepadEx m_driver;
    private DriveSubsystem m_drive;

    private IntakeSubsystem m_intake;
    private TrollSubsystem m_troll;

    private OuttakeSubsystem m_outtake;

    Telemetry telemetry;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_drive = new DriveSubsystem(hardwareMap);
        m_intake = new IntakeSubsystem(hardwareMap);
        m_outtake = new OuttakeSubsystem(hardwareMap);
        register(m_drive, m_troll, m_intake, m_outtake);

        schedule(new InstantCommand(m_drive::startTeleOp, m_drive));

        m_drive.setDefaultCommand(
                new RunCommand(() ->
                        m_drive.drive(
                                m_driver.getLeftY(),
                                -m_driver.getLeftX(),
                                -m_driver.getRightX(),
                                true
                        ), m_drive));

        m_driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(new RunCommand(() ->
                        m_drive.drive(
                                0.35*m_driver.getLeftY(),
                                -0.35*m_driver.getLeftX(),
                                -0.35*m_driver.getRightX(),
                            true
                        ), m_drive));


        m_driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() ->
                    m_outtake.toggle(), m_outtake
            ));

        m_driver.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(() ->
                        m_intake.toggle1(), m_intake
                ));
        m_driver.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new InstantCommand(() ->
                        m_intake.toggle2(), m_intake
                ));

    }


}
