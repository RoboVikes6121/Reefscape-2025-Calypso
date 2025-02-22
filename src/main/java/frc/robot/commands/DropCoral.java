package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubystem;

public class DropCoral extends Command {

    IntakeSubystem intakeMotor;
    double speed;

    public DropCoral(IntakeSubystem intakeMotor, double speed) {
        this.intakeMotor = intakeMotor;
        this.speed = speed;

        addRequirements(intakeMotor);
    }

    // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  intakeMotor.DropCoral(speed);


}
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
