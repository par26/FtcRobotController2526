package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//driverhub art lol
public class ASCIISubsystem extends SubsystemBase{

    private Telemetry m_telemetry;

    public ASCIISubsystem(Telemetry telemetry) {
        this.m_telemetry = telemetry;
    }

    private void loveLog() {
        m_telemetry.addLine("");
        m_telemetry.addLine("|￣￣￣￣￣￣￣￣￣|");
        m_telemetry.addLine("            i            ");
        m_telemetry.addLine("            <3            ");
        m_telemetry.addLine("            23929              ");
        m_telemetry.addLine("|＿＿＿＿＿＿＿＿＿|");
        m_telemetry.addLine("            \\(•◡•)/     ");
        m_telemetry.addLine("              \\   /      ");
        m_telemetry.addLine("               ---       ");
        m_telemetry.addLine("               | |       ");

        m_telemetry.update();
    }

    private void bananaLog() {
        m_telemetry.addLine("");

        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⠀⠀⠀⢈⣿⣿⣿⣇⡀⠀⠀⠀⠀⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⠀⢀⣴⠟⠉⠙⠉⠉⢿⣦⡀⠀⠀⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⠀⣿⠏⠀⡄⠀⠀⠀⠄⠈⢻⣦⡀⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⣼⠏⠀⠀⠃⠀⠀⠀⠀⠀⠀⠘⣷⡀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⢸⣟⣀⣴⠶⡶⡶⢦⣄⡀⠀⠀⠀⠹⣷⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⢀⡿⠋⠑⠀⡌⢀⣢⣆⡅⠛⢳⣦⠀⠀⢿⡇⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⢠⡿⡠⠅⠢⠗⠬⠘⠜⣌⠆⠠⠤⢹⣆⠀⢸⡇⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⢨⣿⠸⣿⡽⡆⠀⠀⠀⣼⣲⣦⠀⢈⣿⠀⢾⡇⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⣽⠀⠈⠉⠀⢄⠄⠀⠉⠉⠉⢀⣾⠃⠀⣾⠇⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠪⡷⠶⣄⣄⡈⠃⢀⣀⡤⠖⠈⠀⢀⢀⣿⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⣾⠃⠀⠀⠀⡀⠁⠉⠀⠀⠀⢀⠎⠀⠈⠳⣦⡀⠀");
        m_telemetry.addLine("⠀⢀⡴⠾⣿⠀⠀⠀⠀⠂⠀⠀⠀⠀⠀⠁⣶⡀⠀⠀⠈⢻⡀");
        m_telemetry.addLine("⢀⡿⠁⢸⣿⠠⠀⢠⡄⠀⠀⠀⠀⠐⠀⠀⠨⣧⣀⠀⠀⣸⠁");
        m_telemetry.addLine("⠈⠳⠦⠾⢿⠐⢶⣾⣿⠷⠖⠀⠀⠀⠀⠀⠀⣨⣿⠳⠟⠃⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠘⣦⡘⠋⠛⠧⠀⠀⠀⠀⠀⡠⠀⢹⡇⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⣿⠙⢳⣦⣤⣦⣤⣤⡄⠀⠀⠁⢸⡆⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⢴⣟⣁⣠⣌⣹⡏⠙⣽⣏⠀⠀⠀⢼⡂⠀⠀⠀⠀");
        m_telemetry.addLine("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠛⠉⠉⠁⠀⠀⠀⠀");

        m_telemetry.update();
    }

    @Override
    public void periodic() {
        bananaLog();
    }
}
