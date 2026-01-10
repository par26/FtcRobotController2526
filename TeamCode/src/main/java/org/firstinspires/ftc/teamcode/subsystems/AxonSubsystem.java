package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.hardware.AbsoluteAnalogEncoder;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.RTPAxon;

@Configurable
public class AxonSubsystem extends SubsystemBase {

    private CRServoEx crServo;
    private AnalogInput encoder;
    private RTPAxon servo;

    private double error;

    private double targetPos;

    private Telemetry telemetry;
    public static double kP = 0.2;
    public static double kI = 0.0;
    public static double kD = 0.0015;

    public AxonSubsystem(HardwareMap hwMap, Telemetry telemetry) {
        //crServo = hwMap.get(CRServoEx.class, "servo");
        AbsoluteAnalogEncoder encoder = new AbsoluteAnalogEncoder(hwMap, "encoder");

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

    public double getCurrentAngle() {
        return (encoder.getVoltage() / 3.3) *  360;
    }
    public double getError() {
        return targetPos - getCurrentAngle();
    }
    @SuppressLint("DefaultLocale")
    public void log() {
        telemetry.addLine(
                String.format(
                "%s: %.1f° → %s (err: f) %s",
                crServo.getDeviceType(),
                getCurrentAngle(),
                targetPos,
                getError(),
                crServo.atTargetPosition() ? "✓" : "..."
        ));
    }

    @Override
    public void periodic() {
        log();

        telemetry.update();
        crServo.set(targetPos);
    }
}
