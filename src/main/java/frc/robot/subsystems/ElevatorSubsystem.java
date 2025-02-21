package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    public final TalonFX m_elevator1;
    public final TalonFX m_elevator2;
    public boolean isZeroed = false;

    FeedbackConfigs elevatorFeedbackConfigs = new FeedbackConfigs();
    CurrentLimitsConfigs elevatorCurrentConfigs = new CurrentLimitsConfigs();

    private final PositionVoltage m_request = new PositionVoltage(0);


public pivotSubsystem() {

    m_piviotMotor = TalonFX(operatorConstants.piviotMotorID, "Canivore");


    // Factory default on motors
    m_piviotMotor.getConfigurator().apply(new TalonFXConfiguration());

    
}

}
