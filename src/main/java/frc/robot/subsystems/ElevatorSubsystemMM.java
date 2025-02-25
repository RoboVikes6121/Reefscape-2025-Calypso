/** package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Inches;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ElevatorConstants;

public class ElevatorSubsystemMM extends SubsystemBase {
    TalonFX leaderElevatorMotor;
    TalonFX followerElevatorMotor;

    private Distance lastDesiredPosition;

    Distance currentLeftPosition = Units.Inches.of(0);
    Distance currentRightPosition = Units.Inches.of(0);

    PositionVoltage positionRequest;
    VoltageOut voltageRequest = new VoltageOut(0);

    public boolean attemptingZeroing = false;
    public boolean hasZeroed = false;

    MotionMagicVoltage motionRequest;

    FeedbackConfigs elevatorFeedbackConfigs = new FeedbackConfigs();
    CurrentLimitsConfigs elevatorCurrentLimits = new CurrentLimitsConfigs();

    private final PositionVoltage m_request = new PositionVoltage(0);

public ElevatorSubsystemMM() {

    leaderElevatorMotor = new TalonFX(ElevatorConstants.leaderElevatorMotorId, "Canivore");
    followerElevatorMotor = new TalonFX(ElevatorConstants.followerElevatorMotorId, "Canivore");

    lastDesiredPosition = Units.Inches.of(0);
    voltageRequest = new VoltageOut(0);
    motionRequest = new MotionMagicVoltage(0);

    var talonFXConfigs = new TalonFXConfiguration();

    // Factory default on motors
    leaderElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());
    followerElevatorMotor.getConfigurator().apply(new TalonFXConfiguration());

    //leader and follower motors
    followerElevatorMotor.setControl(new Follower(leaderElevatorMotor.getDeviceID(), false));

    // Motion Profile Position
    var slot0Configs = talonFXConfigs.Slot0;

    slot0Configs.kG = ElevatorConstants.kG;
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

public Distance getElevatorPosition() {
    return Units.Inches.of(leaderElevatorMotor.getPosition().getValueAsDouble());
}

public boolean isAtSetPoint() {
    return (getElevatorPosition()
        .compareTo(getLastDesiredPosition().minus(ElevatorConstants.deadzoneDistance)) > 0) &&
        getElevatorPosition().compareTo(getLastDesiredPosition().plus(ElevatorConstants.deadzoneDistance)) < 0;
}

public AngularVelocity getRotorVelocity() {
    return leaderElevatorMotor.getRotorVelocity().getValue();
}

public Distance getLastDesiredPosition() {
    return lastDesiredPosition;
}

//public void setCoastMode(Boolean coastMode) {
  //  if (coastMode) {
   //     leaderElevatorMotor.getConfigurator().apply(ElevatorConstants.coastModeConfiguration);
   //     followerElevatorMotor.getConfigurator().apply(ElevatorConstants.coastModeConfiguration);
 //   } else {
  //      leaderElevatorMotor.getConfigurator().apply(ElevatorConstants.elevatorConfiguration);
  //      followerElevatorMotor.getConfigurator().apply(ElevatorConstants.elevatorConfiguration);
  //  }
//}

//public boolean isRotorVelocityZero() {
 //   return getRotorVelocity().isNear(Units.RotationsPerSecond.zero(), 0.01);
//}

public void setPosition(Distance height) {
    leaderElevatorMotor.setControl(motionRequest.withPosition(height.in(Units.Inches)));
    followerElevatorMotor.setControl(new Follower(leaderElevatorMotor.getDeviceID(), false));
    lastDesiredPosition = height;
}

public void setNeutral() {
    leaderElevatorMotor.setControl(new NeutralOut());
    followerElevatorMotor.setControl(new NeutralOut());
}

public void setVoltage(Voltage voltage) {
    leaderElevatorMotor.setControl(voltageRequest.withOutput(voltage));
    followerElevatorMotor.setControl(new Follower(leaderElevatorMotor.getDeviceID(), false));
}

public void setSoftwareLimits(boolean reverseLimitEnable, boolean forwardLimitEnable) {
    ElevatorConstants.elevatorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = reverseLimitEnable;
    ElevatorConstants.elevatorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = forwardLimitEnable;

    leaderElevatorMotor.getConfigurator().apply(ElevatorConstants.elevatorConfiguration);
    followerElevatorMotor.getConfigurator().apply(ElevatorConstants.elevatorConfiguration);
}

public void resetSensorPosition(Distance setpoint) {
    leaderElevatorMotor.setPosition(setpoint.in(Inches));
    followerElevatorMotor.setPosition(setpoint.in(Inches));
}


}
**/