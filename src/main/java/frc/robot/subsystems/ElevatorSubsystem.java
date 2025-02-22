package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
    TalonFX leaderElevatorMotor;
    TalonFX followerElevatorMotor;

    FeedbackConfigs elevatorFeedbackConfigs = new FeedbackConfigs();
    CurrentLimitsConfigs elevatorCurrentLimits = new CurrentLimitsConfigs();

    private final PositionVoltage m_request = new PositionVoltage(0);

public ElevatorSubsystem() {

    leaderElevatorMotor = new TalonFX(ElevatorConstants.leaderElevatorMotorId, "Canivore");
    followerElevatorMotor = new TalonFX(ElevatorConstants.followerElevatorMotorId, "Canivore");

    var talonFXConfigs = new TalonFXConfiguration();

    // Factory default on motors
    leaderElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());
    followerElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());

    //leader and follower motors
    followerElevatorMotor.setControl(new Follower(leaderElevatorMotor.getDeviceID(), false));

    // Motion Profile Position
    var slot0Configs = talonFXConfigs.Slot0;

    slot0Configs.kS = ElevatorConstants.kS;
    slot0Configs.kV = ElevatorConstants.kV;
    slot0Configs.kA = ElevatorConstants.kA;
    slot0Configs.kP = ElevatorConstants.kP;
    slot0Configs.kI = ElevatorConstants.kI;
    slot0Configs.kD = ElevatorConstants.kD;

    //set motion magic settings
    var motionMagicConfigs = talonFXConfigs.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity = 5;
    motionMagicConfigs.MotionMagicAcceleration = 5;
    motionMagicConfigs.MotionMagicJerk = 10;



    leaderElevatorMotor.getConfigurator().apply(talonFXConfigs);
    followerElevatorMotor.getConfigurator().apply(talonFXConfigs);

    elevatorFeedbackConfigs.withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor);

    leaderElevatorMotor.getConfigurator().apply(elevatorFeedbackConfigs);

    elevatorCurrentLimits.withStatorCurrentLimit(40);
    elevatorCurrentLimits.withStatorCurrentLimitEnable(true);

    leaderElevatorMotor.getConfigurator().apply(elevatorCurrentLimits);
    followerElevatorMotor.getConfigurator().apply(elevatorCurrentLimits);
}

}
