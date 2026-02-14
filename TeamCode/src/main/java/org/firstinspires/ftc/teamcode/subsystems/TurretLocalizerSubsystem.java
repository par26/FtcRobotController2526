package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.PedroCoordinates;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import java.util.function.DoubleSupplier;

@Configurable
public class TurretLocalizerSubsystem extends SubsystemBase {

    PedroCoordinates robotPosition;

    PedroCoordinates goalPos;

    Follower follower;

    private Motor m_motor;

    //tune values in prod
    public static double p = 0.001;
    public static double i = 0.0;
    public static double d = 0.0001;

    private double power;

    public TurretLocalizerSubsystem(HardwareMap hwMap, Follower follower) {
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
