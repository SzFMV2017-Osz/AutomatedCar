package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    private final int GearShiftStateID = 0;
    private String gearShiftState;

    private ArrayList<Integer> pressedKeyCodes;

    private UserInputHandler() {
        super();
        this.pressedKeyCodes = new ArrayList<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent userKeyPress) {
        if (!this.pressedKeyCodes.contains(userKeyPress.getKeyCode())){
            pressedKeyCodes.add(userKeyPress.getKeyCode());
        }
        if (userKeyPress.getKeyCode() == KeyEvent.VK_D){
            // set the GearShift to drive mode
            this.gearShiftState = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {
        pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));
    }

    @Override
    public void loop() {
        // ezt hívja meg tőlünk a bus
        // itt kéne feldobni a buszra a user által nyomott dolgokat
        VirtualFunctionBus.sendSignal(new Signal(this.GearShiftStateID, this.gearShiftState));
    }

    @Override
    public void receiveSignal(Signal s) {
        switch (s.getId()){
            case GearShiftStateID:
                this.gearShiftState = (String)s.getData();
                break;

            default:
        }

    }
}
