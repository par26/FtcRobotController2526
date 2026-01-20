package org.firstinspires.ftc.teamcode.subsystems.turret;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
import org.firstinspires.ftc.teamcode.util.AngularUtil;

public class ManualTurretSubsystem extends SubsystemBase {
    //manual
    private Motor m_motor;
    private Telemetry m_telemetry;

    private double targetAngle = 0;
    private int targetTicks = 0;

    public ManualTurretSubsystem(HardwareMap hwMap, Telemetry telemetry) {
        m_motor = new Motor(hwMap, TurretConstants.HW.MOTOR,28, 435);

        m_motor.setRunMode(Motor.RunMode.PositionControl);
        m_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_motor.setPositionCoefficient(TurretConstants.ROTATE.kP);
        m_motor.setPositionTolerance(TurretConstants.ROTATE.TOLERANCE);
        m_motor.resetEncoder();

        m_telemetry = telemetry;
    }

    public void rotateC() {
        targetAngle += 30;
    }

    public void rotateCC() {
        targetAngle -= 30;
    }

    public static double ticksToDegrees(double ticks) {
        return ticks * (360.0 / 384.5);
    }
    public static int degreesToTicks(double degrees) {
        return (int) Math.round(degrees * (384.5 / 360.0));
    }
    public double getAngle() {
        return ticksToDegrees(m_motor.getCurrentPosition());
    }
    public void updateTargetTicks() {
        double current = AngularUtil.wrap360(getAngle());
        double delta = AngularUtil.turretDelta(current, AngularUtil.wrap360(targetAngle) - current);

        targetTicks = degreesToTicks(current + delta);
    }

    @Override
    public void periodic() {
        updateTargetTicks();
        m_motor.setTargetPosition(targetTicks);
        m_motor.set(TurretConstants.ROTATE.HOLD_POWER);

        m_telemetry.addData("Target Angle:", targetAngle);
        m_telemetry.addData("Target Ticks:", targetTicks);
        m_telemetry.addData("Motor Angle:", ticksToDegrees(m_motor.getCurrentPosition()));
        m_telemetry.addData("Wrapped Target Angle", AngularUtil.wrap360(targetAngle));
        m_telemetry.addData("Wrapped Motor Angle", AngularUtil.wrap360(ticksToDegrees(m_motor.getCurrentPosition())));
        m_telemetry.addData("kP", m_motor.getPositionCoefficient());
        m_telemetry.update();
    }
}
