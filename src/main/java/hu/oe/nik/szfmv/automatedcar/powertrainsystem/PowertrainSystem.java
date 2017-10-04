package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

public class PowertrainSystem extends SystemComponent {

    // signal id table for PowertrainSystem

    // input signals
    private int gasPedal = 0;
    private int breakPedal = 0;
    private int steeringWheel = 0;
    private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.P;

    // Output signals
    // Only these are available trough getters
    private int x = 0;
    private int y = 0;
    private double wheelAngle = 0;

    public PowertrainSystem(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public void loop() {
        //TODO write this
    }

    @Override
    public void receiveSignal(Signal s) {
        SignalEnum switchVal = SignalEnum.values()[s.getId()];
        switch (switchVal) {
            // Handle demo signal
            case DEMO:
                x += (int) s.getData();
                break;
            case GASPEDAL: 
                this.gasPedal = (int)s.getData();
                break;
            case BREAKPEDAL: 
                this.breakPedal = (int)s.getData();
                break;
            case STEERINGWHEEL: 
                this.steeringWheel = (int)s.getData();
                break;
            case AUTOTRANSMISSION: 
                this.autoTransmission = (AutoTransmissionEnum)s.getData();
                break;
            default:
                // ignore other signals
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getWheelAngle() {
        return wheelAngle;
    }

    public int getGasPedal() {
        return gasPedal;
    }
}
