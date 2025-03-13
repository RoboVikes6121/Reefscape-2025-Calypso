package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LimelightConfig;
import frc.robot.subsystems.LimelightHelpers.LimelightResults;
import frc.robot.subsystems.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.subsystems.LimelightHelpers.RawFiducial;

public class LimelightSubsystem extends SubsystemBase {
  private final LimelightConfig limelightConfig;
  private final String limelightName;

  private RawFiducial[] fiducials;
  private LimelightResults limelightResults;

  public LimelightSubsystem(LimelightConfig limelightConfig) {
    this.limelightConfig = limelightConfig;
    this.limelightName = this.limelightConfig.name();

    LimelightHelpers.setCameraPose_RobotSpace(
        this.limelightName,
        this.limelightConfig.forward(),
        this.limelightConfig.side(),
        this.limelightConfig.up(),
        this.limelightConfig.roll(),
        this.limelightConfig.pitch(),
        this.limelightConfig.yaw());
    // LimelightHelpers.SetFiducialIDFiltersOverride("", new int[] { 1, 4 });
  }

  public static class NoSuchTargetException extends RuntimeException {
    public NoSuchTargetException(String message) {
      super(message);
    }
  }

  @Override
  public void periodic() {
    this.limelightResults = LimelightHelpers.getLatestResults(this.limelightName);
    SmartDashboard.putNumber("num" + this.limelightName, this.limelightResults.targets_Fiducials.length);
    // this.fiducials = LimelightHelpers.getRawFiducials(this.limelightName);

    // for (RawFiducial fiducial : fiducials) {
    // int id = fiducial.id; // Tag ID
    // double txnc = fiducial.txnc; // X offset (no crosshair)
    // double tync = fiducial.tync; // Y offset (no crosshair)
    // double ta = fiducial.ta; // Target area
    // double distToCamera = fiducial.distToCamera; // Distance to camera
    // double distToRobot = fiducial.distToRobot; // Distance to robot
    // double ambiguity = fiducial.ambiguity; // Tag pose ambiguity
    // SmartDashboard.putNumber("id", id);
    // SmartDashboard.putNumber("txnc", txnc);
    // SmartDashboard.putNumber("tync", tync);
    // SmartDashboard.putNumber("ta", ta);
    // SmartDashboard.putNumber("distToCamera", distToCamera);
    // SmartDashboard.putNumber("distToRobot", distToRobot);
    // SmartDashboard.putNumber("ambiguity", ambiguity);
    // }
  }

  public void setFiducialIDFiltersOverride(int[] ids) {
    LimelightHelpers.SetFiducialIDFiltersOverride(this.limelightName, ids);
  }

  public RawFiducial getRawFiducialWithId(int id) {
    for (RawFiducial fiducial : fiducials) {
      if (fiducial.id != id) {
        continue;
      }

      return fiducial;
    }
    throw new NoSuchTargetException("No target with ID " + id + " is in view!");
  }

  public LimelightTarget_Fiducial getTargetFiducialWithId(int id) {
    for (LimelightTarget_Fiducial fiducial : limelightResults.targets_Fiducials) {
      if (fiducial.fiducialID != (double) id) {
        continue;
      }

      return fiducial;
    }

    throw new NoSuchTargetException("No target with ID " + id + " is in view!");
  }

  public RawFiducial getFiducialWithIdFirstMatch(int[] ids) {
    for (RawFiducial fiducial : fiducials) {
      if (!Arrays.stream(ids).anyMatch(i -> i == fiducial.id)) {
        continue;
      }

      return fiducial;
    }
    throw new NoSuchTargetException("No target with ID " + ids + "is in view!");
  }
}