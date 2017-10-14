package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    public static final int minGaspedalState = 0;
    public static final int maxGaspedalState = 100;
    public static final int minSteeringWheelState = -100;
    public static final int maxSteeringWheelState = 100;

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
        this.steeringWheelState = 0;
        this.gaspedalState = 0;
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

        if (userKeyPress.getKeyCode() == KeyEvent.VK_P){
            // set autotransmission to PARK mode
            this.autotransmissionState = "P";
            this.sendNewAutotransmissionState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_R){
            // set autotransmission to REVERSE mode
            this.autotransmissionState = "R";
            this.sendNewAutotransmissionState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_N){
            // set autotransmission to NEUTRAL mode
            this.autotransmissionState = "N";
            this.sendNewAutotransmissionState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_D){
            // set autotransmission to drive mode
            this.autotransmissionState = "D";
            this.sendNewAutotransmissionState();
        }

        if(userKeyPress.getKeyCode() == KeyEvent.VK_LEFT) {
            // TODO: @eky0151 => csökkenteni kell a steeringWheelState változót (maximum -100 -ig)
            this.steeringWheelState = this.steeringWheelState - 1;
            this.sendNewSteeringWheelState();
        }

        if(userKeyPress.getKeyCode() == KeyEvent.VK_RIGHT) {
            // TODO: @eky0151 => növelni a steeringWheelState változót (maximum +100 -ig)
            this.steeringWheelState = this.steeringWheelState + 1;
            this.sendNewSteeringWheelState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_UP){
            // TODO: @hermanistvan => növelni a gaspedalState változót (maximum 100 -ig)
            this.gaspedalState = this.gaspedalState + 1;
            this.sendNewGaspedalState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_DOWN){
            // TODO: @hermanistvan => csökkenteni a gaspedalState változót (minimum 0 -ig)
            this.gaspedalState = this.gaspedalState - 1;
            this.sendNewGaspedalState();
        }
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {
        pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));
    }

    private void sendNewAutotransmissionState() {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.AUTOTRANSMISSION,
                        this.autotransmissionState
                )
        );
    }

    private void sendNewSteeringWheelState() {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.STEERINGWHEEL,
                        this.steeringWheelState
                )
        );
    }

    private void sendNewGaspedalState() {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.GASPEDAL,
                        this.gaspedalState
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
