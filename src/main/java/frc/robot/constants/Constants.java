package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d;

public class Constants {

    public static class ElevatorConstants {
        public static final int leaderElevatorMotorId = 21;
        public static final int followerElevatorMotorId = 22;
        public static final double kP = .3;
        public static final double kI = .0;
        public static final double kD = .0;
        public static final double kS = .0;
        public static final double kV = .0;
        public static final double kA = 0;
        public static final double kG = .1;
    }

    public static class WristConstants {
        public static final int WristMotorId = 31;
        public static final double GEAR_RATIO = 2.5;
        public static final double kP = .5;
        public static final double kI = .0;
        public static final double kD = .0;
    }

    public static class IntakeConstants {
        public static final int IntakeMotorId = 41;
        public static final int SensorId = 42;
        public static final double kP = .0;
        public static final double kI = .0;
        public static final double kD = .0;
        public static final double PROXIMITY_THRESHOLD = 0.1;
        public static final double DEBOUNCE = 0.00;
    }

    public static final class aprilTagConstants {
   
        public static int REEF_AB_TAGID;
        public static int REEF_CD_TAGID;
        public static int REEF_EF_TAGID;
        public static int REEF_GH_TAGID;
        public static int REEF_IJ_TAGID;
        public static int REEF_KL_TAGID;

        public static int CORAL_STATION_LEFT_TAGID;
        public static int CORAL_STATION_RIGHT_TAGID;

        // beliw in meters
        public static final double INSIDE_REEF_ZONE_THRESHOLD = 1.6;
        public static final double AUTO_ADJUST_THRESHOLD = 1.8;

        public static final double CORAL_STATION_OFFSET_HORIZONTAL = 0.3;
        public static final double CORAL_STATION_OFFSET_VERTICAL = 0.03;
        public static Translation2d CORAL_STATION_LEFT_OFFSET;
        public static Translation2d CORAL_STATION_RIGHT_OFFSET;

   
    }

}
