package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.sorter.KickerCommand;
import org.firstinspires.ftc.teamcode.subsystems.sorter.ManualSorterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.sorter.RTPSubsystem;

@TeleOp(group="test")
public class TestSorter extends CommandOpMode {

    private GamepadEx m_driver;
    private ManualSorterSubsystem m_sorter;
    private RTPSubsystem m_rtp;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_rtp = new RTPSubsystem(hardwareMap);
        m_sorter = new ManualSorterSubsystem(hardwareMap, m_rtp);

        register(m_sorter, m_rtp);

        //heres the kicker
        m_driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new KickerCommand(m_sorter));
    }
}
