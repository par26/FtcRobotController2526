package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class IntakeSubsystem  extends SubsystemBase{


    private final DcMotorEx motor1;
    private final DcMotorEx motor2;
    public static double speed1, speed2 = 0.35;

    public boolean spinning1, spinning2, spinning3;

    public IntakeSubsystem(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotorEx.class, "motor");
        motor2 = hwMap.get(DcMotorEx.class, "motor2");


        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);


        spinning1 = false;
        spinning2 = false;
        spinning3 = false;
    }

    public void toggle1() {

        spinning1 = !spinning1;

        speed1 = spinning1 ? 0 : 0.45;
    }

    public void toggle2() {

        spinning2 = !spinning2;

        speed2 = spinning2 ? 0 : 0.35;
    }


    @Override
    public void periodic() {
        motor1.setPower(speed1);
        motor2.setPower(speed2);
    }
}
