package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.visualisation.GameDisplayJPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    private static final int decreasingTimePeriod = 14;

    private AutoTransmissionEnum autotransmissionState;
    private int steeringWheelState;
    private int gaspedalState;
    private int breakpedalState;

    private ArrayList<Integer> pressedKeyCodes;
    private CarComponentStateCalculator componentStateCalculator;

    private Timer timer;
    private boolean isKeyPressingHappened;

    public UserInputHandler() {
        super();
        this.pressedKeyCodes = new ArrayList<>();
        this.componentStateCalculator = new CarComponentStateCalculator();
        this.timer = new Timer();

        // starting states
        this.autotransmissionState = AutoTransmissionEnum.N;
        this.steeringWheelState = componentStateCalculator.basicSteeringWheelState;
        this.gaspedalState = componentStateCalculator.minPedalState;
        this.breakpedalState = componentStateCalculator.minPedalState;

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
            this.autotransmissionState = AutoTransmissionEnum.P;
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_R)) {
            // set autotransmission to REVERSE mode
            this.autotransmissionState = AutoTransmissionEnum.R;
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_N)) {
            // set autotransmission to NEUTRAL mode
            this.autotransmissionState = AutoTransmissionEnum.N;
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_D)) {
            // set autotransmission to DRIVE mode
            this.autotransmissionState = AutoTransmissionEnum.D;
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

                if (!isKeyPressingHappened){

                    if (gaspedalState != componentStateCalculator.minPedalState){
                        gaspedalState--;
                    }

                    if (breakpedalState != componentStateCalculator.minPedalState){
                        breakpedalState--;
                    }

                    if (steeringWheelState > componentStateCalculator.basicSteeringWheelState){
                        steeringWheelState--;
                    }

                    if (steeringWheelState < componentStateCalculator.basicSteeringWheelState) {
                        steeringWheelState += 5;
                    }
                } else {
                    this.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(task, 100, decreasingTimePeriod);
    }

    private void sendNewAutotransmissionState(AutoTransmissionEnum newTransmissionState) {
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AUTOTRANSMISSION, newTransmissionState));
    }

    private void sendNewSteeringWheelState(int newSteeringWheelState) {
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.STEERINGWHEEL, newSteeringWheelState));
    }

    private void sendNewGaspedalState(int newGaspedalState) {
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.GASPEDAL, newGaspedalState));
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
        this.sendNewAutotransmissionState(this.autotransmissionState);
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
                this.breakpedalState = (int)s.getData();
                break;

            default:
        }
    }
}
