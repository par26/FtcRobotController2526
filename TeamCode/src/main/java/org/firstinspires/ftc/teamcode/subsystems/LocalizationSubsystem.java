package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.util.FieldConstants;

public class LocalizationSubsystem extends SubsystemBase{

    private Follower m_follower;
    private Telemetry telemetry;

    public LocalizationSubsystem(Telemetry telemetry, Follower follower) {
        this.m_follower = follower;
        this.telemetry = telemetry;
    }

    public Pose getPose() {
        return m_follower.getPose();
    }

    private void log() {
        Pose pose = m_follower.getPose();
        telemetry.addData("Heading", Math.toDegrees(pose.getHeading()));
        telemetry.addData("X", pose.getX());
        telemetry.addData("Y", pose.getY());

        telemetry.update();
    }

    @Override
    public void periodic() {
        m_follower.update();

    }
}
