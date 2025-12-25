package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class IntakeSubsystem  extends SubsystemBase{


    private final DcMotorEx motor1;
    private final DcMotorEx motor2;
    public static double SPINSPEED_1 = 0.53;
    public static double SPINSPEED_2 = 0.55;
    private double speed_1;
    private double speed_2;

    public boolean isSpinning1, isSpinning2;

    public IntakeSubsystem(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotorEx.class, "intake1");
        motor2 = hwMap.get(DcMotorEx.class, "intake2");

        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        isSpinning1 = true;
        isSpinning2 = false;
        speed_1 = SPINSPEED_1;
        speed_2 = 0;
    }

    public void toggle1() {
        isSpinning1 = !isSpinning1;

        speed_1 = isSpinning1 ? SPINSPEED_1 : 0;
    }

    public void toggle2() {
        isSpinning2 = !isSpinning2;

        speed_2 = isSpinning2 ? SPINSPEED_2 : 0;
    }

    public void set1(boolean startSpin) {
        speed_1 = startSpin ? SPINSPEED_1 : 0;
        isSpinning1 = startSpin;
    }

    public void set2(boolean startSpin) {
        speed_2 = startSpin ? SPINSPEED_2 : 0;
        isSpinning2 = startSpin;
    }

    public void reverse1() {
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void forward1() {
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    @Override
    public void periodic() {
        motor1.setPower(speed_1);
        motor2.setPower(speed_2);
    }
}
