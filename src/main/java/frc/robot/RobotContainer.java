// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.AlgaeL2Elevator;
import frc.robot.commands.AlgaeL3Elevator;
import frc.robot.commands.AlignCommand;
import frc.robot.commands.Barge;
import frc.robot.commands.BargeUnYeet;
import frc.robot.commands.BargeYeet;
import frc.robot.commands.CommandSwerveDrivetrain;
import frc.robot.commands.DropCoral;
import frc.robot.commands.DropntCoral;
import frc.robot.commands.AlgaeOut;
import frc.robot.commands.Stow;
import frc.robot.commands.StowAlgae;
import frc.robot.commands.StowButDefautCommand;
import frc.robot.commands.L2Elevator;
import frc.robot.commands.WristCenter;
import frc.robot.commands.WristInside;
import frc.robot.commands.L3Elevator;
import frc.robot.commands.L4Elevator;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.LimelightHelpers;

public class RobotContainer {
    
    private final CommandJoystick m_operatorController = new CommandJoystick(1);
    private static final ElevatorSubsystem m_leaderElevatorMotor = new ElevatorSubsystem();
    private static final ElevatorSubsystem m_followerElevatorMotor = new ElevatorSubsystem();
    private static final IntakeSubystem m_intakeMotor = new IntakeSubystem();
    private static final WristSubsystem m_wristMotor = new WristSubsystem();
    //private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
    private SendableChooser<Command> autoChooser;
                
    
    
                
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity
                
     /* Setting up bindings for necessary control of the swerve drive platform */
     private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
                
    private final Telemetry logger = new Telemetry(MaxSpeed);
                
    private final CommandXboxController m_driverController = new CommandXboxController(0);
                
     public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();        

     //private final Command alignCommand = new AlignCommand(m_visionSubsystem, drivetrain);
                    
                    
     public RobotContainer() {     
            
        //register named commands
        NamedCommands.registerCommand("DropCoral", new DropCoral(m_intakeMotor).withTimeout(.5));
        NamedCommands.registerCommand("Stow", new Stow(m_leaderElevatorMotor, m_wristMotor).withTimeout(2));
        NamedCommands.registerCommand("L4Elevtor", new L4Elevator(m_leaderElevatorMotor,m_wristMotor).withTimeout(2));
        

        //in 2024 these 2 lines were under configure button bindings
        autoChooser = AutoBuilder.buildAutoChooser("Tests");
        SmartDashboard.putData("Auto Mode", autoChooser);
        
         configureBindings();
      
        
        }

    
        private void configureBindings() {
            // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );
    
            m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
            m_driverController.b().whileTrue(drivetrain.applyRequest(() ->
                point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))
            ));
    
            // Run SysId routines when holding back/start and X/Y.
            // Note that each routine should be run exactly once in a single log.
            m_driverController.back().and(m_driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
            m_driverController.back().and(m_driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
            m_driverController.start().and(m_driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
            m_driverController.start().and(m_driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
    
            // reset the field-centric heading on left bumper press
            m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));
    
            //atempt to align to April Tag
            //m_driverController.x().whileTrue(new AlignCommand(drivetrain, m_visionSubsystem));
    
            drivetrain.registerTelemetry(logger::telemeterize);
    
            //Operator bindings 
            m_operatorController.button(1).whileTrue(new DropCoral(m_intakeMotor));
            m_operatorController.button(7).whileTrue(new AlgaeOut(m_intakeMotor));
            m_operatorController.button(2).whileTrue(new DropntCoral(m_intakeMotor));
            m_operatorController.button(4).onTrue(new Stow(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.button(3).onTrue(new StowAlgae(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.povDown().whileTrue(new WristInside(m_wristMotor));
            m_operatorController.button(14).onTrue(new L2Elevator(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.button(8).onTrue(new AlgaeL2Elevator(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.button(15).onTrue(new L3Elevator(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.button(9).onTrue(new AlgaeL3Elevator(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.button(16).onTrue(new L4Elevator(m_leaderElevatorMotor,m_wristMotor));
            m_operatorController.button(10).onTrue(new Barge(m_leaderElevatorMotor, m_wristMotor));
            m_operatorController.button(5).onTrue(new BargeYeet(m_leaderElevatorMotor,m_wristMotor));
            m_operatorController.button(6).onTrue(new BargeUnYeet(m_leaderElevatorMotor,m_wristMotor));
            
            
            m_wristMotor.setDefaultCommand(new WristCenter(m_wristMotor));
            m_leaderElevatorMotor.setDefaultCommand(new StowButDefautCommand(m_leaderElevatorMotor));
            
           

    }

    public Command getAutonomousCommand() {
        //Run the path selected from the auto chooser
        return autoChooser.getSelected();
      }


}

