package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;


import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.constants.Constants.IntakeConstants;


public class IntakeSubystem extends SubsystemBase {
    TalonFX m_intakeMotor;
    VoltageOut m_request = new VoltageOut(0);

    CANrange m_intakeSensor;
    Trigger m_hasGamePiece;
    


    CurrentLimitsConfigs intakeCurrentLimits = new CurrentLimitsConfigs();

    public IntakeSubystem () {
        m_intakeMotor = new TalonFX(IntakeConstants.IntakeMotorId, "Canivore");
        var slot0Configs = new Slot0Configs();
       
        CANrangeConfiguration CANrangeConfiguration = new CANrangeConfiguration();
        CANrangeConfiguration.ProximityParams.ProximityThreshold = IntakeConstants.PROXIMITY_THRESHOLD;


        slot0Configs.kP = IntakeConstants.kP;
        slot0Configs.kI = IntakeConstants.kI;
        slot0Configs.kD = IntakeConstants.kD;

        m_intakeMotor.getConfigurator().apply(slot0Configs);

        intakeCurrentLimits.withStatorCurrentLimitEnable(true);
        intakeCurrentLimits.withStatorCurrentLimit(110);

        m_intakeMotor.getConfigurator().apply(intakeCurrentLimits);

        m_intakeSensor= new CANrange(IntakeConstants.SensorId, "Canivore");
        m_intakeSensor.getConfigurator().apply(CANrangeConfiguration);
        m_hasGamePiece = new Trigger(this::isDetected).debounce(IntakeConstants.DEBOUNCE);
    }

    //boolean coralDetected = isDetected();
        

    public Trigger hasCoral(){
        return m_hasGamePiece;
    }

    @Override
    public void periodic() {
       SmartDashboard.putBoolean("hasGamePiece", isDetected());

    }
    private boolean isDetected(){
        return m_intakeSensor.getIsDetected().getValue();

        
    }
    public void dropCoral() {
        m_intakeMotor.setVoltage(-1.5);
    }

    /*public void intakeCoralAuto() {
        if (coralDetected = false) {
            m_intakeMotor.setVoltage(-1.5);
        } else {
            m_intakeMotor.setVoltage(0);
        }
    } */

    public void dropAlgae() {
        m_intakeMotor.setVoltage(-5);
    }

    public void InCoral() {

        //set voltage output
        m_intakeMotor.setVoltage(1.5);
    }

    public void algeaOut(){

        //Set Voltage output
        m_intakeMotor.setVoltage(3);
    }

    public void feedStop() {
        
        m_intakeMotor.setVoltage(0);
    }
} 
