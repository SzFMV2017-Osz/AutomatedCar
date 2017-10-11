package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

public class Characteristics {
    
    public final String NAME;
    public final double FORWARD_MAX_SPEED;
    public final double REVERSE_MAX_SPEED;
    public final double PEDAL_MAX_VALUE;
    public final double[] SHIFTING_RATIOS;
    public final double FINAL_DRIVE_RATIO;
    public final double[] SHIFTING_UP_LEVELS;
    public final double[] MAX_REVOLUTION_STEPS;
    public final double WEIGHT_OF_CAR;
    public final double ENGINE_TORQUE;
    public final double WHEEL_DIAMETER;
    public final double MIN_RPM;
    public final double MAX_RPM;
    public final double RPM_SPEED_CONV_RATE;    
    
    public Characteristics(
            String name, 
            double forwardMaxSpeed,
            double reverseMaxSpeed,
            double pedalMaxValue,
            double[] shiftingRatios,
            double finalDriveRatio,
            double[] shiftingUpLevels,
            double[] maxRevolutionSteps,
            double weightOfCar,
            double engineTorque,
            double wheelDiameter,
            double minRPM,
            double maxRPM,
            double RPMSpeedConvRate){
        this.NAME = name;
        this.FORWARD_MAX_SPEED = forwardMaxSpeed;
        this.REVERSE_MAX_SPEED = reverseMaxSpeed;
        this.PEDAL_MAX_VALUE = pedalMaxValue;
        this.SHIFTING_RATIOS = shiftingRatios;
        this.FINAL_DRIVE_RATIO = finalDriveRatio;
        this.SHIFTING_UP_LEVELS = shiftingUpLevels;
        this.MAX_REVOLUTION_STEPS = maxRevolutionSteps;
        this.WEIGHT_OF_CAR = weightOfCar;
        this.ENGINE_TORQUE = engineTorque;
        this.WHEEL_DIAMETER = wheelDiameter;
        this.MIN_RPM = minRPM;
        this.MAX_RPM = maxRPM;
        this.RPM_SPEED_CONV_RATE = RPMSpeedConvRate;  
    }    
    
}
