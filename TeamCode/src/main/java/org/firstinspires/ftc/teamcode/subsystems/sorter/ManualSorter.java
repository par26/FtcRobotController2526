package org.firstinspires.ftc.teamcode.subsystems.sorter;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;

@Configurable
public class ManualSorter extends SubsystemBase {

    private RTP m_rtp;
    private Servo m_servo;

    private int deviation = 0;
    private double curKickerAngle;

    public ManualSorter(HardwareMap hwMap, RTP rtp) {
        m_rtp = rtp;
        m_servo = hwMap.get(Servo.class, "kicker");

        curKickerAngle = SorterConstants.Kicker.RESET_POS;
    }

    public void kickerActivate() {
        curKickerAngle = SorterConstants.Kicker.ACTIVATE_POS;
    }

    public void kickerReset() {
        curKickerAngle = SorterConstants.Kicker.RESET_POS;
        m_rtp.changeAngle(-deviation);
    }

    public void rotateCC() {
        m_rtp.changeAngle(-SorterConstants.Index.NODE_ANGLE_DEG);
    }

    public void rotateC() {
        m_rtp.changeAngle(SorterConstants.Index.NODE_ANGLE_DEG);
    }

    public void leftOffset() {
        if (deviation < 0) return;

        m_rtp.changeAngle(-SorterConstants.Index.OFFSET_ANGLE_DEG);
        deviation -= SorterConstants.Index.OFFSET_ANGLE_DEG;

    }

    public void rightOffset() {
        if (deviation > 0) return;

        m_rtp.changeAngle(SorterConstants.Index.OFFSET_ANGLE_DEG);
        deviation += SorterConstants.Index.OFFSET_ANGLE_DEG;

    }

    @Override
    public void periodic() {
        m_servo.setPosition(curKickerAngle);
    }
}
