package org.firstinspires.ftc.teamcode.opmodes;

import com.seattlesolvers.solverslib.command.Robot;

public class MyRobot extends Robot {

    public enum OpModes {
        TELEOP, AUTO;
    }
    public MyRobot(OpModes type) {

        if (type == OpModes.TELEOP) {
            initTele();
        } else {
            initAuto();
        }
    }

    void initTele() {

    }

    void initAuto() {

    }
}
