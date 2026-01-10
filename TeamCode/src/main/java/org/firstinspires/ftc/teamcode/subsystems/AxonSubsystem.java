package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.RTPAxon;

@Configurable
public class AxonSubsystem extends SubsystemBase {

    private CRServo crServo;
    private AnalogInput encoder;
    private RTPAxon servo;

    private Telemetry telemetry;
    public static double kP = 0.02;
    public static double kI = 0.0;
    public static double kD = 0.0015;

    public AxonSubsystem(HardwareMap hwMap, Telemetry telemetry) {
        crServo = hwMap.get(CRServo.class, "servo");
        encoder = hwMap.get(AnalogInput.class, "encoder");

        servo = new RTPAxon(crServo, encoder);
        servo.setDirection(RTPAxon.Direction.FORWARD);
        servo.setMaxPower(1);

        servo.setPidCoeffs(kP,kI,kD);
        this.telemetry = telemetry;
        servo.resetPID();
    }

    public void changeAngle(int angle) {
        servo.changeTargetRotation(angle);
    }

    public void setAngle(int angle) {
        servo.setTargetRotation(angle);
    }

    public void log() {
        telemetry.addLine(servo.log());
    }

    @Override
    public void periodic() {
        servo.update();
        log();
        telemetry.update();
    }
}
