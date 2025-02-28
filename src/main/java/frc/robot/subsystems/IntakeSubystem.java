package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.IntakeConstants;

public class IntakeSubystem extends SubsystemBase {
    TalonFX m_intakeMotor;
    VoltageOut m_request = new VoltageOut(0);

    CurrentLimitsConfigs intakeCurrentLimits = new CurrentLimitsConfigs();

    public IntakeSubystem () {
        m_intakeMotor = new TalonFX(IntakeConstants.IntakeMotorId, "Canivore");
        var slot0Configs = new Slot0Configs();

        slot0Configs.kP = IntakeConstants.kP;
        slot0Configs.kI = IntakeConstants.kI;
        slot0Configs.kD = IntakeConstants.kD;

        m_intakeMotor.getConfigurator().apply(slot0Configs);

        intakeCurrentLimits.withStatorCurrentLimitEnable(true);
        intakeCurrentLimits.withStatorCurrentLimit(40);

        m_intakeMotor.getConfigurator().apply(intakeCurrentLimits);
    }

    @Override
    public void periodic() {}

    public void dropCoral() {

        //set voltage output
        m_intakeMotor.setVoltage(-1);
    }
    public void InCoral() {

        //set voltage output
        m_intakeMotor.setVoltage(1);
    }

    public void algeaOut(){

        //Set Voltage output

        m_intakeMotor.setVoltage(3);
    }

    public void feedStop() {
        
        m_intakeMotor.setVoltage(0);
    }
} 
