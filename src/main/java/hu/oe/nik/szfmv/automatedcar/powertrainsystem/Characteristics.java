package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

public abstract class Characteristics {

    public final String name;
    public final double forwardMaxSpeed;
    public final double reverseMaxSpeed;
    public final double maxBrakeForce;
    public final double[] shiftingRatios;
    public final double finalDriveRatio;
    public final double[] shiftingUpLevels;
    public final double weightOfCar;

    public double getWeightOfCar() {
        return weightOfCar;
    }

    public final double engineTorque;
    public final double engineBrakingTorque;
    public final double wheelDiameter;
    public final double minRPM;
    public final double maxRPM;
    public final double RPMSpeedConvRate;

    public Characteristics(String name, double forwardMaxSpeed, double reverseMaxSpeed, double maxBrakeForce,
            double[] shiftingRatios, double finalDriveRatio, double[] shiftingUpLevels, double weightOfCar,
            double engineTorque, double engineBrakingTorque, double wheelDiameter, double minRPM, double maxRPM,
            double RPMSpeedConvRate) {
        this.name = name;
        this.forwardMaxSpeed = forwardMaxSpeed;
        this.reverseMaxSpeed = reverseMaxSpeed;
        this.maxBrakeForce = maxBrakeForce;
        this.shiftingRatios = shiftingRatios;
        this.finalDriveRatio = finalDriveRatio;
        this.shiftingUpLevels = shiftingUpLevels;
        this.weightOfCar = weightOfCar;
        this.engineTorque = engineTorque;
        this.engineBrakingTorque = engineBrakingTorque;
        this.wheelDiameter = wheelDiameter;
        this.minRPM = minRPM;
        this.maxRPM = maxRPM;
        this.RPMSpeedConvRate = RPMSpeedConvRate;
    }
}
