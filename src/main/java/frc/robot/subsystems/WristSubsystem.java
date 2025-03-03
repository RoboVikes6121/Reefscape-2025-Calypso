package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.Radians;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ElevatorConstants;
import frc.robot.constants.Constants.WristConstants;

public class WristSubsystem extends SubsystemBase {
    
    TalonFX wristMotor;
    FeedbackConfigs wristFeedbackConfigs = new FeedbackConfigs();
    CurrentLimitsConfigs wristCurrentLimits = new CurrentLimitsConfigs();

    private final PositionVoltage m_request = new PositionVoltage(0);

    public WristSubsystem() {
        
        wristMotor = new TalonFX(WristConstants.WristMotorId, "Canivore");

        wristMotor.getConfigurator().apply(new TalonFXConfiguration());

        var slot0Configs = new Slot0Configs();

    slot0Configs.kP = WristConstants.kP;
    slot0Configs.kI = WristConstants.kI;
    slot0Configs.kD = WristConstants.kD;

    wristMotor.getConfigurator().apply(slot0Configs);

    wristFeedbackConfigs.withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor);

    wristMotor.getConfigurator().apply(wristFeedbackConfigs);

    wristCurrentLimits.withStatorCurrentLimit(100);//to do set hight if necessary
    wristCurrentLimits.withStatorCurrentLimitEnable(true);
    wristMotor.getConfigurator().apply(wristCurrentLimits);
    }

    public void setPosition(double position) {
        TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();

        m_request.Position = m_setpoint.position;
        m_request.Velocity = m_setpoint.velocity;
        wristMotor.setControl(m_request.withPosition(position).withSlot(0));
    }

    public void intakePosition(double position) {
        TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();

        m_request.Position = m_setpoint.position;
        m_request.Velocity = m_setpoint.velocity;
        wristMotor.setControl(m_request.withPosition(position).withSlot(0));

    }

    @Override
    public void periodic() {
        
    }
}
