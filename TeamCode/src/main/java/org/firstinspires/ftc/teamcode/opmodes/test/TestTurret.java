package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LocalizationSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.turret.TurretSubsystem;
import org.firstinspires.ftc.teamcode.util.FieldConstants;
import org.firstinspires.ftc.teamcode.util.MatchConstants;

@TeleOp(group="test")
public class TestTurret extends CommandOpMode {

    private TurretSubsystem m_turret;
    private GamepadEx m_driver1;
    private DriveSubsystem m_drive;
    private Follower m_follower;
    private LocalizationSubsystem m_local;

    @Override
    public void initialize() {

        MatchConstants.goalPose = FieldConstants.redGoalPose;
        MatchConstants.startPose = FieldConstants.redSpawnTest;
        MatchConstants.isBlueAlliance = false;
        m_follower = Constants.createFollower(hardwareMap);
        m_follower.setStartingPose(FieldConstants.redSpawnTest);

        m_driver1 = new GamepadEx(gamepad1);
        m_turret = new TurretSubsystem(hardwareMap, telemetry, m_follower);
        m_drive = new DriveSubsystem(hardwareMap, m_follower);
        m_local = new LocalizationSubsystem(telemetry, m_follower);

        register(m_local, m_turret, m_drive);

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

    }
}
