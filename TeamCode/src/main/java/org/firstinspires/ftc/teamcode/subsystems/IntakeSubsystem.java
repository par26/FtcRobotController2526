package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class IntakeSubsystem extends SubsystemBase {

    private CRServo m_servo;
    private double spinSpeed;
    private final CRServo.Direction intakingDirection = CRServo.Direction.REVERSE;
    private final CRServo.Direction reversingDirection = CRServo.Direction.FORWARD;
    private CRServo.Direction curDirection;

    public static double INTAKE_POWER = 1;
    public static double REVERSE_POWER = 1;

    //remember to tune directions
    public IntakeSubsystem(HardwareMap hwMap) {
        m_servo = hwMap.get(CRServo.class, "intakeServo");
        m_servo.setDirection(intakingDirection);
        m_servo.setPower(INTAKE_POWER);

    }

    public void intake() {
        curDirection = intakingDirection;
        spinSpeed = INTAKE_POWER;
    }

    public void reverse() {
        curDirection = reversingDirection;
        spinSpeed = REVERSE_POWER;
    }

    @Override
    public void periodic() {
        m_servo.setPower(spinSpeed);
        m_servo.setDirection(curDirection);
    }
}
