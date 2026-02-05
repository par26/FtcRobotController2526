package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.sorter.KickerCommand;
import org.firstinspires.ftc.teamcode.subsystems.sorter.ManualSorter;
import org.firstinspires.ftc.teamcode.subsystems.sorter.RTP;

@TeleOp(group="test")
public class TestSorter extends CommandOpMode {

    private GamepadEx m_driver;
    private ManualSorter m_sorter;
    private RTP m_rtp;

    @Override
    public void initialize() {
        m_driver = new GamepadEx(gamepad1);
        m_rtp = new RTP(hardwareMap);
        m_sorter = new ManualSorter(hardwareMap, m_rtp);

        register(m_sorter, m_rtp);

        //heres the kicker
        m_driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new KickerCommand(m_sorter));
    }
}
