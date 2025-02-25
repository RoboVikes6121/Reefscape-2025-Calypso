/*package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystemMM;
import frc.robot.subsystems.WristSubsystem;

public class L3Elevator extends Command {

ElevatorSubsystemMM m_elevator;
WristSubsystem m_wrist;

double elevatorPosition = 10;
double speed;

public L3Elevator(ElevatorSubsystemMM m_elevator, WristSubsystem m_wrist)
{
    this.m_elevator = m_elevator; 
    this.m_wrist = m_wrist;

    addRequirements(m_elevator);
}

@Override
public void initialize() {}

@Override
public void execute() {

m_elevator.setPosition(elevatorPosition);
}
@Override
public void end(boolean interrupted) {}

@Override
public boolean isFinished() {
 return false;

}
}
*/