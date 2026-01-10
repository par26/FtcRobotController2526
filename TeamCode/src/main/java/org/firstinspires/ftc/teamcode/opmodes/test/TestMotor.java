package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(group="test")
public class TestMotor extends CommandOpMode {

    private IntakeSubsystem intake;

    @Override
    public void initialize() {
        intake = new IntakeSubsystem(hardwareMap);
        register(intake);
    }
}
