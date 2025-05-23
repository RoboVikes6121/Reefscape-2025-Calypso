package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {

    TalonFX m_leaderElevatorMotor;
    TalonFX m_followerElevatorMotor;
    FeedbackConfigs elevatorFeedbackConfigs = new FeedbackConfigs();
    CurrentLimitsConfigs elevatorCurrentLimits = new CurrentLimitsConfigs();

    private final PositionVoltage m_request = new PositionVoltage(0);

public ElevatorSubsystem() {

    m_leaderElevatorMotor = new TalonFX(ElevatorConstants.leaderElevatorMotorId, "Canivore");
    m_followerElevatorMotor = new TalonFX(ElevatorConstants.followerElevatorMotorId, "Canivore");

    m_leaderElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());
    m_followerElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());

    var slot0Configs = new Slot0Configs();
    
    slot0Configs.kG = ElevatorConstants.kG;
    slot0Configs.kS = ElevatorConstants.kS;
    slot0Configs.kV = ElevatorConstants.kV;
    slot0Configs.kA = ElevatorConstants.kA;
    slot0Configs.kP = ElevatorConstants.kP;
    slot0Configs.kI = ElevatorConstants.kI;
    slot0Configs.kD = ElevatorConstants.kD;

    m_leaderElevatorMotor.getConfigurator().apply(slot0Configs);
    m_followerElevatorMotor.getConfigurator().apply(slot0Configs);

    elevatorFeedbackConfigs.withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor);

    m_leaderElevatorMotor.getConfigurator().apply(elevatorFeedbackConfigs);

    elevatorCurrentLimits.withStatorCurrentLimit(30); //max 120  controlls accelertion of elvator
    elevatorCurrentLimits.withStatorCurrentLimitEnable(true);

    m_leaderElevatorMotor.getConfigurator().apply(elevatorCurrentLimits);
    m_followerElevatorMotor.getConfigurator().apply(elevatorCurrentLimits);

}
public void setPosition(double position) {

    TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State(); 

    m_request.Position = m_setpoint.position; 
    m_request.Velocity = m_setpoint.velocity; 
    m_leaderElevatorMotor.setControl(m_request.withPosition(position).withSlot(0));
    m_followerElevatorMotor.setControl(m_request.withPosition(position).withSlot(0));
}

public void elevatorDiagnostics(){

}

@Override
public void periodic() {

}

}