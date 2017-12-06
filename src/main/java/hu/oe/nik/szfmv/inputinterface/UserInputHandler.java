package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.LaneKeeping;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.visualisation.GameDisplayJPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Class that handles keystrokes from the user</h1>
 * <p></p>extends <code>SystemComponent</code> to write signals to the <code>VirtualFunctionBus</code>
 * implements <i>KeyListener</i> java interface
 * </p>
 *
 * @author danijanos
 */
public final class UserInputHandler extends SystemComponent implements KeyListener {

    private static final Logger logger = LogManager.getLogger(UserInputHandler.class);

    // constant declaration for timer tick:
    private static final int DECREASING_TIME_PERIOD = 15;

    private int steeringWheelState;
    private int gaspedalState;
    private int breakpedalState;

    private Timer timer;
    private ArrayList<Integer> pressedKeyCodes;
    private CarComponentStateCalculator componentStateCalculator;

    public UserInputHandler() {
        super();
        this.timer = new Timer();
        this.pressedKeyCodes = new ArrayList<>();
        this.componentStateCalculator = new CarComponentStateCalculator();

        // starting states
        this.sendNewAutotransmissionState(AutoTransmissionEnum.N);
        this.steeringWheelState = componentStateCalculator.BASIC_STEERING_WHEEL_STATE;
        this.gaspedalState = componentStateCalculator.MIN_PEDAL_STATE;
        this.breakpedalState = componentStateCalculator.MIN_PEDAL_STATE;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent userKeyPress) {

        // collecting simultaneous key presses
        if (!this.pressedKeyCodes.contains(userKeyPress.getKeyCode())) {
            pressedKeyCodes.add(userKeyPress.getKeyCode());
        }

        /*----------------------
         *  Transmissions
         *----------------------*/

        if (this.pressedKeyCodes.contains(KeyEvent.VK_P)) {
            // set auto transmission to PARK mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.P);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_R)) {
            // set auto transmission to REVERSE mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.R);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_N)) {
            // set auto transmission to NEUTRAL mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.N);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_D)) {
            // set auto transmission to DRIVE mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.D);
        }

        /*----------------------
        *   Steering
        *----------------------*/

        if (this.pressedKeyCodes.contains(KeyEvent.VK_LEFT)) {
            // turn the car left
            this.steeringWheelState = this.componentStateCalculator.turnTheSteeringwheelLeft(this.steeringWheelState);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_RIGHT)) {
            // turn the car right
            this.steeringWheelState = this.componentStateCalculator.turnTheSteeringwheelRight(this.steeringWheelState);
        }

        /*----------------------
        *   Gas & Break
        *----------------------*/

