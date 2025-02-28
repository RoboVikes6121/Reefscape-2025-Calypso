package frc.robot.commands;

import com.ctre.phoenix6.controls.MotionMagicVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class WristInside extends Command {

WristSubsystem m_wrist;

double wristIntakePosition = .1;

public WristInside(WristSubsystem m_wrist) {

    this.m_wrist = m_wrist; 

    addRequirements(m_wrist);
}

@Override
public void initialize() {}

@Override
public void execute() {

    m_wrist.setPosition(wristIntakePosition);

}
@Override
public void end(boolean interrupted) {
    m_wrist.setPosition(wristIntakePosition);
}

@Override
public boolean isFinished() {
 return false;

}
}
