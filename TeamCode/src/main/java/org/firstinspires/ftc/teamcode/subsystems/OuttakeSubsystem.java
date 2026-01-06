package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

@Configurable
public class OuttakeSubsystem extends SubsystemBase{
    private Motor m_flywheelMotor;
    public static double LAUNCH_POWER_CLOSE = 0.41;
    public static double LAUNCH_POWER_FAR = 0.50;

    //tune values in prod
    public static double p = 0.7;
    public static double i = 0.2;
    public static double d = 0.5;

    private double power;

    public OuttakeSubsystem(HardwareMap hwMap) {
        m_flywheelMotor = new Motor(hwMap, "flyWheel",28, 6000);
        m_flywheelMotor.setRunMode(Motor.RunMode.VelocityControl);
        m_flywheelMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_flywheelMotor.setVeloCoefficients(p, i, d);

        power = LAUNCH_POWER_CLOSE;
        m_flywheelMotor.resetEncoder();
    }

    public void spin(boolean isThree) {
        m_flywheelMotor.setRunMode(Motor.RunMode.VelocityControl);
        power = isThree ? LAUNCH_POWER_FAR : LAUNCH_POWER_CLOSE;
    }

    @Override
    public void periodic() {
        m_flywheelMotor.set(power);
    }
}
