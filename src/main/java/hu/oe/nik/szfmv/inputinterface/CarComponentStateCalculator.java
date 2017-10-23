package hu.oe.nik.szfmv.inputinterface;

public class CarComponentStateCalculator implements ICalculations{

    public int turnTheSteeringwheelLeft(int currentSteeringwheelState) {
        int output;
        if (currentSteeringwheelState > maxLeftSteeringWheelState){
            output = currentSteeringwheelState - 1;
        }
        else {
            output = maxLeftSteeringWheelState;
        }
        return output;
    }

    public int turnTheSteeringwheelRight(int currentSteeringwheelState) {
        int output;
        if (currentSteeringwheelState < maxRightSteeringWheelState){
            output = currentSteeringwheelState + 1;
        }
        else {
            output = maxRightSteeringWheelState;
        }
        return output;
    }

    public int addGas(int currentGaspedalState) {
        int output;
        if (currentGaspedalState < maxGaspedalState){
            output = currentGaspedalState + 2;
        }
        else {
            output = maxGaspedalState;
        }
        return output;
    }

    public int applyingBreak(int currentGaspedalState) {
        int output;
        if (currentGaspedalState > minGaspedalState){
            output = currentGaspedalState - 1;
        }
        else {
            output = minGaspedalState;
        }
        return output;
    }
}
