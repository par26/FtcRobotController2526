package org.firstinspires.ftc.teamcode.subsystems.turret;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class TurretConstants {

    private TurretConstants() {}

    public static Turret turret = new Turret();
    public static class Turret {
        public ROTATE rotate = new ROTATE();
        public SEARCH search = new SEARCH();
        public VISION vision = new VISION();
    }

    public static class HW {
        public static final String LIMELIGHT = "limelight";
        public static final String MOTOR = "turretMotor";

        private HW() {};
    }

    public static class VISION {
        public static int POLL_RATE = 250;
    }

    //tuned :checkmark:
    public static class ROTATE {
        public static double POWER = 0.60;
        public static double kP = 0.01;
        public static double TOLERANCE = 15;
    }

    public static class SEARCH {
        public static double SEARCH_SPEED = 0.3;
    }
}
