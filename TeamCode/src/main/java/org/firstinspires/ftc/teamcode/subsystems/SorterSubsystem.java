package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.util.RTPAxon;

import java.util.HashMap;

//2 = intakeNode
@Configurable
public class SorterSubsystem extends SubsystemBase{

    private enum SorterState {
        INTAKE,
        SHOOT
    }

    private enum NodeOption {
        PURPLE,
        GREEN,
        EMPTY
    }

    private CRServo m_servo;
    private RTPAxon m_axon;
    private AnalogInput m_encoder;

    private RevColorSensorV3 node1;
    private RevColorSensorV3 node2;
    private RevColorSensorV3 node3;

    private HashMap<Integer, NodeOption> sorterNodes = new HashMap<Integer, NodeOption>();
    private SorterState state;

    public SorterSubsystem(HardwareMap hwMap) {
        m_servo = hwMap.get(CRServo.class, "sorterServo");
        m_encoder = hwMap.get(AnalogInput.class, "sorterEncoder");

        node1 = hwMap.get(RevColorSensorV3.class, "node1");
        node2 = hwMap.get(RevColorSensorV3.class, "node2");
        node3 = hwMap.get(RevColorSensorV3.class, "node3");

        node1.enableLed(true);
        node2.enableLed(true);
        node3.enableLed(true);

        sorterNodes.put(1, NodeOption.EMPTY);
        sorterNodes.put(2, NodeOption.EMPTY);
        sorterNodes.put(3, NodeOption.EMPTY);

        m_axon = new RTPAxon(hwMap, "servo", "encoder");
        //m_axon.setRtp(true);

        state = SorterState.INTAKE;
    }

    private NodeOption checkNode(RevColorSensorV3 node) {
        boolean isClose = node.getDistance(DistanceUnit.CM) < 6;

        if (isClose) {
            NormalizedRGBA n = node.getNormalizedColors();
            float[] hsv = new float[3];
            int argb = n.toColor();
            Color.colorToHSV(argb, hsv);

            float hue = hsv[0];

            //adjust based on test class
            boolean isGreen = hue > 190 && hue < 250;

            if(isGreen) return NodeOption.GREEN;
            return NodeOption.PURPLE;
        }
        return NodeOption.EMPTY;
    }

    public static double shootOffset = 15;
    private void switchNodes(int node1, int node2) {
        double changeAngle = 0;

        if(node2 == 4) {
            changeAngle = shootOffset;
        }


    }

    private void updateNodes() {
        sorterNodes.put(1, checkNode(node1));
        sorterNodes.put(2, checkNode(node2));
        sorterNodes.put(3, checkNode(node3));
    }

    @Override
    public void periodic() {
        switch (state) {
            case INTAKE:


                break;
            case SHOOT:


                break;
        }

    }
}
