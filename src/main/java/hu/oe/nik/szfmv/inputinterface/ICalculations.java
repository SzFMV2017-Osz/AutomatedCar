package hu.oe.nik.szfmv.inputinterface;

public interface ICalculations {

    // constant threshold declarations for gaspedal states:
    int minGaspedalState = 0;
    int maxGaspedalState = 100;

    // constant threshold declarations for steeringwheel states :
    int maxLeftSteeringWheelState = -100;
    int maxRightSteeringWheelState = 100;
    int basicSteeringWheelState = 0;

    // state change calculation method signatures:
    int turnTheSteeringwheelLeft(int currentSteeringwheelState);
    int turnTheSteeringwheelRight(int currentSteeringwheelState);
    int addGas(int currentGaspedalState);
    int applyingBreak(int currentGaspedalState);
}
