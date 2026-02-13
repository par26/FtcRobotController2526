package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterSensor;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterServo;
import org.firstinspires.ftc.teamcode.subsystems.turret.Turret;

import java.util.List;

//TODO: Restructure code
public class Robot {

    public final Intake intake;
    public final Turret turret;
    public final Outtake outtake;
    public final SorterSensor sensor;
    public final SorterServo servo;
    public final Follower follower;
    public Alliance a;

    private final List<LynxModule> hubs;
    public static Pose defaultPose = new Pose(8 + 24, 6.25 + 24, 0);
    public static Pose shootTarget = new Pose(6, 144 - 6, 0);

    public Robot(HardwareMap hwMap, Alliance a, Telemetry telemetry) {
        this.a = a;
        follower = Constants.createFollower(hwMap);
        intake = new Intake(hwMap);
        turret = new Turret(hwMap, telemetry, follower);
        outtake = new Outtake(hwMap);
        servo = new SorterServo(hwMap);
        sensor = new SorterSensor(hwMap);

        hubs = hwMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    public void register(CommandOpMode opmode) {
        opmode.register(intake, turret, outtake, sensor, servo);
    }

}
