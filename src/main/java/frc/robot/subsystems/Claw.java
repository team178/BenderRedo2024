package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

public class Claw extends SubsystemBase {
    private Solenoid m_solenoid;

    public Claw() {
        m_solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ClawConstants.kSolenoidPort);
        m_solenoid.set(false);
    }

    public void toggleClaw() {
        m_solenoid.toggle();
    }

    public CommandBase runToggleClaw() {
        return runOnce(() -> toggleClaw());
    }
}
