package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.util.SorterNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configurable
public class TurretSubsystem extends SubsystemBase{

    public static int POLL_RATE = 100;

    private Limelight3A m_limelight;
    private Motor m_motor;

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
    private boolean hasFoundMotifTag = false;
    private boolean hasFoundGoalTag = false;

    private List<LLResultTypes.FiducialResult> foundTags;
    private LLResultTypes.FiducialResult goalTag;
    public static SorterNode.NodeOption[] gameMotif;
    private boolean isBlueAlliance;

    private double goal_tx;
    private double goal_ty;

    private enum TurretVisionState {
        SEARCHING,
        LOCKED
    }

    private TurretVisionState state;

    //TODO: boolean value for alliance side

    public TurretSubsystem(HardwareMap hwMap, boolean isBlueAlliance) {
        m_limelight = hwMap.get(Limelight3A.class, "limelight");
        m_motor = new Motor(hwMap, "turretMotor", 28, 6000);
        m_motor.setRunMode(Motor.RunMode.RawPower);

        m_limelight.setPollRateHz(POLL_RATE); //arbitrary for now
        m_limelight.pipelineSwitch(8);
        m_limelight.start();

        this.isBlueAlliance = isBlueAlliance;
        state = TurretVisionState.SEARCHING;
    }

    /*Roadmap
    * 1. Search for obelisk & goal
    * 2. Verify that motif is found
    * 3. Verify that goal tag is current alliance goaltag
    * 4. Lock onto goal tag
    */

    public static double BASE_SEARCH_SPEED = 0.3;
    private void search() {
        if (!searchCheck()) {
            //switch state
            state = TurretVisionState.LOCKED;
            return;
        }
        m_motor.set(BASE_SEARCH_SPEED);
    }

    //min command: min power for motor to move
    //kP is the amplifier (negative or not depends on orientation of camera)
    public static double kP = -0.1;
    public static double MIN_COMMAND = 0.05;
    private void lock() {
        goal_tx = goalTag.getTargetXDegrees();

        double heading_error = -goal_tx;
        double adjustment = 0;

        //deadband
        if (Math.abs(goal_tx) > 1) {
            //adjusting based on error direction
            adjustment = kP * heading_error + Math.copySign(MIN_COMMAND, heading_error);
        }

        m_motor.set(adjustment);
    }

    //checking if found game motif & if goal wanted found
    private boolean searchCheck() {
        for (LLResultTypes.FiducialResult curTag : foundTags) {
            int curID = curTag.getFiducialId();

            //motif mapping
            if (MOTIF_MAP.containsKey(curTag.getFiducialId()) && !hasFoundMotifTag) {
                gameMotif = MOTIF_MAP.get(curID);
                hasFoundMotifTag = true;
                continue;
            }

            //blue check
            if (!hasFoundGoalTag) {
                if (curID == BLUE_TAG && isBlueAlliance) {
                    goalTag = curTag;
                    gameMotif = MOTIF_MAP.get(curTag.getFiducialId());
                    hasFoundGoalTag = true;
                    continue;
                }

                if (curID == RED_TAG && !isBlueAlliance) {
                    goalTag = curTag;
                    hasFoundGoalTag = true;
                }
            }

            if (hasFoundGoalTag && hasFoundMotifTag) break;
        }
        if (hasFoundGoalTag && hasFoundMotifTag) {return true;} else {return false;}
    }

    @Override
    public void periodic() {
        results = m_limelight.getLatestResult();
        if (results == null || !results.isValid()) return;

        foundTags = results.getFiducialResults();

        switch (state) {
            case SEARCHING:
                search();
                break;
            case LOCKED:
                lock();
                break;
        }
    }
}
