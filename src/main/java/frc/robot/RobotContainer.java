// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.AlignCommand;
import frc.robot.commands.CommandSwerveDrivetrain;
import frc.robot.commands.DropCoral;
import frc.robot.commands.L1Elevator;
import frc.robot.commands.L2Elevator;
import frc.robot.commands.L3Elevator;
import frc.robot.commands.L4Elevator;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class RobotContainer {
    
    private final CommandXboxController m_operatorController = new CommandXboxController(1);
    private static final ElevatorSubsystem leaderElevatorMotor = new ElevatorSubsystem();
    private static final ElevatorSubsystem followerElevatorMotor = new ElevatorSubsystem();
    private static final IntakeSubystem m_intakeMotor = new IntakeSubystem();
    private static final WristSubsystem wristMotor = new WristSubsystem();

    private final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
    private final VisionSubsystem m_vision = new VisionSubsystem();

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

    public RobotContainer() {
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
        m_driverController.x().whileTrue(new AlignCommand(m_drivetrain, m_vision));

        drivetrain.registerTelemetry(logger::telemeterize);

        //Operator bindings
        m_operatorController.rightBumper().whileTrue(new DropCoral(m_intakeMotor));
        //m_operatorController.rightTrigger().whileFalse(new feedStop(m_intakeMotor));
        m_operatorController.a().onTrue(new L1Elevator(leaderElevatorMotor, null));
        m_operatorController.x().onTrue(new L2Elevator(leaderElevatorMotor, null));
        m_operatorController.b().onTrue(new L3Elevator(leaderElevatorMotor, null));
        m_operatorController.y().onTrue(new L4Elevator(leaderElevatorMotor, null));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}

