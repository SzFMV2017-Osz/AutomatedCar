package hu.oe.nik.szfmv.automatedcar.adaptivetempomat;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class AdaptiveTempomat extends SystemComponent {

    private TempomatStatus status;
    private double currentSpeed;

    public AdaptiveTempomat() {
//        VirtualFunctionBus.registerComponent(this);
        status = new TempomatStatus();
        this.status.turnOff();
    }

    @Override
    public void loop() {
        if (status.isOn()) {
            VirtualFunctionBus.sendSignal(new Signal(SignalEnum.GASPEDAL, 100));
        }
//        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.TEMPOMAT, status));
    }

    @Override
    public void receiveSignal(Signal s) {

        if (s.getId() == SignalEnum.TEMPOMATCONTROL) {
            TempomatControlEnum tempomat = (TempomatControlEnum) s.getData();
            if (tempomat != null) {
                switch (tempomat) {
                    case TURN_ON:
                        status.turnOn();
                        break;
                    case TURN_OFF:
                        status.turnOff();
                        break;
                    case TOGGLE:
                        if (status.isOn()) {
                            System.out.println("Turning tempomat off...");
                            status.turnOff();
                        } else {
                            System.out.println("Turning tempomat on...");
                            status.turnOn();
                        }
                        break;
                }
            }
        }
        if (status.isOn()) {
            switch (s.getId()) {
                case SPEED:
                    this.currentSpeed = (double) s.getData();
                    break;
                case TEMPOMATCONTROL:
                    switch ((TempomatControlEnum) s.getData()) {
                        case INCREASE_TARGET_SPEED:
                            status.increaseTargetSpeed();
                            break;
                        case DECREASE_TARGET_SPEED:
                            status.decreaseTargetSpeed();
                            break;
                    }
                default:
            }
        }
    }
}
