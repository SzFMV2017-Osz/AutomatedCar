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
        return pushPedal(currentGaspedalState);
    }

    public int applyingBreak(int currentBreakpedalState) {
        return pushPedal(currentBreakpedalState);
    }

    private int pushPedal(int currentpedalState) {
        int output;
        if (currentpedalState < maxPedalState){
            output = currentpedalState + 1;
        }
        else {
            output = maxPedalState;
        }
        return output;
    }
}
