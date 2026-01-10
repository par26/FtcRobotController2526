package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.hardware.AbsoluteAnalogEncoder;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
public class RTPSubsystem extends SubsystemBase {

    private CRServoEx crServo;
    private AbsoluteAnalogEncoder encoder;

    private double error;
    private double targetPos;

    private Telemetry telemetry;
    public static double kP = 0.2;
    public static double kI = 0.0;
    public static double kD = 0.0015;

    public RTPSubsystem(HardwareMap hwMap) {
        encoder = new AbsoluteAnalogEncoder(hwMap, "encoder");
        crServo = new CRServoEx(hwMap, "servo", encoder, CRServoEx.RunMode.OptimizedPositionalControl);

        crServo.setPIDF(new PIDFCoefficients(kP, kI, kD, 0));
        crServo.set(Math.toRadians(0));
    }

    public void changeAngle(double angle) {
        targetPos += angle;
    }

    public void setAngle(double angle) {
        targetPos = angle;

    }

    public boolean isAtTarget() {
        return crServo.atTargetPosition();
    }

    public double getCurrentAngle() {
        return (encoder.getVoltage() / 3.3) *  360;
    }

    public double getError() {
        return targetPos - getCurrentAngle();
    }

//    @SuppressLint("DefaultLocale")
//    public void log() {
//        telemetry.addLine(
//                String.format(
//                "%s: %.1f° → %s (err: f) %s",
//                crServo.getDeviceType(),
//                getCurrentAngle(),
//                targetPos,
//                getError(),
//                crServo.atTargetPosition() ? "✓" : "..."
//        ));
//    }

    @Override
    public void periodic() {
        crServo.set(targetPos);
    }
}
