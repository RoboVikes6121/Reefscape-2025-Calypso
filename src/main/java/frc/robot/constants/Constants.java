package frc.robot.constants;

import static edu.wpi.first.units.Units.Radian;

import edu.wpi.first.units.measure.Angle;

public class Constants {

    public static class ElevatorConstants {
        public static final int LeftElevatorMotorId = 21;
        public static final int RightElevatorMotorId = 22;
        public static final double kP = .1;
        public static final double kI = .0;
        public static final double kD = .0;
        public static final double MaxElevatorHeight = 1;
        public static final double Level2Height = .2;
        public static final double Level3Height = .5;
        public static final double Level4Height = .9;
        public static final double AlgaePickupLow = .23;
        public static final double AlgeaPickupHigh = .53;
        public static final double StowHeight = .01;
        public static final double TargetSpeed = .0025;
    }

    public static class WristConstants {
        public static final int WristMotorId = 31;
        public static final double GEAR_RATIO = 1;
        public static final double kP = .1;
        public static final double kI = .0;
        public static final double kD = .0;
        public static final Angle L1WristPosition = Angle.ofBaseUnits(0, Radian);
        public static final Angle L2and3WristPosition = Angle.ofBaseUnits(.4, Radian);
        public static final Angle L4WristPosition = Angle.ofBaseUnits(1.75, Radian);
        public static final double LimitClawPosition = 0;
    }

    public static class IntakeConstants {
        public static final int IntakeMotorId = 41;
        public static final int SensorId = 42;
    }
}
