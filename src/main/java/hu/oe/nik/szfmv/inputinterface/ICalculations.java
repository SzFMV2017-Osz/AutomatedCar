package hu.oe.nik.szfmv.inputinterface;

public interface ICalculations {

    // constant threshold declarations for gaspedal states:
    int MIN_PEDAL_STATE = 0;
    int MAX_PEDAL_STATE = 100;

    // constant threshold declarations for steeringwheel states :
    int MAX_LEFT_STEERING_WHEEL_STATE = -100;
    int MAX_RIGHT_STEERING_WHEEL_STATE = 100;
    int BASIC_STEERING_WHEEL_STATE = 0;

    // state change calculation method signatures:
    int turnTheSteeringwheelLeft(int currentSteeringwheelState);

    int turnTheSteeringwheelRight(int currentSteeringwheelState);

    int addGas(int currentGaspedalState);

    int applyingBreak(int currentGaspedalState);
}
