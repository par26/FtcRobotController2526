package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class TrollSubsystem extends SubsystemBase {

    private final Servo troll;
    private boolean launched = false;

    public static double REST = 0;
    public static double LAUNCH = 0.35;

    public TrollSubsystem(HardwareMap hwMap, String target) {
        troll = hwMap.get(Servo.class, target);
        troll.setPosition(REST);
    }

    public void toggle() {

        launched = !launched;

        troll.setPosition(launched ? REST : LAUNCH);
    }

    @Override
    public void periodic() {

    }
}
