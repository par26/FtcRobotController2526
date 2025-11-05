package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;

//Who's Rem?
@TeleOp
public class ReZero extends OpMode {
    private ServoImplEx m_servo;

    @Override
    public void init() {
        m_servo = hardwareMap.get(ServoImplEx.class, "s");

        telemetry.addData("Servo Current Position: ", m_servo.getPosition());
        telemetry.update();
    }

    @Override
    public void loop() {
        m_servo.setPosition(0);
        telemetry.update();
    }
}
