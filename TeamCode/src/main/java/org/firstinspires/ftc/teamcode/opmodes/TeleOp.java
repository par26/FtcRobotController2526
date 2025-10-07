package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

public class TeleOp extends CommandOpMode {

    private GamepadEx m_driverop;

    @Override
    public void initialize() {
        m_driverop = new GamepadEx(gamepad1);


    }
}
