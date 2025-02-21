package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
    TalonFX leftElevatorMotor;
    TalonFX rightElevatorMotor;

    FeedbackConfigs elevatorFeedbackConfigs = new FeedbackConfigs();
    CurrentLimitsConfigs elevatorCurrentLimits = new CurrentLimitsConfigs();

    private final PositionVoltage m_request = new PositionVoltage(0);


public ElevatorSubsystem() {

    leftElevatorMotor = new TalonFX(ElevatorConstants.LeftElevatorMotorId, "Canivore");
    rightElevatorMotor = new TalonFX(ElevatorConstants.RightElevatorMotorId, "Canivore");

    // Factory default on motors
    leftElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());
    rightElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());

    // Motion Profile Position
    var slot0Configs = new Slot0Configs();

    slot0Configs.kP = ElevatorConstants.kP;
    slot0Configs.kI = ElevatorConstants.kI;
    slot0Configs.kD = ElevatorConstants.kD;

    leftElevatorMotor.getConfigurator().apply(slot0Configs);
    rightElevatorMotor.getConfigurator().apply(slot0Configs);

    elevatorFeedbackConfigs.withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor);

    leftElevatorMotor.getConfigurator().apply(elevatorFeedbackConfigs);

    elevatorCurrentLimits.withStatorCurrentLimit(40);
    elevatorCurrentLimits.withStatorCurrentLimitEnable(true);

    leftElevatorMotor.getConfigurator().apply(elevatorCurrentLimits);
    rightElevatorMotor.getConfigurator().apply(elevatorCurrentLimits);
}

}
