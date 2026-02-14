//package org.firstinspires.ftc.teamcode.subsystems;
//
//import com.bylazar.configurables.annotations.Configurable;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
//
//@Configurable
//public class KickerSubsystem {
//
//        private Servo kicker;
//
//
//
//        public static double kickPos = 0.75;
//        public static double kickRest = 0;
//
//        public static boolean reversed = false;
//
//
//
//        public KickerSubsystem(HardwareMap hwMap) {
//            kicker = hw
//            if(reversed) {
//                kicker.setInverted(true);
//            }
//
//            kicker.set
//
//
//        }
//
//        public void IntakeToggle() {
//            isIntaking = !isIntaking;
//            if (isIntaking) {
//                m_servo.setDirection(CRServo.Direction.FORWARD);
//            } else {
//                m_servo.setDirection(CRServo.Direction.REVERSE);
//            }
//
//            spinSpeed = isIntaking ? SPIN_POWER : REVERSE_POWER;
//        }
//
//
//        @Override
//        public void periodic() {
//            m_servo.setPower(spinSpeed);
//        }
//
//
//}
