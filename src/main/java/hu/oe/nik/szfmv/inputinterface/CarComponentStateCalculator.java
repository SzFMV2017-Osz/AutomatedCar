package hu.oe.nik.szfmv.inputinterface;

public class CarComponentStateCalculator implements ICalculations{

    public int turnTheSteeringwheelLeft(int currentSteeringwheelState) {
        // TODO: @eky0151 => csökkenteni kell a steeringWheelState változót (maximum -100 -ig)

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
        // TODO: @eky0151 => növelni kell a steeringWheelState változót (maximum +100 -ig)

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
        // TODO: @hermanistvan => növelni a gaspedalState változót (maximum 100 -ig)

        int output;
        if (currentGaspedalState < maxGaspedalState){
            output = currentGaspedalState + 1;
        }
        else {
            output = maxGaspedalState;
        }
        return output;
    }

    public int applyingBreak(int currentGaspedalState) {
        // TODO: @hermanistvan => csökkenteni a gaspedalState változót (minimum 0 -ig)

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
