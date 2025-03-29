package frc.robot.commands;

import java.nio.channels.WritableByteChannel;

import com.ctre.phoenix6.controls.MotionMagicVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class StowAlgae extends Command {

ElevatorSubsystem m_elevator;
WristSubsystem m_wrist;

double L1elevatorPosition = 8;
double wristCenterPosition = 10;

public StowAlgae(ElevatorSubsystem m_elevator, WristSubsystem m_wrist) {

    this.m_elevator = m_elevator;
    this.m_wrist = m_wrist;

    addRequirements(m_elevator, m_wrist);
}

@Override
public void initialize() {}

@Override
public void execute() {

    m_elevator.setPosition(L1elevatorPosition);
    m_wrist.setPosition(wristCenterPosition);

}
@Override
public void end(boolean interrupted) {}

@Override
public boolean isFinished() {
 return false;

}
}
