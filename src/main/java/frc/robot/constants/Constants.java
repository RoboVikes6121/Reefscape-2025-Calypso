package frc.robot.constants;

import static edu.wpi.first.units.Units.Radian;

import edu.wpi.first.units.measure.Angle;

public class Constants {

    public static class ElevatorConstants {
        public static final int leaderElevatorMotorId = 21;
        public static final int followerElevatorMotorId = 22;
        public static final double kP = .1;
        public static final double kI = .0;
        public static final double kD = .0;
        public static final double kS = .0;
        public static final double kV = .0;
        public static final double kA = .0;
        public static final double kG = .3;
    }

    public static class WristConstants {
        public static final int WristMotorId = 31;
        public static final double GEAR_RATIO = 2.5;
        public static final double kP = .1;
        public static final double kI = .0;
        public static final double kD = .0;
    }

    public static class IntakeConstants {
        public static final int IntakeMotorId = 41;
        public static final int SensorId = 42;
        public static final double kP = .0;
        public static final double kI = .0;
        public static final double kD = .0;
    }
}
