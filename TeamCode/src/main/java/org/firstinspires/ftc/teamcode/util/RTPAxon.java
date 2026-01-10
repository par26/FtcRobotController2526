package org.firstinspires.ftc.teamcode.util;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.hardware.AbsoluteAnalogEncoder;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Wrapper class for CRServoEx with AbsoluteAnalogEncoder
 * Provides easy setup, telemetry, and positional control for Axon servos
 */
public class RTPAxon {
    // SolversLib hardware
    private final CRServoEx crServo;
    private final AbsoluteAnalogEncoder encoder;

    // Configuration
    private final String servoName;
    private final String encoderName;

    private double kP;
    private double kI;
    private double kD;

    // Control state
    private double targetPosition; // in degrees
    private boolean positionControlEnabled;

    // Telemetry fields
    public int initAttempts = 0;
    private double lastSetPosition = 0;

    // region Constructors

    /**
     * Basic constructor with default PIDF values
     * Uses degrees as angle unit, 3.3V encoder range
     *
     * @param hwMap Hardware map
     * @param servoID Name of CR servo in config
     * @param encoderID Name of analog encoder in config
     */
    public RTPAxon(HardwareMap hwMap, String servoID, String encoderID) {
        this(hwMap, servoID, encoderID, new PIDFCoefficients(0.01, 0.0, 0.005, 0.0));
    }

    /**
     * Constructor with custom PIDF coefficients
     *
     * @param hwMap Hardware map
     * @param servoID Name of CR servo in config
     * @param encoderID Name of analog encoder in config
     * @param pidfCoeffs Custom PIDF coefficients for position control
     */
    public RTPAxon(HardwareMap hwMap, String servoID, String encoderID, PIDFCoefficients pidfCoeffs) {
        this.servoName = servoID;
        this.encoderName = encoderID;

        // Initialize encoder (3.3V range, degrees)
        encoder = new AbsoluteAnalogEncoder(hwMap, encoderID, 3.3, AngleUnit.DEGREES);

        // Initialize CRServoEx in OptimizedPositionalControl mode
        crServo = new CRServoEx(
                hwMap,
                servoID,
                encoder,
                CRServoEx.RunMode.OptimizedPositionalControl
        );

        // Set PIDF coefficients
        crServo.setPIDF(pidfCoeffs);

        // Optional: Set power caching tolerance for loop optimization
        crServo.setCachingTolerance(0.0001);

        // Wait for valid encoder reading
        waitForValidEncoder();

        // Initialize to current position
        targetPosition = getCurrentPosition();
        positionControlEnabled = true;
        lastSetPosition = targetPosition;
    }

    /**
     * Advanced constructor with encoder configuration
     *
     * @param hwMap Hardware map
     * @param servoID Name of CR servo in config
     * @param encoderID Name of analog encoder in config
     * @param analogRange Voltage range (3.3 or 5.0)
     * @param angleUnit Angle unit (DEGREES or RADIANS)
     * @param pidfCoeffs PIDF coefficients
     */
    public RTPAxon(HardwareMap hwMap, String servoID, String encoderID,
                   double analogRange, AngleUnit angleUnit, PIDFCoefficients pidfCoeffs) {
        this.servoName = servoID;
        this.encoderName = encoderID;

        // Initialize encoder with custom settings
        encoder = new AbsoluteAnalogEncoder(hwMap, encoderID, analogRange, angleUnit);

        // Initialize CRServoEx
        crServo = new CRServoEx(
                hwMap,
                servoID,
                encoder,
                CRServoEx.RunMode.OptimizedPositionalControl
        );

        crServo.setPIDF(pidfCoeffs);
        crServo.setCachingTolerance(0.0001);

        waitForValidEncoder();

        targetPosition = getCurrentPosition();
        positionControlEnabled = true;
        lastSetPosition = targetPosition;
    }

    /**
     * Wait for encoder to provide valid readings
     */
    private void waitForValidEncoder() {
        initAttempts = 0;
        double voltage;

        do {
            voltage = encoder.getVoltage();
            initAttempts++;

            if (initAttempts > 50) {
                break;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}

        } while (Math.abs(voltage) < 0.1);
    }

    // endregion

    // region Position Control Methods

    /**
     * Set target position in degrees
     * Servo will automatically move to this position using shortest path
     *
     * @param degrees Target position in degrees (0-360)
     */
    public void setTargetPosition(double degrees) {
        if (!positionControlEnabled) {
            enablePositionControl();
        }

        // Convert to radians for CRServoEx
        targetPosition = degrees;
        lastSetPosition = degrees;
        crServo.set(Math.toRadians(degrees));
    }

    /**
     * Change target position by a delta
     *
     * @param deltaDegrees Amount to change position by (in degrees)
     */
    public void changeTargetPosition(double deltaDegrees) {
        setTargetPosition(targetPosition + deltaDegrees);
    }

    /**
     * Get current target position
     *
     * @return Target position in degrees
     */
    public double getTargetPosition() {
        return targetPosition;
    }

    /**
     * Get current actual position from encoder
     *
     * @return Current position in degrees (0-360)
     */
    public double getCurrentPosition() {
        // CRServoEx uses radians internally, convert to degrees
        double radians = crServo.get();
        double degrees = Math.toDegrees(radians);

        // Normalize to 0-360
        while (degrees < 0) degrees += 360;
        while (degrees >= 360) degrees -= 360;

        return degrees;
    }

