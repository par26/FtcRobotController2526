//package org.firstinspires.ftc.teamcode.opmodes;
//
//import com.seattlesolvers.solverslib.command.CommandOpMode;
//import com.seattlesolvers.solverslib.command.InstantCommand;
//import com.seattlesolvers.solverslib.command.RunCommand;
//import com.seattlesolvers.solverslib.gamepad.GamepadEx;
//import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
//
//import org.firstinspires.ftc.teamcode.commands.DriveCommand;
//import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.ManualTurretSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;
//
//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
//public class TeleOp extends CommandOpMode {
//
//    private GamepadEx m_driver1;
//    private GamepadEx m_driver2;
//    private DriveSubsystem m_drive;
//
//    private IntakeSubsystem m_intake;
//    private OuttakeSubsystem m_outtake;
//
//    private ManualTurretSubsystem m_turret;
//
//    @Override
//    public void initialize() {
//        m_driver1 = new GamepadEx(gamepad1);
//        m_driver2 = new GamepadEx(gamepad2);
//        m_drive = new DriveSubsystem(hardwareMap);
//        m_intake = new IntakeSubsystem(hardwareMap);
//        m_outtake = new OuttakeSubsystem(hardwareMap);
//
//        m_turret = new ManualTurretSubsystem(hardwareMap);
//
//        register(m_drive, m_intake, m_outtake, m_turret);
//
//        m_drive.setDefaultCommand(
//                new DriveCommand(
//                        m_drive,
//                        () -> m_driver1.getLeftY(),
//                        () -> -m_driver1.getLeftX(),
//                        () -> -m_driver1.getRightX(),
//                        () -> true,
//                        () -> m_driver1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).get() ? 0.35 : 1.0
//                )
//        );
//
//        m_turret.setDefaultCommand(
//                new RunCommand(() -> m_turret.setPower(
//                        () -> m_driver2.getRightX()
//                ), m_intake)
//        );
//
//        m_intake.setDefaultCommand(
//                new RunCommand(() -> m_intake.intake(), m_intake)
//        );
//
//        m_driver1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
//                .whileHeld(new InstantCommand(() -> m_intake.reverse(), m_intake));
//
//        m_driver1.getGamepadButton(GamepadKeys.Button.B)
//                .whenPressed(new InstantCommand(() -> m_outtake.spin(true), m_outtake));
//
//        m_driver1.getGamepadButton(GamepadKeys.Button.X)
//                .whenPressed(new InstantCommand(() -> m_outtake.spin(false), m_outtake));
//
//    }
//
//
//}
