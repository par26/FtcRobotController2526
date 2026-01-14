package org.firstinspires.ftc.teamcode.subsystems.sorter;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.RTPSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;

@Configurable
public class ManualSorterSubsystem extends SubsystemBase {

    private RTPSubsystem m_rtp;
    private Servo m_servo;

    public static double KICKER_DOWN = 0.85;
    public static double KICKER_RESET = 0;

    public static int OFFSET_ANGLE = 15;
    public static int NODE_ANGLE = 120;

    private int deviation = 0;
    private double curKickerAngle;

    public ManualSorterSubsystem(HardwareMap hwMap, RTPSubsystem rtp) {
        m_rtp = rtp;
        m_servo = hwMap.get(Servo.class, "kicker");

        curKickerAngle = KICKER_RESET;
    }

    public void kickerDown() {
        curKickerAngle = KICKER_DOWN;
    }

    public void kickerReset() {
        curKickerAngle = KICKER_RESET;
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
        m_servo.setPosition(curKickerAngle);
    }
}
