package frc.robot.commands;

import com.ctre.phoenix6.controls.MotionMagicVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class L1Elevator extends Command {

ElevatorSubsystem m_elevator;
WristSubsystem m_wrist;

double elevatorPosition = 1;
double speed;

public L1Elevator(ElevatorSubsystem m_elevator, WristSubsystem m_wrist)
{
    // create a Motion Magic request, voltage output
    final MotionMagicVoltage m_request = new MotionMagicVoltage(0);



    this.m_elevator = m_elevator; 
    this.m_wrist = m_wrist;

    addRequirements(m_elevator);
}

@Override
public void initialize() {}

@Override
public void execute() {

 // set target position to 100 rotations
m_elevator.setControl(m_request.withPosition(100));
//m_elevator.setPosition(elevatorPosition);
}
@Override
public void end(boolean interrupted) {}

@Override
public boolean isFinished() {
 return false;

}
}
