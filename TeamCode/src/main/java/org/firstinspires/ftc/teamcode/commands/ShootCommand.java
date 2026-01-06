//package org.firstinspires.ftc.teamcode.commands;
//
//import com.bylazar.configurables.annotations.Configurable;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.seattlesolvers.solverslib.command.InstantCommand;
//import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
//import com.seattlesolvers.solverslib.command.WaitCommand;
//
//import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;
//
//import kotlin.time.Instant;
//
//@Configurable
//public class ShootCommand extends CommandBase {
//
//    public static int START_COOLDOWN = 2000;
//    public static int STOP_COOLDOWN = 5500;
//    private IntakeSubsystem m_intake;
//    private OuttakeSubsystem m_outtake;
//    private SequentialCommandGroup process;
//
//    public ShootCommand(OuttakeSubsystem outtake, IntakeSubsystem intake) {
//        this.m_outtake = outtake;
//        this.m_intake = intake;
//
//        addRequirements(m_outtake, m_intake);
//    }
//
//    @Override
//    public void initialize() {
//        m_intake.set1(true);
//        m_intake.set2(false);
//
//        process = new SequentialCommandGroup(
//                new WaitCommand(START_COOLDOWN),
//                new InstantCommand(() -> m_intake.set2(true), m_intake),
//                new WaitCommand(STOP_COOLDOWN),
//                new InstantCommand(() -> m_intake.set2(false), m_intake)
//        );
//        process.schedule();
//    }
//
//    @Override
//    public boolean isFinished() {
//        return true;
//    }
//
//
//
//
//
//
//
//}
