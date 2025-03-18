package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubystem;

public class AutoIntake extends Command {

    IntakeSubystem m_intakeMotor;
    IntakeSubystem m_intakeSensor;
    boolean getIntakeSensor;

    public AutoIntake(IntakeSubystem m_intakeMotor, IntakeSubystem m_intakeSensor) {
        this.m_intakeMotor = m_intakeMotor;
        this.m_intakeMotor = m_intakeSensor;


        addRequirements(m_intakeMotor, m_intakeSensor);
    }

    // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //if (m_intakeSensor.getIntakeSensor() = true) {
        m_intakeMotor.dropCoral();
  //  } else {

    //}

  

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
