    package org.firstinspires.ftc.teamcode.subsystems.turret;

    import com.bylazar.configurables.annotations.Configurable;
    import com.pedropathing.follower.Follower;
    import com.pedropathing.geometry.Pose;
    import com.qualcomm.hardware.limelightvision.LLResult;
    import com.qualcomm.hardware.limelightvision.LLResultTypes;
    import com.qualcomm.hardware.limelightvision.Limelight3A;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    import com.qualcomm.robotcore.util.ElapsedTime;
    import com.seattlesolvers.solverslib.hardware.motors.Motor;

    import org.firstinspires.ftc.robotcore.external.Telemetry;
    import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
    import org.firstinspires.ftc.teamcode.util.AngularUtil;
    import org.firstinspires.ftc.teamcode.util.FieldConstants;
    import org.firstinspires.ftc.teamcode.util.MatchValues;
    import org.firstinspires.ftc.teamcode.util.SorterNode;

    import java.util.List;
    import java.util.Map;

    @Configurable
    public class Turret extends SubsystemBase {

        private final Limelight3A m_limelight;
        private final Motor m_motor;
        private final Telemetry m_telemetry;
        private Follower m_follower;

        private final int BLUE_TAG = 20;
        private final int RED_TAG = 24;

        private static final Map<Integer, SorterNode.NodeOption[]> MOTIF_MAP =
                Map.of(
                        21, new SorterNode.NodeOption[]{
                                SorterNode.NodeOption.GREEN,
                                SorterNode.NodeOption.PURPLE,
                                SorterNode.NodeOption.PURPLE
                        },
                        22, new SorterNode.NodeOption[]{
                                SorterNode.NodeOption.PURPLE,
                                SorterNode.NodeOption.GREEN,
                                SorterNode.NodeOption.PURPLE
                        },
                        23, new SorterNode.NodeOption[]{
                                SorterNode.NodeOption.PURPLE,
                                SorterNode.NodeOption.PURPLE,
                                SorterNode.NodeOption.GREEN
                        }
                );

        private enum TurretState {
            SEARCHING,
            LOCKED
        }
        private TurretState state;
        private Pose targetPose;

        private LLResult results;
        private boolean foundMotifTag = false;
        private boolean foundGoalTag = false;

        private List<LLResultTypes.FiducialResult> foundTags;
        private LLResultTypes.FiducialResult targetTag;

        private double targetAngle;
        private int targetTicks;

        private ElapsedTime timer = new ElapsedTime();
        private double accumulatedOffsetAngle = 0;

        //TODO: boolean value for alliance side

        public Turret(HardwareMap hwMap, Telemetry telemetry, Follower follower) {
            m_limelight = hwMap.get(Limelight3A.class, TurretConstants.HW.LIMELIGHT);
            m_motor = new Motor(hwMap, TurretConstants.HW.MOTOR,28, 435);
            m_telemetry = telemetry;
            m_follower = follower;

            m_motor.setRunMode(Motor.RunMode.PositionControl);
            m_motor.setInverted(true);
            m_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            m_motor.setPositionCoefficient(TurretConstants.ROTATE.kP);
            m_motor.setPositionTolerance(TurretConstants.ROTATE.TOLERANCE);
            m_motor.resetEncoder();

            m_limelight.setPollRateHz(TurretConstants.VISION.POLL_RATE);
            m_limelight.pipelineSwitch(8);
            m_limelight.start();

            state = TurretState.SEARCHING;
            targetPose = FieldConstants.obeliskPose;
        }

        //checking if found game motif & if goal wanted found
        private void scanTags() {
            foundGoalTag = false;
            for (LLResultTypes.FiducialResult curTag : foundTags) {
                int curID = curTag.getFiducialId();

                //motif mapping
                if (MOTIF_MAP.containsKey(curID) && !foundMotifTag) {
                    MatchValues.matchMotif = MOTIF_MAP.get(curID);
                    foundMotifTag = true;
                    continue;
                }

                if (curID == BLUE_TAG && MatchValues.isBlueAlliance) {
                    targetTag = curTag;
                    foundGoalTag = true;
                    break;
                }

                if (curID == RED_TAG && !MatchValues.isBlueAlliance) {
                    targetTag = curTag;
                    foundGoalTag = true;
                }

                if (foundGoalTag && foundMotifTag) break;
            }
        }

        /* angle updates */
        static double MOTOR_TICKS_PER_REV = 384.5;
        static double TICKS_PER_TURRET_REV = MOTOR_TICKS_PER_REV * TurretConstants.ROTATE.EXTERNAL_GEAR_RATIO;

        public static double ticksToDegrees(double ticks) {
            return ticks * (360.0 / TICKS_PER_TURRET_REV);
        }

        public static int degreesToTicks(double degrees) {
            return (int) Math.round(degrees * (TICKS_PER_TURRET_REV / 360.0));
        }

        private double calculateTurretAngle(Pose currentPose, Pose goalPose) {
            Pose turretPoseRelativeToRobot = getAdjustedPose(currentPose, 12);

            double deltaX = goalPose.getX() - turretPoseRelativeToRobot.getX();
            double deltaY = goalPose.getY() - turretPoseRelativeToRobot.getY();

            //translational angle adjustment
            double absoluteAngleToTarget = Math.toDegrees(Math.atan2(deltaY, deltaX));
            double robotHeading = AngularUtil.wrap360(Math.toDegrees(currentPose.getHeading()));

            // turret angle = (target angle) - (robot heading - 90°)
            double turretAngleToTarget = absoluteAngleToTarget - (robotHeading - 90.0);
            turretAngleToTarget = AngularUtil.wrap360(turretAngleToTarget);

            return turretAngleToTarget;     
        }

        /**
         * Using limelight to offset turret angle (if motor drifts) and turret not currently rotate
         */
        private void calculateOffset() {
            double tx = targetTag.getTargetXDegrees();
            double curPos = m_motor.getCurrentPosition();
            double delta = Math.abs(targetAngle - curPos);
            m_telemetry.addData("tx", tx);
            if (timer.seconds() > TurretConstants.ROTATE.OFFSET_INTERVAL && delta < 5) {
                accumulatedOffsetAngle += TurretConstants.ROTATE.OFFSET_AMPLIFIER * -tx;
                timer.reset();
            }
        }

        /**
         * accounting for the turret being offset from center
         * @param currentPose
         * @param offsetDistanceCm
         * @return
         */
        public static Pose getAdjustedPose(Pose currentPose, double offsetDistanceCm) {
            double headingRad = currentPose.getHeading();
            offsetDistanceCm /= 2.54;

            double offsetX = -offsetDistanceCm * Math.cos(headingRad);
            double offsetY = -offsetDistanceCm * Math.sin(headingRad);

            return new Pose(currentPose.getX() + offsetX, currentPose.getY() + offsetY, currentPose.getHeading()
            );
        }

        public void updateTargetTicks() {
            if (foundGoalTag) {calculateOffset();}
            targetAngle = calculateTurretAngle(m_follower.getPose(), targetPose);
            targetTicks = degreesToTicks(AngularUtil.turretDelta(targetAngle + accumulatedOffsetAngle));
        }

        @Override
        public void periodic() {
            m_follower.update();
            results = m_limelight.getLatestResult();
            foundTags = results.getFiducialResults();
            scanTags();

            state = (foundMotifTag) ? TurretState.LOCKED : TurretState.SEARCHING;

            switch (state) {
                case SEARCHING:
                    targetPose = FieldConstants.obeliskPose;
                    m_telemetry.addLine("Turret: Searching");

                    break;
                case LOCKED:
                    targetPose = MatchValues.isBlueAlliance ? FieldConstants.blueGoalPose : FieldConstants.redGoalPose;
                    m_telemetry.addLine("Turret: Locked");
                    break;
            }

            updateTargetTicks();
            m_motor.setTargetPosition(targetTicks);
            m_motor.set(TurretConstants.ROTATE.POWER);
            log();
        }

        private void log() {

            m_telemetry.addData("Done Searching", foundMotifTag ? "✔️" : "✖️");
            m_telemetry.addData("Motor Angle:", ticksToDegrees(m_motor.getCurrentPosition()));
            m_telemetry.addData("Motor Ticks:", m_motor.getCurrentPosition());
            m_telemetry.addData("Accumulated:", accumulatedOffsetAngle);
            m_telemetry.addData("X:", m_follower.getPose().getX());
            m_telemetry.addData("Y:", m_follower.getPose().getY());
            m_telemetry.addData("H:", AngularUtil.wrap360(Math.toDegrees(m_follower.getPose().getHeading())));

            m_telemetry.update();
        }
    }
