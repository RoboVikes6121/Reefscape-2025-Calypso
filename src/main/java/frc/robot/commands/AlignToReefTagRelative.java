/*package frc.robot.commands;

import frc.robot.commands.CommandSwerveDrivetrain;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.limelightConstants;
import frc.robot.subsystems.LimelightHelpers;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.ForwardPerspectiveValue;
import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentric;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class AlignToReefTagRelative extends Command {

    private CommandSwerveDrivetrain drivetrain;
private PIDController xController, yController, rotController;
private boolean isRightScore;
private Timer dontSeeTagTimer, stopTimer; 
private double tagID = -1;
private CommandSwerveDrivetrain m_drivetrain;
private SwerveRequest.FieldCentricFacingAngle m_drive;
private Pose2d m_targetPose = new Pose2d();
private final SwerveRequest.RobotCentric robotDrive = new SwerveRequest.RobotCentric();
private double positions [];
private double velX = xController.calculate(positions[2]);
private double velY = -yController.calculate(positions[0]);
private double rotValue = -rotController.calculate(positions[4]);


   // private RobotCentric limeDrive = new RobotCentric()
   // .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  //  private double alignmentSpeed, m_xspeed;
  //  private int m_pipeline;


    // Constructor accepts limeDrive as a parameter from RobotContainer
    public AlignToReefTagRelative(boolean isRightScore, CommandSwerveDrivetrain drivetrain) {
        xController = new PIDController(limelightConstants.X_REEF_ALIGNMENT_P, 0.0, 0);
        yController = new PIDController(limelightConstants.Y_REEF_ALIGNMENT_P, 0.0, 0);
        rotController = new PIDController(limelightConstants.ROT_REEF_ALIGNMENT_P, 0, 0);
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);  // Ensure the drivetrain is required for this command
    }

    @Override
    public void initialize() {
    this.stopTimer = new Timer();
    this.stopTimer.start();
    this.dontSeeTagTimer = new Timer();
    this.dontSeeTagTimer.start();

    rotController.setSetpoint(limelightConstants.ROT_SETPOINT_REEF_ALIGNMENT);
    rotController.setTolerance(limelightConstants.ROT_TOLERANCE_REEF_ALIGNMENT);

    xController.setSetpoint(limelightConstants.X_SETPOINT_REEF_ALIGNMENT);
    xController.setTolerance(limelightConstants.X_TOLERANCE_REEF_ALIGNMENT);

    yController.setSetpoint(isRightScore ? limelightConstants.Y_SETPOINT_REEF_ALIGNMENT : -limelightConstants.Y_SETPOINT_REEF_ALIGNMENT);
    yController.setTolerance(limelightConstants.Y_TOLERANCE_REEF_ALIGNMENT);

    tagID = LimelightHelpers.getFiducialID("");
    }

    @Override
    public void execute() {
        if (LimelightHelpers.getTV("") && LimelightHelpers.getFiducialID("") == tagID) {
            this.dontSeeTagTimer.reset();

          double[] positions = LimelightHelpers.getBotPose_TargetSpace("");
            SmartDashboard.putNumber("x", positions[2]);

            double xSpeed = xController.calculate(positions[2]);
            SmartDashboard.putNumber("xspee", xSpeed);
            double ySpeed = -yController.calculate(positions[0]);
            double rotValue = -rotController.calculate(positions[4]);

            
            double positions[] = LimelightHelpers.getBotPose_TargetSpace("");
            double velX = xController.calculate(positions[2]);
            double velY = -yController.calculate(positions[0]);
            double rotValue = -rotController.calculate(positions[4]);
            
            m_drivetrain.setControl(robotDrive
            .withVelocityX(velX) 
            .withVelocityY(velY)
            .withRotationalRate(rotValue));

            if (!rotController.atSetpoint() ||
                !yController.atSetpoint() ||
                !xController.atSetpoint()) {
                    stopTimer.reset();
                }
        } else {
            m_drivetrain.setControl(robotDrive
            .withVelocityX(velX) 
            .withVelocityY(velY)
            .withRotationalRate(rotValue));
        }
        SmartDashboard.putNumber("poseValidTimer", stopTimer.get());
        System.out.println(positions);
        }

    @Override
    public void end(boolean interrupted) {
        m_drivetrain.setControl(robotDrive
            .withVelocityX(velX) 
            .withVelocityY(velY)
            .withRotationalRate(rotValue));
}

@Override
public boolean isFinished() {
    return this.dontSeeTagTimer.hasElapsed(limelightConstants.DONT_SEE_TAG_WAIT_TIME) ||
    stopTimer.hasElapsed(limelightConstants.POSE_VALIDATION_TIME);
}

} */