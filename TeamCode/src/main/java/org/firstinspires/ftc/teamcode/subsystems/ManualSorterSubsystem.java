package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class ManualSorterSubsystem extends SubsystemBase{

    private RTPSubsystem m_rtp;
    private Servo m_servo;

    public static double KICKER_UP;
    public static double KICKER_RESET;

    public static int OFFSET_ANGLE = 15;
    public static int NODE_ANGLE = 120;

    private double deviation = 0;

    public ManualSorterSubsystem(HardwareMap hwMap, RTPSubsystem rtp) {
        m_rtp = rtp;
        m_servo = hwMap.get(Servo.class, "kicker");

    }

    public void kickerUp() {
        m_servo.setPosition(KICKER_UP);
    }

    public void kickerReset() {
        m_servo.setPosition(KICKER_RESET);
        m_rtp.changeAngle(-deviation);
    }

    public void rotateCC() {
        m_rtp.changeAngle(-NODE_ANGLE);
    }

    public void rotateC() {
        m_rtp.changeAngle(NODE_ANGLE);
    }

    public void leftOffset() {
        if (deviation < 0) return;

        m_rtp.changeAngle(-OFFSET_ANGLE);
        deviation -= OFFSET_ANGLE;

    }

    public void rightOffset() {
        if (deviation > 0) return;

        m_rtp.changeAngle(OFFSET_ANGLE);
        deviation += OFFSET_ANGLE;

    }

    @Override
    public void periodic() {

    }
}
