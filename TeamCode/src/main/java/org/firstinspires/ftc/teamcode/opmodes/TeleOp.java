package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.lynx.LynxModule;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.sorter.SorterIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.sorter.SorterShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.sorter.RTP;
import org.firstinspires.ftc.teamcode.subsystems.sorter.Sorter;

public class TeleOp extends CommandOpMode {

    //Subsystems
    private GamepadEx gp1;

    private Sorter m_sorter;
    private RTP m_rtp;


    @Override
    public void initialize() {

        for (LynxModule hub : hardwareMap.getAll(LynxModule.class)) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        gp1 = new GamepadEx(gamepad1);

        m_rtp = new RTP(hardwareMap);
        m_sorter = new Sorter(hardwareMap, m_rtp);

        //Sorter
        gp1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> m_sorter.switchState(), m_sorter));

        new Trigger(m_sorter::isIntakeState)
                .whenActive(new SorterIntakeCommand(m_sorter));
        new Trigger(m_sorter::isShootState)
                .whenActive(new SorterShootCommand(m_sorter));
    }
}
