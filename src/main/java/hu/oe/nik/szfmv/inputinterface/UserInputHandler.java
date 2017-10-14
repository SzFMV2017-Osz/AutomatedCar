package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    private static final int minGaspedalState = 0;
    private static final int maxGaspedalState = 100;

    private static final int maxLeftSteeringWheelState = -100;
    private static final int maxRightSteeringWheelState = 100;
    private static final int basicSteeringWheelState = 0;

    private String autotransmissionState;
    private int steeringWheelState;
    private int gaspedalState;
    // private int breakpedalState;

    private ArrayList<Integer> pressedKeyCodes;

    public UserInputHandler() {
        super();
        this.pressedKeyCodes = new ArrayList<>();

        // starting states
        this.autotransmissionState = "N";
        this.steeringWheelState = basicSteeringWheelState;
        this.gaspedalState = minGaspedalState;
        // this.breakpedalState = 0;
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

        switch (userKeyPress.getKeyCode()){
            case KeyEvent.VK_P:
                // set autotransmission to PARK mode
                this.sendNewAutotransmissionState("P");
                break;
            case KeyEvent.VK_R:
                // set autotransmission to REVERSE mode
                this.sendNewAutotransmissionState("R");
                break;
            case KeyEvent.VK_N:
                // set autotransmission to NEUTRAL mode
                this.sendNewAutotransmissionState("N");
                break;
            case KeyEvent.VK_D:
                // set autotransmission to drive mode
                this.sendNewAutotransmissionState("D");
                break;
            case KeyEvent.VK_LEFT:
                // turn the car left
                this.sendNewSteeringWheelState(this.turningTheSteeringwheelLeft());
                break;
            case KeyEvent.VK_RIGHT:
                // turn the car right
                this.sendNewSteeringWheelState(this.turningTheSteeringwheelRight());
                break;
            case KeyEvent.VK_UP:
                this.sendNewGaspedalState(this.addGas());
                break;
            case KeyEvent.VK_DOWN:
                this.sendNewGaspedalState(this.applyingBreak());
                break;
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {
        pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));
    }

    private int turningTheSteeringwheelLeft() {
        // TODO: @eky0151 => csökkenteni kell a steeringWheelState változót (maximum -100 -ig)

        int output;
        if (this.steeringWheelState > maxLeftSteeringWheelState){
            output = this.steeringWheelState - 1;
        }
        else {
            output = maxLeftSteeringWheelState;
        }
        return output;
    }

    private int turningTheSteeringwheelRight() {
        // TODO: @eky0151 => növelni kell a steeringWheelState változót (maximum +100 -ig)

        int output;
        if (this.steeringWheelState < maxRightSteeringWheelState){
            output = this.steeringWheelState + 1;
        }
        else {
            output = maxRightSteeringWheelState;
        }
        return output;
    }

    private int addGas() {
        // TODO: @hermanistvan => növelni a gaspedalState változót (maximum 100 -ig)
        int output;
        if (this.gaspedalState < maxGaspedalState){
            output = this.gaspedalState + 1;
        }
        else {
            output = maxGaspedalState;
        }
        return output;
    }

    private int applyingBreak() {
        // TODO: @hermanistvan => csökkenteni a gaspedalState változót (minimum 0 -ig)
        int output;
        if (this.gaspedalState > minGaspedalState){
            output = this.gaspedalState - 1;
        }
        else {
            output = minGaspedalState;
        }
        return output;
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

    @Override
    public void loop() {
        // ezt hívja meg tőlünk folyamatosan a bus
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
