package frc.robot.commands;

import com.ctre.phoenix6.controls.MotionMagicVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class BargeYeet extends Command {

ElevatorSubsystem m_elevator;
WristSubsystem m_wrist;

double elevatorHeight = 45;
double wristCenterPosition = 4.5;

public BargeYeet(ElevatorSubsystem m_elevator, WristSubsystem m_wrist) {

    this.m_elevator = m_elevator;
    this.m_wrist = m_wrist; 

    addRequirements(m_elevator, m_wrist);
}

@Override
public void initialize() {}

@Override
public void execute() {

    m_elevator.setPosition(elevatorHeight);
    m_wrist.setPosition(wristCenterPosition);

}
@Override
public void end(boolean interrupted) {}

@Override
public boolean isFinished() {
 return false;

}
}
