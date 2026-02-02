package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

public class MatchConstants {
    public enum RobotStart {
        FAR,
        CLOSE,
        TEST
    }

    public static MatchConstants.RobotStart startLocation;
    public static boolean isBlueAlliance;
    public static Follower matchFollower;
    public static Pose goalPose;
    public static Pose startPose;

    public static SorterNode.NodeOption[] matchMotif;
}
