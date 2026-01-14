package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class IntakeSubsystem extends SubsystemBase {

    private CRServo m_servo;
    private boolean isIntaking = true;
    private double spinSpeed;

    public static double INTAKE_POWER = 0.75;
    public static double REVERSE_POWER = 0.65;

    private final CRServo.Direction intakeDirection = CRServo.Direction.FORWARD;
    private final CRServo.Direction reverseDirection = CRServo.Direction.REVERSE;

    public IntakeSubsystem(HardwareMap hwMap) {
        m_servo = hwMap.get(CRServo.class, "intakeServo");
        m_servo.setDirection(intakeDirection);

    }

    public void intake() {
        m_servo.setDirection(intakeDirection);
        spinSpeed = INTAKE_POWER;
    }

    public void reverse() {
        m_servo.setDirection(reverseDirection);
        spinSpeed = REVERSE_POWER;
    }

    public void IntakeToggle() {
        isIntaking = !isIntaking;
        if (isIntaking) {
            m_servo.setDirection(CRServo.Direction.FORWARD);
        } else {
            m_servo.setDirection(CRServo.Direction.REVERSE);
        }

        spinSpeed = isIntaking ? INTAKE_POWER : REVERSE_POWER;
    }


    @Override
    public void periodic() {
        m_servo.setPower(spinSpeed);
    }
}
