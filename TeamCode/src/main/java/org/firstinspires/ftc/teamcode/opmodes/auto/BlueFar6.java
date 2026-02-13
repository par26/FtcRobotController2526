package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.util.MatchValues;

@Autonomous(name = "BlueFar6", preselectTeleOp = "TeleOp")
public class BlueFar6 extends CommandOpMode {

    @Override
    public void initialize() {
        MatchValues.isBlueAlliance = true;
        MatchValues.startLocation = MatchValues.RobotStart.FAR;


    }
}
