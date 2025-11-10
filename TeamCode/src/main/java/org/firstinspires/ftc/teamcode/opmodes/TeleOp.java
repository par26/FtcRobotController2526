package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Robot;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TrollSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends CommandOpMode {

    private GamepadEx m_driver;
    private DriveSubsystem m_drive;

    private IntakeSubsystem m_intake;
    private TrollSubsystem m_troll;

    private OutakeSubsystem m_outake;

    Telemetry telemetry;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_drive = new DriveSubsystem(hardwareMap);
        m_intake = new IntakeSubsystem(hardwareMap);
        m_troll = new TrollSubsystem(hardwareMap, "servo");
        m_outake = new OutakeSubsystem(hardwareMap);
        register(m_drive, m_troll, m_intake, m_outake);

        schedule(new InstantCommand(m_drive::startTeleOp, m_drive));

        m_drive.setDefaultCommand(
                new RunCommand(() ->
                        m_drive.drive(
                                m_driver.getLeftY(),
                                -m_driver.getLeftX(),
                                -m_driver.getRightX(),
                                true
                        ), m_drive));

        m_driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> m_troll.toggle(), m_troll));


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
                    m_outake.toggle(), m_outake
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
