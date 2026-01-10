package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

@Configurable
public class OuttakeV2Subsystem extends SubsystemBase{
    private Motor m_flywheelMotor;

    //tune values in prod
    public static double p = 0.7;
    public static double i = 0.2;
    public static double d = 0.5;

    private double power;

    public OuttakeV2Subsystem(HardwareMap hwMap) {
        m_flywheelMotor = new Motor(hwMap, "flyWheel",28, 6000);
        m_flywheelMotor.setRunMode(Motor.RunMode.VelocityControl);
        m_flywheelMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_flywheelMotor.setVeloCoefficients(p, i, d);

        m_flywheelMotor.resetEncoder();
    }

    public void spin(boolean isThree) {
        m_flywheelMotor.setRunMode(Motor.RunMode.VelocityControl);
    }

    @Override
    public void periodic() {
        m_flywheelMotor.set(power);
    }
}
