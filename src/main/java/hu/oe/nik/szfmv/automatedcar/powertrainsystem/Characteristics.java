package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

public abstract class Characteristics {

    public final String NAME;
    public final double FORWARD_MAX_SPEED;
    public final double REVERSE_MAX_SPEED;
    public final double MAX_BRAKE_FORCE;
    public final double[] SHIFTING_RATIOS;
    public final double FINAL_DRIVE_RATIO;
    public final double[] SHIFTING_UP_LEVELS;
    public final double WEIGHT_OF_CAR;
    public final double ENGINE_TORQUE;
    public final double ENGINE_BRAKING_TORQUE;
    public final double WHEEL_DIAMETER;
    public final double MIN_RPM;
    public final double MAX_RPM;
    public final double RPM_SPEED_CONV_RATE;

    public Characteristics(String name, double forwardMaxSpeed, double reverseMaxSpeed, double maxBrakeForce,
            double[] shiftingRatios, double finalDriveRatio, double[] shiftingUpLevels, double weightOfCar,
            double engineTorque, double engineBrakingTorque, double wheelDiameter, double minRPM, double maxRPM,
            double RPMSpeedConvRate) {
        this.NAME = name;
        this.FORWARD_MAX_SPEED = forwardMaxSpeed;
        this.REVERSE_MAX_SPEED = reverseMaxSpeed;
        this.MAX_BRAKE_FORCE = maxBrakeForce;
        this.SHIFTING_RATIOS = shiftingRatios;
        this.FINAL_DRIVE_RATIO = finalDriveRatio;
        this.SHIFTING_UP_LEVELS = shiftingUpLevels;
        this.WEIGHT_OF_CAR = weightOfCar;
        this.ENGINE_TORQUE = engineTorque;
        this.ENGINE_BRAKING_TORQUE = engineBrakingTorque;
        this.WHEEL_DIAMETER = wheelDiameter;
        this.MIN_RPM = minRPM;
        this.MAX_RPM = maxRPM;
        this.RPM_SPEED_CONV_RATE = RPMSpeedConvRate;
    }
}
