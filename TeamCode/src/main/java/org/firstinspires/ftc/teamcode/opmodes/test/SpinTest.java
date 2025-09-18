package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Configurable
public class SpinTest extends OpMode {

    DcMotorEx left;
    DcMotorEx right;

    @Override
    public void init() {
        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "right");

        left.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        right.setDirection(DcMotor.Direction.REVERSE);

    }

    public void loop() {

        left.setPower(1);
        right.setPower(1);
    }
}
