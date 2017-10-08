package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    private final int GearShiftID_forTesting = 0;
    private String gearShift;
    private ArrayList<KeyEvent> pressedKeys;

    public UserInputHandler() {
        super();
        this.pressedKeys = new ArrayList<KeyEvent>();
        this.gearShift = "N";
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D){
            // set the GearShift to drive mode
            this.gearShift = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void loop() {

    }

    @Override
    public void receiveSignal(Signal s) {
        switch (s.getId()){
            case GearShiftID_forTesting :
                this.gearShift = (String)s.getData();
                break;

            default:
        }

    }
}
