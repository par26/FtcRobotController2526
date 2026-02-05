package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.util.MatchConstants;

@Autonomous(name = "BlueFar6", preselectTeleOp = "TeleOp")
public class BlueFar6 extends CommandOpMode {

    @Override
    public void initialize() {
        MatchConstants.isBlueAlliance = true;
        MatchConstants.startLocation = MatchConstants.RobotStart.FAR;


    }
}
