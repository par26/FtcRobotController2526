package org.firstinspires.ftc.teamcode.subsystems.sorter;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class SorterConstants {

    private SorterConstants() {};

    /** Hardware Names */
    public static final class HW {
        public static final String ENCODER = "sorterEncoder";
        public static final String KICKER  = "kicker";
        public static final String NODE1   = "node1";
        public static final String NODE2   = "node2";
        public static final String NODE3   = "node3";

        private HW() {}
    }

    /** Node detection thresholds / filtering */
    public static final class Detect {
        public static double GREEN_HUE_MIN  = 140;
        public static double GREEN_HUE_MAX  = 180;

        public static double PURPLE_HUE_MIN = 200;
        public static double PURPLE_HUE_MAX = 280;

        private Detect() {}
    }

    /** Sorter rotation / indexing behavior */
    public static final class Index {
        public static int OFFSET_ANGLE_DEG = 15;   // node1/node2 offset
        public static int NODE_ANGLE_DEG   = 120;  // spacing between nodes

        private Index() {}
    }

    /** Kicker servo positions + timing */
    public static final class Kicker {
        public static double ACTIVATE_POS = 0.85;
        public static double RESET_POS    = 0.00;

        // Use seconds if your code uses ElapsedTime.seconds()
        public static long HOLD_TIME_MS  = 2500;

        private Kicker() {}
    }
}
