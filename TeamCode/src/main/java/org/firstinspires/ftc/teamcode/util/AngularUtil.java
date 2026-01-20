package org.firstinspires.ftc.teamcode.util;

public final class AngularUtil {
    private AngularUtil() {}

    public static double wrap360(double angleDeg) {
        double a = angleDeg % 360.0;
        if (a < 0) a += 360.0;
        return a;
    }

    public static double limitedTurretDelta(double current, double offset, double maxAngle) {
        double rawTarget = current + offset;

        double wrapped = wrap360(rawTarget);

        if (wrapped > maxAngle) {
            double deadzoneMid = (360.0 + maxAngle) / 2.0;

            if (wrapped < deadzoneMid) {
                wrapped = maxAngle;
            } else {
                wrapped = 0.0;
            }
        }

        return wrapped - current;
    }

    public static double turretDelta(double current, double offset) {
        return limitedTurretDelta(current, offset, 330);
    }
}

