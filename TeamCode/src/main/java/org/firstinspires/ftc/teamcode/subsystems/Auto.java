package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import static org.firstinspires.ftc.teamcode.subsystems.FieldConstants.*;


public class Auto {

    private Follower m_follower;

    public enum RobotStart {
        FAR,
        CLOSE,
        TEST
    }
    private RobotStart startlocation;

    //Poses
    public Pose startPose, preloadPose;

    //Paths/Pathchains

    public Auto() {

    }

    private void createPoses() {
        switch (startlocation) {
            case FAR:
                startPose = farStartPose;
                preloadPose = closePreloadPose;

                break;
            case CLOSE:


                break;
            case TEST:


                break;
        }
    }
}