        if (this.pressedKeyCodes.contains(KeyEvent.VK_UP)) {
            // add gas
            this.gaspedalState = this.componentStateCalculator.addGas(this.gaspedalState);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_DOWN)) {
            // applying break
            this.breakpedalState = this.componentStateCalculator.applyingBreak(this.breakpedalState);
        }

        /*----------------------
        *   Sensor visualization toggles
        *----------------------*/

        if (this.pressedKeyCodes.contains(KeyEvent.VK_0) || this.pressedKeyCodes.contains(KeyEvent.VK_NUMPAD0)) {
            GameDisplayJPanel.changeSensorDebugMode();
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_1) || this.pressedKeyCodes.contains(KeyEvent.VK_NUMPAD1)) {
            GameDisplayJPanel.changeCameraSensorDebugMode();
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_2) || this.pressedKeyCodes.contains(KeyEvent.VK_NUMPAD2)) {
            GameDisplayJPanel.changeRadarSensorDebugMode();
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_3) || this.pressedKeyCodes.contains(KeyEvent.VK_NUMPAD3)) {
            GameDisplayJPanel.changeSensorSensorDebugMode();
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_L)) {
            LaneKeeping.changeIsLaneKeepingTurnedOn();
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_LEFT) || this.pressedKeyCodes.contains(KeyEvent.VK_RIGHT)) {
            LaneKeeping.turnLaneKeepingOff();
        }
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {

        this.pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));

        // non of the steering control buttons are pressed
        if (!this.pressedKeyCodes.contains(KeyEvent.VK_LEFT) && !this.pressedKeyCodes.contains(KeyEvent.VK_RIGHT)) {
            if (this.steeringWheelState != componentStateCalculator.BASE_STATE) {
                this.setSteeringWheelToBasicState();
            }
        }

        // the gas control button is released
        if (!this.pressedKeyCodes.contains(KeyEvent.VK_UP)) {
            if (this.gaspedalState > componentStateCalculator.BASE_STATE) {
                this.setGasPedalToBasicState();
            }
        }

        // the break control button is released
        if (!this.pressedKeyCodes.contains(KeyEvent.VK_DOWN)) {
            if (this.breakpedalState > componentStateCalculator.BASE_STATE) {
                this.setBreakPedalToBasicState();
            }
        }
    }

    private void setGasPedalToBasicState() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // while a gas key pressing is not happened and the gas pedal doesn't reached it's base state
                if (!pressedKeyCodes.contains(KeyEvent.VK_UP) && gaspedalState > componentStateCalculator.BASE_STATE) {
                    gaspedalState--;
                } else {
                    this.cancel();
                }
            }
        };
        // A timer which runs the timer task at a fixed rate
        timer.scheduleAtFixedRate(task, 100, DECREASING_TIME_PERIOD);
    }

    private void setBreakPedalToBasicState() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // while the break key pressing is not happened and the break pedal doesn't reached it's base state
                if (!pressedKeyCodes.contains(KeyEvent.VK_DOWN) && breakpedalState > componentStateCalculator.BASE_STATE) {
                    breakpedalState--;
                } else {
                    this.cancel();
                }
            }
        };
        // A timer which runs the timer task at a fixed rate
        timer.scheduleAtFixedRate(task, 100, DECREASING_TIME_PERIOD);
    }

    private void setSteeringWheelToBasicState() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // while there is no steering control buttons press the task runs
                if (!pressedKeyCodes.contains(KeyEvent.VK_LEFT) && !pressedKeyCodes.contains(KeyEvent.VK_RIGHT)) {

                    // the steering wheel reached its base state
                    if (steeringWheelState == componentStateCalculator.BASE_STATE) {
                        this.cancel();
                    }

                    // steering wheel was turned right
                    if (steeringWheelState > componentStateCalculator.BASE_STATE) {
                        steeringWheelState--;
                    }

                    // steering wheel was turned left
                    if (steeringWheelState < componentStateCalculator.BASE_STATE) {
                        steeringWheelState++;
                    }
                } else {
                    this.cancel();
                }

            }
        };

        // A timer which runs the timer task at a fixed rate
        timer.scheduleAtFixedRate(task, 100, DECREASING_TIME_PERIOD);
    }

    private void sendNewAutotransmissionState(AutoTransmissionEnum newTransmissionState) {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.AUTOTRANSMISSION,
                        newTransmissionState
                )
        );

        if (logger.isDebugEnabled()) {
            logger.debug("A new transmission state is sent to the VirtualBus by the user from the keyboard");
            logger.info("sendNewAutotransmissionState invoked with param: " + newTransmissionState + "which is a " + newTransmissionState.getClass());
        }
    }

    private void sendNewSteeringWheelState(int newSteeringWheelState) {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.STEERINGWHEEL,
                        newSteeringWheelState
                )
        );
    }

    private void sendNewGaspedalState(int newGaspedalState) {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.GASPEDAL,
                        newGaspedalState
                )
        );
    }

    private void sendNewBreakpedalState(int newBreakpedalState) {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.BREAKPEDAL,
                        newBreakpedalState
                )
        );
    }

    @Override
    public void loop() {
        this.sendNewSteeringWheelState(this.steeringWheelState);
        this.sendNewGaspedalState(this.gaspedalState);
        this.sendNewBreakpedalState(this.breakpedalState);
    }

    @Override
    public void receiveSignal(Signal s) {
        switch (s.getId()) {
            case STEERINGWHEEL:
                this.steeringWheelState = (int) s.getData();
                break;
            case GASPEDAL:
                this.gaspedalState = (int) s.getData();
                break;
            case BREAKPEDAL:
                this.breakpedalState = (int) s.getData();
                break;

            default:
        }
    }
}
