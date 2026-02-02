package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import static org.firstinspires.ftc.teamcode.util.FieldConstants.*;


public class Auto {

    private Follower m_follower;

    //Poses
    public Pose startPose, preloadPose;

    //Paths/Pathchains

    public Auto() {

    }

    private void createPoses() {
        switch (MatchConstants.startLocation) {
            case FAR:

                break;
            case CLOSE:


                break;
            case TEST:


                break;
        }
    }
}
