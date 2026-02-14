package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.follower.Follower;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.util.Alliance;
import org.firstinspires.ftc.teamcode.util.MatchValues;

public class TestShooter extends CommandOpMode {

    private Follower m_follower;
    private Shooter m_shooter;

    @Override
    public void initialize() {
        MatchValues.alliance = Alliance.RED;
        m_shooter = new Shooter(hardwareMap, m_follower);

        register(m_shooter);

    }
}
