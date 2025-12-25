package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

@Configurable
public class OuttakeSubsystem extends SubsystemBase{
    Motor flywheelMotorLeft;
    Motor flywheelMotorRight;
    public static double LAUNCH_POWER_CLOSE = 0.41;
    public static double LAUNCH_POWER_FAR = 0.50;
    public static double STOP_POWER = 0;

    boolean isSpinning;
    PIDCoefficients pid = new PIDCoefficients(0.7, 0, .2);


    double power;
    // set and get the coefficients

    public OuttakeSubsystem(HardwareMap hardwareMap) {
        flywheelMotorLeft = new Motor(hardwareMap, "leftFlyWheel",28, 6000);
        flywheelMotorRight = new Motor(hardwareMap, "rightFlyWheel", 28, 6000);
        flywheelMotorRight.setRunMode(Motor.RunMode.VelocityControl);
        flywheelMotorLeft.setRunMode(Motor.RunMode.VelocityControl);

        flywheelMotorLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        flywheelMotorRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        flywheelMotorLeft.setInverted(true);

        flywheelMotorLeft.setVeloCoefficients(0.05, 0, 0.01);
        flywheelMotorRight.setVeloCoefficients(0.05, 0, 0.01);

        flywheelMotorRight.resetEncoder();
        flywheelMotorLeft.resetEncoder();

        power = LAUNCH_POWER_CLOSE;
        isSpinning = true;
    }

    public void spin(boolean isThree) {
        flywheelMotorRight.setRunMode(Motor.RunMode.VelocityControl);
        flywheelMotorLeft.setRunMode(Motor.RunMode.VelocityControl);
        power = isThree ? LAUNCH_POWER_FAR : LAUNCH_POWER_CLOSE;
    }

    public void stop() {
        flywheelMotorRight.setRunMode(Motor.RunMode.RawPower);
        flywheelMotorLeft.setRunMode(Motor.RunMode.RawPower);
        power = STOP_POWER;
    }


//    public void toggle() {
//        isSpinning = !isSpinning;
//
//        if (isSpinning) {
//            stop();
//        } else {
//            spin();
//        }
//    }

    @Override
    public void periodic() {
        flywheelMotorRight.set(power);
        flywheelMotorLeft.set(power);

    }
}
