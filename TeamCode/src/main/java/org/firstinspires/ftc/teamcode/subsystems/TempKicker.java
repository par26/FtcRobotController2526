package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterConstants;

 public class TempKicker extends SubsystemBase {
    private Servo m_kicker;

    double curKickerAngle;

    public TempKicker(HardwareMap hwMap) {
        m_kicker = hwMap.get(Servo.class, "kicker");
        curKickerAngle = SorterConstants.Kicker.RESET_POS;

    }

    public void kickerActivate() {
        curKickerAngle = SorterConstants.Kicker.ACTIVATE_POS;
    }
    public void kickerReset() {
        curKickerAngle = SorterConstants.Kicker.RESET_POS;
    }

    @Override
    public void periodic() {
        m_kicker.setPosition(curKickerAngle);
    }



}
