package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.seattlesolvers.solverslib.hardware.motors.Motor;


public class OuttakeSubsystem extends SubsystemBase{
    Motor flywheelMotorLeft;
    Motor flywheelMotorRight;

    boolean isSpinning;
    PIDCoefficients pid = new PIDCoefficients(0.5, 0, .2);


    double power;
    // set and get the coefficients

    public OuttakeSubsystem(HardwareMap hardwareMap) {
        flywheelMotorLeft = new Motor(hardwareMap, "leftFlyWheel",28, 6000);
        flywheelMotorRight = new Motor(hardwareMap, "rightFlyWheel", 28, 6000);
        flywheelMotorRight.setRunMode(Motor.RunMode.VelocityControl);
        flywheelMotorLeft.setRunMode(Motor.RunMode.VelocityControl);
        flywheelMotorLeft.setInverted(true);

        flywheelMotorLeft.setVeloCoefficients(0.05, 0, 0.01);
        flywheelMotorRight.setVeloCoefficients(0.05, 0, 0.01);

        flywheelMotorRight.resetEncoder();
        flywheelMotorLeft.resetEncoder();

        power = 0;
        isSpinning = false;
    }

    public void spin() {
        flywheelMotorRight.setRunMode(Motor.RunMode.VelocityControl);
        flywheelMotorLeft.setRunMode(Motor.RunMode.VelocityControl);
        power = 0.5;
    }

    public void stop() {
        flywheelMotorRight.setRunMode(Motor.RunMode.RawPower);
        flywheelMotorLeft.setRunMode(Motor.RunMode.RawPower);
        power = 0;
    }


    public void toggle() {
        isSpinning = !isSpinning;

        if (isSpinning) {
            stop();
        } else {
            spin();
        }
    }

    @Override
    public void periodic() {
        flywheelMotorRight.set(power);
        flywheelMotorLeft.set(power);

    }
}
