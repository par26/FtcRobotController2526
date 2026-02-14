package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.lynx.LynxModule;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.IntakeStateCommand;
import org.firstinspires.ftc.teamcode.commands.ShootStateCommand;
import org.firstinspires.ftc.teamcode.commands.sorter.SorterIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.sorter.SorterShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterServo;
//import org.firstinspires.ftc.teamcode.subsystems.sorter.Sorter;
import org.firstinspires.ftc.teamcode.subsystems.sorter.SorterSensor;
import org.firstinspires.ftc.teamcode.util.MatchValues;

public class TeleOp extends CommandOpMode {

    //Subsystems
    private GamepadEx gp1;

    private SorterSensor m_sorter;
    private SorterServo m_servo;





    @Override
    public void initialize() {

        for (LynxModule hub : hardwareMap.getAll(LynxModule.class)) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        gp1 = new GamepadEx(gamepad1);
        m_servo = new SorterServo(hardwareMap);
    }
}
