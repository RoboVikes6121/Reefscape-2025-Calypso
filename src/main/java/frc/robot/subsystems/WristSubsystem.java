package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.Radians;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.WristConstants;

public class WristSubsystem extends SubsystemBase {
    private final TalonFX wristMotor = new TalonFX(WristConstants.WristMotorId);

    public WristSubsystem() {
        TalonFXConfigurator config = wristMotor.getConfigurator();
        CurrentLimitsConfigs limitConfig = new CurrentLimitsConfigs();

        config.apply(limitConfig);

        wristMotor.setNeutralMode(NeutralModeValue.Brake);
        wristMotor.setPosition(Angle.ofBaseUnits(0, Radian));
    }

    public Angle getWristPosition() {
        return Angle.ofBaseUnits(wristMotor.getPosition().getValue().in(Radian)%(2*Math.PI*WristConstants.GEAR_RATIO), Radians);
    }
    public void runWristMotor (double speed) {
        wristMotor.set(speed);
    }

    @Override
    public void periodic() {
        System.out.println("wrist: " + getWristPosition().in(Radian));
    }
}
