package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    private String autotransmissionState;
    private int steeringWheelState;
    private int gaspedalState;

    private ArrayList<Integer> pressedKeyCodes;
    private CarComponentStateCalculator componentStateCalculator;
    private Timer timer;

    public UserInputHandler() {
        super();
        this.pressedKeyCodes = new ArrayList<>();
        this.componentStateCalculator = new CarComponentStateCalculator();
        this.timer = new Timer();

        // starting states
        this.autotransmissionState = "N";
        this.steeringWheelState = componentStateCalculator.basicSteeringWheelState;
        this.gaspedalState = componentStateCalculator.minGaspedalState;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent userKeyPress) {

        // collecting simultaneous key presses
        if (!this.pressedKeyCodes.contains(userKeyPress.getKeyCode())){
            pressedKeyCodes.add(userKeyPress.getKeyCode());
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_P)){
            // set autotransmission to PARK mode
            this.autotransmissionState = "P";
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_R)) {
            // set autotransmission to REVERSE mode
            this.autotransmissionState = "R";
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_N)) {
            // set autotransmission to NEUTRAL mode
            this.autotransmissionState = "N";
        }

        if (this.pressedKeyCodes.contains(KeyEvent.VK_D)) {
            // set autotransmission to DRIVE mode
            this.autotransmissionState = "D";
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
            this.gaspedalState = this.componentStateCalculator.applyingBreak(this.gaspedalState);
        }
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {

        this.pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));

        // amíg nincs input, minden visszaáll alaphelyzetbe
        if (this.pressedKeyCodes.isEmpty()){
            this.decreaseGaspadalStateToBase();
        }
    }

    private void decreaseGaspadalStateToBase() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (gaspedalState != 0){
                    gaspedalState--;
                }
                else {
                    this.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(task,0, 300);
    }

    private void sendNewAutotransmissionState(String newTransmissionState) {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.AUTOTRANSMISSION,
                        newTransmissionState
                )
        );
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

    private void printCurrentCarComponentStates() {
        System.out.println(
                "The autotransmission state is: " + this.autotransmissionState +
                "\nThe steeringwheel state is: " + this.steeringWheelState +
                "\nThe gaspedal state is: " + this.gaspedalState
        );
    }

    // ezt hívja meg tőlünk folyamatosan a bus
    @Override
    public void loop() {
        this.sendNewAutotransmissionState(this.autotransmissionState);
        this.sendNewSteeringWheelState(this.steeringWheelState);
        this.sendNewGaspedalState(this.gaspedalState);
        this.printCurrentCarComponentStates();
    }

    // a busz itt dobálja minden Signal új állapotát, tehát innen tudjuk frissíteni a mi állapotváltozóinkat is
    @Override
    public void receiveSignal(Signal s) {
        switch (s.getId()){
            case AUTOTRANSMISSION:
                this.autotransmissionState = (String)s.getData();
                break;
            case STEERINGWHEEL:
                this.steeringWheelState = (int)s.getData();
                break;
            case GASPEDAL:
                this.gaspedalState = (int)s.getData();
                break;

            default:
        }
    }
}