    /**
     * Get current position error
     *
     * @return Error in degrees (target - current)
     */
    public double getError() {
        double current = getCurrentPosition();
        double error = targetPosition - current;

        // Normalize to -180 to +180 (shortest path)
        while (error > 180) error -= 360;
        while (error < -180) error += 360;

        return error;
    }

    /**
     * Check if servo is at target position within default tolerance (5°)
     *
     * @return True if at target
     */
    public boolean isAtTarget() {
        return isAtTarget(5.0);
    }

    /**
     * Check if servo is at target position within custom tolerance
     *
     * @param toleranceDegrees Tolerance in degrees
     * @return True if at target
     */
    public boolean isAtTarget(double toleranceDegrees) {
        return Math.abs(getError()) < toleranceDegrees;
    }

    // endregion

    // region Power Control Methods

    /**
     * Set raw power to servo (disables position control)
     * Use this for manual control
     *
     * @param power Power from -1.0 to 1.0
     */
    public void setPower(double power) {
        disablePositionControl();
        crServo.set(power);
    }

    /**
     * Enable position control mode
     */
    public void enablePositionControl() {
        if (!positionControlEnabled) {
            crServo.setRunMode(CRServoEx.RunMode.OptimizedPositionalControl);
            positionControlEnabled = true;
            // Reset to current position
            targetPosition = getCurrentPosition();
        }
    }

    /**
     * Disable position control (switches to raw power mode)
     */
    public void disablePositionControl() {
        if (positionControlEnabled) {
            crServo.setRunMode(CRServoEx.RunMode.RawPower);
            positionControlEnabled = false;
        }
    }

    /**
     * Check if position control is enabled
     *
     * @return True if position control is active
     */
    public boolean isPositionControlEnabled() {
        return positionControlEnabled;
    }

    // endregion

    // region PIDF Configuration

    /**
     * Set PIDF coefficients for position control
     *
     * @param pidfCoeffs New PIDF coefficients
     */
    public void setPIDF(PIDFCoefficients pidfCoeffs) {
        crServo.setPIDF(pidfCoeffs);
    }

    /**
     * Set PIDF coefficients individually
     *
     * @param kP Proportional gain
     * @param kI Integral gain
     * @param kD Derivative gain
     * @param kF Feed-forward gain
     */
    public void setPIDF(double kP, double kI, double kD, double kF) {
        crServo.setPIDF(new PIDFCoefficients(kP, kI, kD, kF));
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /**
     * Get current PIDF coefficients
     *
     * @return Current PIDF coefficients
     */
    /**
     * Set power caching tolerance
     * Lower values = more precise but potentially slower
     *
     * @param tolerance Caching tolerance (default: 0.0001)
     */
    public void setCachingTolerance(double tolerance) {
        crServo.setCachingTolerance(tolerance);
    }

    /**
     * Get power caching tolerance
     *
     * @return Current caching tolerance
     */
    public double getCachingTolerance() {
        return crServo.getCachingTolerance();
    }

    /**
     * Set servo direction reversed
     *
     * @param reversed True to reverse direction
     */
    public void setReversed(boolean reversed) {
        crServo.setInverted(reversed);
    }

    /**
     * Check if servo is reversed
     *
     * @return True if reversed
     */
    public boolean isReversed() {
        return crServo.getInverted();
    }

    // endregion

    // region Hardware Access

    /**
     * Get raw encoder voltage
     *
     * @return Encoder voltage (0-3.3V typically)
     */
    public double getEncoderVoltage() {
        return encoder.getVoltage();
    }

    /**
     * Get raw encoder angle in radians
     *
     * @return Angle in radians
     */
    public double getEncoderAngleRadians() {
        return crServo.getCurrentPosition();
    }

    /**
     * Get underlying CRServoEx object
     *
     * @return CRServoEx instance
     */
    public CRServoEx getCRServo() {
        return crServo;
    }

    /**
     * Get underlying AbsoluteAnalogEncoder object
     *
     * @return AbsoluteAnalogEncoder instance
     */
    public AbsoluteAnalogEncoder getEncoder() {
        return encoder;
    }

    // endregion

    // region Telemetry

    /**
     * Get formatted telemetry string with all relevant information
     *
     * @return Formatted telemetry string
     */
    @SuppressLint("DefaultLocale")
    public String log() {


        return String.format(
                "=== %s ===\n" +
                        "Encoder Voltage: %.3fV\n" +
                        "Current Position: %.2f°\n" +
                        "Target Position: %.2f°\n" +
                        "Position Error: %.2f°\n" +
                        "At Target: %s\n" +
                        "Position Control: %s\n" +
                        "PIDF: P=%.4f I=%.4f D=%.4f F=%.4f\n" +
                        "Reversed: %s\n" +
                        "Init Attempts: %d",
                servoName,
                getEncoderVoltage(),
                getCurrentPosition(),
                targetPosition,
                getError(),
                isAtTarget() ? "YES" : "NO",
                positionControlEnabled ? "ENABLED" : "DISABLED",
                this.kP, this.kI, this.kD,
                isReversed() ? "YES" : "NO",
                initAttempts
        );
    }

    /**
     * Get compact telemetry string (one line)
     *
     * @return Compact telemetry string
     */
    @SuppressLint("DefaultLocale")
    public String logCompact() {
        return String.format(
                "%s: %.1f° → %.1f° (err: %.1f°) %s",
                servoName,
                getCurrentPosition(),
                targetPosition,
                getError(),
                isAtTarget() ? "✓" : "..."
        );
    }

    // endregion
}