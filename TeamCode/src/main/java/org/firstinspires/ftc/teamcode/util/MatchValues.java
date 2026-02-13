package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

public class MatchValues {
    public enum RobotStart {
        FAR,
        CLOSE,
        TEST
    }

    public enum RobotState {
        INTAKE,
        SHOOT
    }

    public static MatchValues.RobotStart startLocation;
    public static MatchValues.RobotState robotState;
    public static boolean isBlueAlliance;
    public static Follower matchFollower;
    public static Pose goalPose;
    public static Pose startPose;

    public static SorterNode.NodeOption[] matchMotif;
}
