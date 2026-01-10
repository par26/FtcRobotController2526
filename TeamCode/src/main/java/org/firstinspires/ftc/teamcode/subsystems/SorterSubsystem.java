package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.util.SorterNode;

@Configurable
public class SorterSubsystem extends SubsystemBase {

    private enum SorterState {
        INTAKE,
        SHOOT
    }

    private enum IntakeState {
        INTAKING,
        ALTERNATING,
    }

    private enum ShootState {
        SHOOT1,
        AWAIT1,
        SHOOT2,
        AWAIT2,
        SHOOT3,
        AWAIT3,
        FINISH,
    }

    private CRServo m_servo;
    private AnalogInput m_encoder;
    private RTPSubsystem m_rtp;

    private RevColorSensorV3 sensor1;
    private RevColorSensorV3 sensor2;
    private RevColorSensorV3 sensor3;

    private SorterNode sorterNode1;
    private SorterNode sorterNode2;
    private SorterNode sorterNode3;

    private SorterNode[] sorterNodes;

    private SorterSubsystem.SorterState baseState;
    private SorterSubsystem.IntakeState intakeState;
    private SorterSubsystem.ShootState shootState;

    private String motif;

    public static int OFFSET_ANGLE = 15;
    public static int NODE_ANGLE = 120;

    private int deviation = 0;

    public SorterSubsystem(HardwareMap hwMap, RTPSubsystem rtp) {
        m_rtp = rtp;
        m_encoder = hwMap.get(AnalogInput.class, "sorterEncoder");

        sensor1 = hwMap.get(RevColorSensorV3.class, "node1");
        sensor2 = hwMap.get(RevColorSensorV3.class, "node2");
        sensor3 = hwMap.get(RevColorSensorV3.class, "node3");

        sensor1.enableLed(true);
        sensor2.enableLed(true);
        sensor3.enableLed(true);

        sorterNode1 = new SorterNode(SorterNode.NodeOption.PURPLE);
        sorterNode2 = new SorterNode(SorterNode.NodeOption.PURPLE);
        sorterNode3 = new SorterNode(SorterNode.NodeOption.PURPLE);

        sorterNodes = new SorterNode[] {sorterNode1, sorterNode3, sorterNode2};

        baseState = SorterState.INTAKE;
        intakeState = IntakeState.INTAKING;
        shootState = ShootState.FINISH;
    }

    public void switchBaseState() {
        this.baseState = (baseState == SorterState.INTAKE) ? SorterState.SHOOT : SorterState.INTAKE;
    }

    public void switchState(SorterState state) {
        this.baseState = state;
    }

    private void rotateC() {
        m_rtp.changeAngle(NODE_ANGLE);
    }
    private void rotateCC() {
        m_rtp.changeAngle(-NODE_ANGLE);
    }

    private double nodeToShoot(SorterNode node) {
        double changeAngle = 0;

        if (node == sorterNode1) {
            changeAngle -= OFFSET_ANGLE;
            deviation -= OFFSET_ANGLE;
        } else if (node == sorterNode2) {
            changeAngle += OFFSET_ANGLE;
            deviation += OFFSET_ANGLE;
        } else {
            changeAngle += OFFSET_ANGLE + NODE_ANGLE;
            deviation += OFFSET_ANGLE;
        }

        return changeAngle;
    }

    private boolean intakeStateHandler() {
        if (sorterNode1.getStoredNode() != SorterNode.NodeOption.EMPTY
                && sorterNode2.getStoredNode() != SorterNode.NodeOption.EMPTY) {
            return true;
        }

        switch (intakeState) {
            case INTAKING:
                if (!isOccupied(sorterNode2)) {
                    intakeState = IntakeState.ALTERNATING;
                    rotateCC();
                }
                break;
            case ALTERNATING:
                if (m_rtp.isAtTarget()) {
                    intakeState = IntakeState.INTAKING;
                }
                break;
        }
        return false;
    }


    private boolean shooterStateHandler() {
        if (isEmpty()) {
            return true;
        }

        SorterNode.NodeOption aim1;
        SorterNode.NodeOption aim2;
        SorterNode.NodeOption aim3;

        switch (shootState) {
            case SHOOT1:

                break;
            case AWAIT1:

                break;
            case SHOOT2:

                break;
            case AWAIT2:

                break;
            case SHOOT3:

                break;
            case AWAIT3:

                break;
            case FINISH:

                break;
        }

        return false;
    }


    private boolean isOccupied(SorterNode node) {
        return node.getStoredNode() != SorterNode.NodeOption.EMPTY;
    }
    private boolean isEmpty() {
        if (!isOccupied(sorterNode1)
                && !isOccupied(sorterNode2)
                && !isOccupied(sorterNode3)) {
            return true;
        }
        return false;
    }
    private SorterNode.NodeOption checkNode(RevColorSensorV3 node) {
        NormalizedRGBA n = node.getNormalizedColors();
        float[] hsv = new float[3];
        int argb = n.toColor();
        Color.colorToHSV(argb, hsv);

        float hue = hsv[0];

        boolean isGreen = hue >= 140 && hue <= 180;
        boolean isPurple = hue >= 200 && hue <= 280;

        if (isGreen) return SorterNode.NodeOption.GREEN;
        if (isPurple) return SorterNode.NodeOption.PURPLE;
        return SorterNode.NodeOption.EMPTY;
    }
    private void updateNodes() {
        SorterNode.NodeOption raw1 = checkNode(sensor1);
        SorterNode.NodeOption raw2 = checkNode(sensor2);
        SorterNode.NodeOption raw3 = checkNode(sensor3);

        sorterNode1.setNode(node1Filter.update(raw1));
        sorterNode2.setNode(node2Filter.update(raw2));
        sorterNode3.setNode(node3Filter.update(raw3));
    }

    //todo: rewrite logic again (fit it into a command dummy)
    @Override
    public void periodic() {
        updateNodes();
        if (motif == null) {motif = TurretSubsystem.gameMotif;}

        switch (baseState) {
            case INTAKE:
                boolean bool1 = intakeStateHandler();
                if (bool1) baseState = SorterState.SHOOT;
                break;
            case SHOOT:
                boolean bool2 = shooterStateHandler();
                if (bool2) baseState = SorterState.INTAKE;
                break;
        }
    }

    private final StableNodeFilter node1Filter = new StableNodeFilter(6, 10);
    private final StableNodeFilter node2Filter = new StableNodeFilter(6, 10);
    private final StableNodeFilter node3Filter = new StableNodeFilter(6, 10);

    private static class StableNodeFilter {
        private SorterNode.NodeOption stable = SorterNode.NodeOption.EMPTY;
        private SorterNode.NodeOption candidate = SorterNode.NodeOption.EMPTY;
        private int streak = 0;

        private final int confirmLoops;   // loops needed to accept a new value
        private final int dropLoops;      // loops needed to drop to EMPTY (optional)

        StableNodeFilter(int confirmLoops, int dropLoops) {
            this.confirmLoops = confirmLoops;
            this.dropLoops = dropLoops;
        }

        SorterNode.NodeOption update(SorterNode.NodeOption raw) {
            // ignore if raw == stable
            if (raw == stable) {
                candidate = raw;
                streak = 0;
                return stable;
            }

            // trakc a candidate change.
            if (raw != candidate) {
                candidate = raw;
                streak = 1;
            } else {
                streak++;
            }

            // diff threshold depends on if empty
            int needed = (candidate == SorterNode.NodeOption.EMPTY) ? dropLoops : confirmLoops;

            if (streak >= needed) {
                stable = candidate;
                streak = 0;
            }

            return stable;
        }
    }
}
