package org.firstinspires.ftc.teamcode.subsystems.sorter;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.RTPSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.util.SorterNode;

import java.util.ArrayDeque;
import java.util.Queue;

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
        TRANSFER,
        SHOOT,
        HOLD1,
        HOLD2,
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

    private SorterNode.NodeOption[] motif;

    public static int OFFSET_ANGLE = 15;
    public static int NODE_ANGLE = 120;

    private int deviation = 0;

    //Kicker
    public static double KICKER_ACTIVATE = 0.85;
    public static double KICKER_RESET = 0;
    public static double KICKER_HOLD_TIME = 0.25;

    private final ElapsedTime kickerTimer = new ElapsedTime();

    private Servo m_kicker;
    private double curKickerAngle;

    public SorterSubsystem(HardwareMap hwMap, RTPSubsystem rtp) {
        m_rtp = rtp;
        m_encoder = hwMap.get(AnalogInput.class, "sorterEncoder");
        m_kicker = hwMap.get(Servo.class, "kicker");

        sensor1 = hwMap.get(RevColorSensorV3.class, "node1");
        sensor2 = hwMap.get(RevColorSensorV3.class, "node2");
        sensor3 = hwMap.get(RevColorSensorV3.class, "node3");

        sensor1.enableLed(true);
        sensor2.enableLed(true);
        sensor3.enableLed(true);

        sorterNode1 = new SorterNode(SorterNode.NodeOption.PURPLE);
        sorterNode2 = new SorterNode(SorterNode.NodeOption.PURPLE);
        sorterNode3 = new SorterNode(SorterNode.NodeOption.PURPLE);

        sorterNodes = new SorterNode[] {sorterNode1, sorterNode2, sorterNode3};

        baseState = SorterState.INTAKE;
        intakeState = IntakeState.INTAKING;
        shootState = ShootState.HOLD1;
    }

    //node angle changes

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

    //kicker methods
    public void kickerActivate() {
        curKickerAngle = KICKER_ACTIVATE;
    }

    public void kickerReset() {
        curKickerAngle = KICKER_RESET;
        m_rtp.changeAngle(-deviation);
    }

    //state handlers

    //1. toggle, 2nd. forced
    public void switchBaseState() {
        this.baseState = (baseState == SorterState.INTAKE) ? SorterState.SHOOT : SorterState.INTAKE;
    }
    public void switchBaseState(SorterState state) {
        this.baseState = state;
    }

    private void intakeStateHandler() {
        if (sorterNode1.getStoredNode() != SorterNode.NodeOption.EMPTY
                && sorterNode3.getStoredNode() != SorterNode.NodeOption.EMPTY) {
            switchBaseState(SorterState.SHOOT);
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
    }

    private boolean shooterStateHandler() {
        //switchstate if out of balls in sorter
        if (isEmpty()) {
            return true;
        }

        //order recalced each loop, only use index 0
        SorterNode[] order = getNodeOrder(sorterNodes ,motif);

        switch (shootState) {
            case TRANSFER:
                m_rtp.changeAngle(nodeToShoot(order[0]));
                shootState = ShootState.SHOOT;
                break;
            case SHOOT:
                if (m_rtp.isAtTarget()) {
                    kickerActivate();
                    kickerTimer.reset();
                    shootState = ShootState.HOLD1;
                }
                break;
            case HOLD1:
                if (kickerTimer.seconds() >= KICKER_HOLD_TIME) {
                    kickerReset();
                    kickerTimer.reset();
                    shootState = ShootState.HOLD2;
                }

                break;
            case HOLD2:
                if (kickerTimer.seconds() >= KICKER_HOLD_TIME) {
                    shootState = ShootState.TRANSFER;
                }
                break;
        }

        return false;
    }

    //utility

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

    /* Possible variants
    * (GPP, 1G, 2P          G -> P -> P)
    * (GPP, 2G, 1P,         G -> P -> ?)
    * (GPP, 2P || 2G        G -> P -> X)
    * (GPP, 1G, 1P)         G -> P -> X)
    * (GPP, 3G || 3P,       ? -> ? -> ?)
    * (PGP, [same input variants]
    * (PPG, [same input variants]
    * [...]
     */
    //returns whole array, but only uses index 0 b/c we want to preserve order
    private SorterNode[] getNodeOrder(SorterNode[] input, SorterNode.NodeOption[] target) {
        Queue<SorterNode> greens = new ArrayDeque<>();
        Queue<SorterNode> purples = new ArrayDeque<>();
        Queue<SorterNode> empties = new ArrayDeque<>();

        SorterNode[] out = new SorterNode[3];

        //first in first out
        for (SorterNode n : input) {
            switch (n.getStoredNode()) {
                case GREEN: greens.add(n);break;
                case PURPLE: purples.add(n);break;
                case EMPTY: empties.add(n); break;
            }
        }

        //try to match as many to target node
        for (int i = 0; i < 3; i++) {
            SorterNode.NodeOption desired = target[i];
            if (desired == SorterNode.NodeOption.GREEN && !greens.isEmpty()) {
                out[i] = greens.poll();
            } else if (desired == SorterNode.NodeOption.PURPLE && !purples.isEmpty()) {
                out[i] = purples.poll();
            }
        }

        //add any remaining
        Queue<SorterNode> remaining = new ArrayDeque<>();
        remaining.addAll(greens);
        remaining.addAll(purples);
        remaining.addAll(empties);

        for (int i = 0; i < 3; i++) {
            if (out[i] == null) {
                out[i] = remaining.poll();
            }
        }

        return out;
    }


    @Override
    public void periodic() {
        updateNodes();
        m_kicker.setPosition(curKickerAngle);

        if (motif == null) {motif = TurretSubsystem.gameMotif;}

        switch (baseState) {
            case INTAKE:
                intakeStateHandler();
                break;
            case SHOOT:
                boolean bool2 = shooterStateHandler();
                if (bool2) baseState = SorterState.INTAKE;
                break;
        }
    }

    private final StableNodeFilter node1Filter = new StableNodeFilter(4, 6);
    private final StableNodeFilter node2Filter = new StableNodeFilter(4, 6);
    private final StableNodeFilter node3Filter = new StableNodeFilter(4, 6);

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
