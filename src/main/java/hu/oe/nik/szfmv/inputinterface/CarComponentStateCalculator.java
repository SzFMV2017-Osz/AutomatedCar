package hu.oe.nik.szfmv.inputinterface;

/**
 * <h1>Class for car component state calculations</h1>
 *
 * @author danijanos
 */
public class CarComponentStateCalculator implements ICalculations {

    public int turnTheSteeringwheelLeft(int currentSteeringwheelState) {
        int output;
        if (currentSteeringwheelState > MAX_LEFT_STEERING_WHEEL_STATE) {
            output = currentSteeringwheelState - 1;
        } else {
            output = MAX_LEFT_STEERING_WHEEL_STATE;
        }
        return output;
    }

    public int turnTheSteeringwheelRight(int currentSteeringwheelState) {
        int output;
        if (currentSteeringwheelState < MAX_RIGHT_STEERING_WHEEL_STATE) {
            output = currentSteeringwheelState + 1;
        } else {
            output = MAX_RIGHT_STEERING_WHEEL_STATE;
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
        if (currentpedalState < MAX_PEDAL_STATE){
            output = currentpedalState + 1;

        }else {
            output = MAX_PEDAL_STATE;
        }
        return output;
    }
}
