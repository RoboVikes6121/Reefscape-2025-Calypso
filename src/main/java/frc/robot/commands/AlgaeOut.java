package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubystem;

public class AlgaeOut extends Command {

    IntakeSubystem m_intakeMotor;


    public AlgaeOut(IntakeSubystem m_intakeMotor) {
        this.m_intakeMotor = m_intakeMotor;


        addRequirements(m_intakeMotor);
    }

    // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  m_intakeMotor.dropAlgae();

}
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intakeMotor.feedStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
