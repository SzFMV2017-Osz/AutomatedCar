package hu.oe.nik.szfmv.inputinterface;

/**
 * <h1>An Interface for car component state calculations</h1>
 * <p>with constants</p>
 *
 * @author danijanos
 */
public interface ICalculations {

    int BASE_STATE = 0;
    int MAX_STATE = 100;

    // constant threshold declarations for gaspedal states:
    int MIN_PEDAL_STATE = BASE_STATE;
    int MAX_PEDAL_STATE = MAX_STATE;

    // constant threshold declarations for steeringwheel states:
    int MAX_RIGHT_STEERING_WHEEL_STATE = MAX_STATE;
    int MAX_LEFT_STEERING_WHEEL_STATE = -MAX_STATE;
    int BASIC_STEERING_WHEEL_STATE = BASE_STATE;

    // contractual agreements about state change calculation method signatures:
    int turnTheSteeringwheelLeft(int currentSteeringwheelState);

    int turnTheSteeringwheelRight(int currentSteeringwheelState);

    int addGas(int currentGaspedalState);

    int applyingBreak(int currentGaspedalState);
}
