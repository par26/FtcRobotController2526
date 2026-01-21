    package org.firstinspires.ftc.teamcode.subsystems.turret;

    import com.bylazar.configurables.annotations.Configurable;
    import com.bylazar.configurables.annotations.IgnoreConfigurable;
    import com.qualcomm.hardware.limelightvision.LLResult;
    import com.qualcomm.hardware.limelightvision.LLResultTypes;
    import com.qualcomm.hardware.limelightvision.Limelight3A;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    import com.seattlesolvers.solverslib.hardware.motors.Motor;

    import org.firstinspires.ftc.robotcore.external.Telemetry;
    import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
    import org.firstinspires.ftc.teamcode.util.AngularUtil;
    import org.firstinspires.ftc.teamcode.util.SorterNode;

    import java.util.List;
    import java.util.Map;

    @Configurable
    public class TurretSubsystem extends SubsystemBase {

        private final Limelight3A m_limelight;
        private final Motor m_motor;
        private final Telemetry m_telemetry;

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


        private LLResult results;
        private boolean foundMotifTag = false;
        private boolean foundGoalTag = false;

        private List<LLResultTypes.FiducialResult> foundTags;
        private LLResultTypes.FiducialResult goalTag;
        @IgnoreConfigurable
        public static SorterNode.NodeOption[] gameMotif;
        private boolean isBlueAlliance;

        private int goal_tx;

        private enum TurretVisionState {
            SEARCHING,
            LOCKED
        }
        private TurretVisionState state;

        private int targetAngle;
        private int targetTicks;

        //TODO: boolean value for alliance side

        public TurretSubsystem(HardwareMap hwMap, boolean isBlueAlliance, Telemetry telemetry) {
            m_limelight = hwMap.get(Limelight3A.class, TurretConstants.HW.LIMELIGHT);
            m_motor = new Motor(hwMap, TurretConstants.HW.MOTOR,28, 435);
            m_telemetry = telemetry;

            m_motor.setRunMode(Motor.RunMode.PositionControl);
            m_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            m_motor.setPositionCoefficient(TurretConstants.ROTATE.kP);
            m_motor.setPositionTolerance(TurretConstants.ROTATE.TOLERANCE);
            m_motor.resetEncoder();

            m_limelight.setPollRateHz(TurretConstants.VISION.POLL_RATE);
            m_limelight.pipelineSwitch(8);
            m_limelight.start();

            this.isBlueAlliance = isBlueAlliance;
            state = TurretVisionState.SEARCHING;
        }

        /*
         * Order of Operations
         * 1. Search for obelisk & goal
         * 2. Verify that motif is found
         * 3. Verify that goal tag is current alliance goal
         * 4. Lock onto goal tag
        */

        private void search() {
            if (m_motor.atTargetPosition()) {
                targetAngle += 15;
            }
        }
        private void lock() {
            goal_tx = (int) goalTag.getTargetXDegrees();
            m_telemetry.addData("TargetX Degrees", goal_tx);

            if (m_motor.atTargetPosition()) {
                targetAngle += goal_tx;
            }

        }

        //checking if found game motif & if goal wanted found
        private void scanTags() {
            foundGoalTag = false;
            for (LLResultTypes.FiducialResult curTag : foundTags) {
                int curID = curTag.getFiducialId();

                //motif mapping
                if (MOTIF_MAP.containsKey(curID) && !foundMotifTag) {
                    gameMotif = MOTIF_MAP.get(curID);
                    foundMotifTag = true;
                    continue;
                }

                if (curID == BLUE_TAG && isBlueAlliance) {
                    goalTag = curTag;
                    foundGoalTag = true;
                    break;
                }

                if (curID == RED_TAG && !isBlueAlliance) {
                    goalTag = curTag;
                    foundGoalTag = true;
                }

                if (foundGoalTag && foundMotifTag) break;
            }
        }

        /* angle updates */
        public static double ticksToDegrees(double ticks) {
            return ticks * (360.0 / 384.5);
        }
        public static int degreesToTicks(double degrees) {
            return (int) Math.round(degrees * (384.5 / 360.0));
        }
        public double getAngle() {
            return ticksToDegrees(m_motor.getCurrentPosition());
        }
        public void updateTargetTicks() {
            double current = AngularUtil.wrap360(getAngle());
            double delta = AngularUtil.turretDelta(current, AngularUtil.wrap360(targetAngle) - current);

            targetTicks = degreesToTicks(current + delta);
        }

        @Override
        public void periodic() {
            results = m_limelight.getLatestResult();
            if (results != null && results.isValid()) {
                foundTags = results.getFiducialResults();
                scanTags();
            }

            state = (foundGoalTag && foundMotifTag) ? TurretVisionState.LOCKED : TurretVisionState.SEARCHING;

            switch (state) {
                case SEARCHING:
                    search();
                    m_telemetry.addLine("Vision: Searching");

                    break;
                case LOCKED:
                    lock();
                    m_telemetry.addLine("Vision: Locked");
                    break;
            }

            updateTargetTicks();
            m_motor.setTargetPosition(targetTicks);
            m_motor.set(TurretConstants.ROTATE.HOLD_POWER);
        }

        private void log() {

            m_telemetry.addData("Target Angle:", targetAngle);
            m_telemetry.addData("Motor Angle:", ticksToDegrees(m_motor.getCurrentPosition()));
            m_telemetry.addData("Target Ticks:", targetTicks);
            m_telemetry.addData("Motor Ticks:", m_motor.getCurrentPosition());
            m_telemetry.addData("Wrapped Target Angle", AngularUtil.wrap360(targetAngle));
            m_telemetry.addData("Wrapped Motor Angle", AngularUtil.wrap360(ticksToDegrees(m_motor.getCurrentPosition())));
            m_telemetry.addData("kP", m_motor.getPositionCoefficient());

            m_telemetry.update();
        }
    }
