package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Localization;
import org.firstinspires.ftc.teamcode.subsystems.turret.Turret;
import org.firstinspires.ftc.teamcode.util.Alliance;
import org.firstinspires.ftc.teamcode.util.FieldConstants;
import org.firstinspires.ftc.teamcode.util.MatchValues;

@TeleOp(group="test")
public class TestTurret extends CommandOpMode {

    private Turret m_turret;
    private GamepadEx m_driver1;
    private Drive m_drive;
    private Follower m_follower;
    private Localization m_local;

    @Override
    public void initialize() {

        MatchValues.goalPose = FieldConstants.redGoalPose;
        MatchValues.startPose = FieldConstants.redSpawnTest;
        MatchValues.alliance = Alliance.RED;
        m_follower = Constants.createFollower(hardwareMap);
        m_follower.setStartingPose(FieldConstants.redSpawnTest);

        m_driver1 = new GamepadEx(gamepad1);
        m_turret = new Turret(hardwareMap, telemetry, m_follower);
        m_drive = new Drive(hardwareMap, m_follower);
        m_local = new Localization(telemetry, m_follower);

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
