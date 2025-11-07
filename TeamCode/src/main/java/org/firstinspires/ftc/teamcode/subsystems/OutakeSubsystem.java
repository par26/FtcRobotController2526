package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.seattlesolvers.solverslib.hardware.motors.Motor;


public class OutakeSubsystem extends SubsystemBase{
    Motor flywheelMotorLeft;
    Motor flywheelMotorRight;
    PIDCoefficients pid = new PIDCoefficients(0.5, 0, .2);


    double power;
    // set and get the coefficients

    public OutakeSubsystem(HardwareMap hardwareMap) {
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
    }

    public void spin() {
        power = .75;
    }

    public void stop() {
       power = 0;
    }

    public double  speed() {
        return flywheelMotorRight.getCorrectedVelocity();
    }

    @Override
    public void periodic() {
        flywheelMotorRight.set(power);
        flywheelMotorLeft.set(power);

    }
}
