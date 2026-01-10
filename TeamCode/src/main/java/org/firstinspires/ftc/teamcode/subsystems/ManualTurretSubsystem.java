package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import java.util.function.DoubleSupplier;

public class ManualTurretSubsystem extends SubsystemBase {
    //manual
    private Motor m_motor;

    //tune values in prod
    public static double p = 0.001;
    public static double i = 0.0;
    public static double d = 0.0001;

    private double power;

    public ManualTurretSubsystem(HardwareMap hwMap) {
        m_motor = new Motor(hwMap, "turretMotor",28, 6000);
        m_motor.setRunMode(Motor.RunMode.VelocityControl);
        m_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_motor.setVeloCoefficients(p, i, d);

        m_motor.resetEncoder();
        m_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(DoubleSupplier doubleSupplier) {
        power = doubleSupplier.getAsDouble();
    }

    @Override
    public void periodic() {
        m_motor.set(power);
    }
}
