package frc.robot.commands;

import com.ctre.phoenix6.controls.MotionMagicVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class L0Elevator extends Command {

ElevatorSubsystem m_elevator;
WristSubsystem m_wrist;

double L1elevatorPosition = .1;

public L0Elevator(ElevatorSubsystem m_elevator) {

    this.m_elevator = m_elevator; 

    addRequirements(m_elevator);
}

@Override
public void initialize() {}

@Override
public void execute() {

    m_elevator.setPosition(L1elevatorPosition);

}
@Override
public void end(boolean interrupted) {}

@Override
public boolean isFinished() {
 return false;

}
}
