package org.firstinspires.ftc.teamcode.opmodes;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Robot;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends CommandOpMode {

    private GamepadEx m_driver;
    private DriveSubsystem m_drive;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_drive = new DriveSubsystem(hardwareMap);
        register(m_drive);

        schedule(new InstantCommand(m_drive::startTeleOp, m_drive));

        m_drive.setDefaultCommand(
                new RunCommand(() ->
                        m_drive.drive(
                                m_driver.getLeftY(),
                                -m_driver.getLeftX(),
                                -m_driver.getRightX(),
                                true
                        ), m_drive));
//
//        m_driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whileHeld(new RunCommand(() ->
//                        m_drive.drive(
//                                0.5*m_driver.getLeftX(),
//                                0.5*m_driver.getLeftY(),
//                                0.5*m_driver.getRightX(),
//                            true
//                        ), m_drive))
//                .whenReleased(new InstantCommand(m_drive::stop, m_drive));

    }


}
