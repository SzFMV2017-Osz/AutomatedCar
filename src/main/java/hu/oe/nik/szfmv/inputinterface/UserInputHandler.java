package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.util.ArrayList;

public final class UserInputHandler extends SystemComponent implements KeyListener{

    private String gearShiftState;

    private final long TIME_TO_SEND_SIGNAL = 100;

    private long leftKeyLastMilisec,  rightKeyLastMilisec;


    private ArrayList<Integer> pressedKeyCodes;

    public UserInputHandler() {
        super();
        this.pressedKeyCodes = new ArrayList<>();
        this.gearShiftState = "N"; // starting state
        this.printCurrentGearShiftState();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent userKeyPress) {

        /*

        majd a szimultán billentyűnyomás kezeléshez kell!

        if (!this.pressedKeyCodes.contains(userKeyPress.getKeyCode())){
            pressedKeyCodes.add(userKeyPress.getKeyCode());
        }

        */

        //ide kéne minden eszközt refaktorálni, külön kezelését megoldani:
        // valahogy pl így:
        // UserGearShifter.ShiftGear(userKeyPress);
        // visszadobni , ha nem rá tartozik...

        if (userKeyPress.getKeyCode() == KeyEvent.VK_P){
            // set the GearShift to PARK mode
            this.gearShiftState = "P";
            this.setNewGearShiftState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_R){
            // set the GearShift to REVERSE mode
            this.gearShiftState = "R";
            this.setNewGearShiftState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_N){
            // set the GearShift to NEUTRAL mode
            this.gearShiftState = "N";
            this.setNewGearShiftState();
        }

        if (userKeyPress.getKeyCode() == KeyEvent.VK_D){
            // set the GearShift to drive mode
            this.gearShiftState = "D";
            this.setNewGearShiftState();
        }

        if(userKeyPress.getKeyCode() == KeyEvent.VK_LEFT) {
            long temp = System.currentTimeMillis();
            if(leftKeyLastMilisec < temp - TIME_TO_SEND_SIGNAL ) {
                leftKeyLastMilisec = temp;
                setSteeringWheelState(SteeringWheelDirections.LEFT);
            }

        }

        if(userKeyPress.getKeyCode() == KeyEvent.VK_RIGHT) {
            long temp = System.currentTimeMillis();
            if(rightKeyLastMilisec < temp - TIME_TO_SEND_SIGNAL ) {
                rightKeyLastMilisec = temp;
                setSteeringWheelState(SteeringWheelDirections.RIGHT);
            }
        }
    }

    private void setSteeringWheelState(SteeringWheelDirections stw)  {
        VirtualFunctionBus.sendSignal(
                new Signal(CarComponent.STEERINGWHEEL.getCarComponentID(),  stw.getSteeringWheelId() == SteeringWheelDirections.LEFT.getSteeringWheelId() ?
                        SteeringWheelDirections.LEFT.getSteeringWheelId() :
                        SteeringWheelDirections.RIGHT.getSteeringWheelId())
        )  ;

        System.out.println("Signal sended for " + (stw.getSteeringWheelId() == 1 ? "LEFT" : "RIGHT"));
    }

    private void setNewGearShiftState() {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        CarComponent.GEARSHIFT.getCarComponentID(),
                        this.gearShiftState
                )
        );
    }

    private void printCurrentGearShiftState() {
        System.out.println("The gearshift state is: " + this.gearShiftState);
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {
        // pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));
    }

    @Override
    public void loop() {
        // ezt hívja meg tőlünk a bus
    }

    // itt kell lekérdeznünk a busztól minden signal aktuális állapotát
    @Override
    public void receiveSignal(Signal s) {
        switch (s.getId()){
            case 104: // kiatlálom még hogy kéne, mert valamiért CarComponent.GEARSHIFT.getCarComponentID() -t nem eszi
                this.gearShiftState = (String)s.getData();

                // csak amig nincs műszerfal
                this.printCurrentGearShiftState();

                break;

            default:
        }
    }
}
