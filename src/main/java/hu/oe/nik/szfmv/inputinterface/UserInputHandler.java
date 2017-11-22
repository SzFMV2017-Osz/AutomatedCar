package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private static final int DECREASING_TIME_PERIOD = 14;

    private AutoTransmissionEnum autotransmissionState;
    private boolean isKeyPressingHappened;
    private int steeringWheelState;
    private int gaspedalState;
    private int breakpedalState;

    private ArrayList<Integer> pressedKeyCodes;
    private CarComponentStateCalculator componentStateCalculator;
    private Timer timer;

    public UserInputHandler() {
        super();
        this.pressedKeyCodes = new ArrayList<>();
        this.componentStateCalculator = new CarComponentStateCalculator();
        this.timer = new Timer();

        // starting states
        this.sendNewAutotransmissionState(AutoTransmissionEnum.N);
        this.steeringWheelState = componentStateCalculator.BASIC_STEERING_WHEEL_STATE;
        this.gaspedalState = componentStateCalculator.MIN_PEDAL_STATE;
        this.breakpedalState = componentStateCalculator.MIN_PEDAL_STATE;

        this.isKeyPressingHappened = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent userKeyPress) {

        this.isKeyPressingHappened = true;

        // collecting simultaneous key presses
        if (!this.pressedKeyCodes.contains(userKeyPress.getKeyCode())) {
            pressedKeyCodes.add(userKeyPress.getKeyCode());
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_P)) {
            // set autotransmission to PARK mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.P);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_R)) {
            // set autotransmission to REVERSE mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.R);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_N)) {
            // set autotransmission to NEUTRAL mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.N);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_D)) {
            // set autotransmission to DRIVE mode
            this.sendNewAutotransmissionState(AutoTransmissionEnum.D);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_LEFT)) {
            // turn the car left
            this.steeringWheelState = this.componentStateCalculator.turnTheSteeringwheelLeft(this.steeringWheelState);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_RIGHT)) {
            // turn the car right
            this.steeringWheelState = this.componentStateCalculator.turnTheSteeringwheelRight(this.steeringWheelState);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_UP)) {
            // add gas
            this.gaspedalState = this.componentStateCalculator.addGas(this.gaspedalState);
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_DOWN)) {
            // applying break
            this.breakpedalState = this.componentStateCalculator.applyingBreak(this.breakpedalState);
        }
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {

        this.pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));

        // amíg nincs input, minden visszaáll alaphelyzetbe
        if (this.pressedKeyCodes.isEmpty()) {
            this.isKeyPressingHappened = false;
            this.decreaseAllComponentStateToBase();
        }
    }

    private void decreaseAllComponentStateToBase() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (!isKeyPressingHappened) {

                    if (gaspedalState != componentStateCalculator.MIN_PEDAL_STATE) {
                        gaspedalState--;
                    }

                    if (breakpedalState != componentStateCalculator.MIN_PEDAL_STATE) {
                        breakpedalState--;
                    }

                    if (steeringWheelState > componentStateCalculator.BASIC_STEERING_WHEEL_STATE) {
                        steeringWheelState--;
                    }

                    if (steeringWheelState < componentStateCalculator.BASIC_STEERING_WHEEL_STATE) {
                        steeringWheelState++;
                    }
                } else {
                    this.cancel();
                }
            }
        };

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
            case AUTOTRANSMISSION:
                this.autotransmissionState = (AutoTransmissionEnum) s.getData();
                break;
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
