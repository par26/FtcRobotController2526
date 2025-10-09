package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedropathing.Constants;

public class DriveSubsystem extends SubsystemBase {

    private final Follower follower;


    public DriveSubsystem(HardwareMap hwMap) {
        follower = Constants.createFollower(hwMap);
    }

    public void startTeleOp() {
        follower.startTeleopDrive();
    }

    public void drive(double forward, double strafe, double turn, boolean robotCentric) {
        follower.setTeleOpDrive(forward, strafe, turn, robotCentric);
    }

    public void stop() {
        follower.setTeleOpDrive(0, 0, 0, true);
    }

    @Override
    public void periodic() {
        follower.update();
    }

}
