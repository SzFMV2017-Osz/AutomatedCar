package hu.oe.nik.szfmv.automatedcar.adaptivetempomat;

import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class TempomatStatus {

    private boolean isOn;
    private double targetSpeed;
    private int followDistanceIndex;

    public static final double MIN_TARGET_SPEED = 30;
    public static final double MAX_TARGET_SPEED = 160;
    public static final double[] FOLLOW_DISTANCES = {0.8, 1.0, 1.2, 1.4};

    public TempomatStatus() {
    }


    public boolean isOn() {
        return isOn;
    }

    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public double getFollowDistance() {
        return TempomatStatus.FOLLOW_DISTANCES[followDistanceIndex];
    }

    public double nextFollowDistance() {
        this.followDistanceIndex = this.followDistanceIndex % TempomatStatus.FOLLOW_DISTANCES.length;
        return getFollowDistance();
    }

    public double getTargetSpeed() {
        return this.targetSpeed;
    }

    public double increaseTargetSpeed() {
        this.targetSpeed = Math.min(this.targetSpeed + 5, TempomatStatus.MAX_TARGET_SPEED);
        return getTargetSpeed();
    }

    public double decreaseTargetSpeed() {
        this.targetSpeed = Math.max(this.targetSpeed - 5, TempomatStatus.MIN_TARGET_SPEED);
        return getTargetSpeed();
    }

    public void turnOn(double currentSpeed) {
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AUTOTRANSMISSION, AutoTransmissionEnum.D));
        this.isOn = true;

        // Init tempomat
        if (this.getTargetSpeed() < TempomatStatus.MIN_TARGET_SPEED) {
            if (currentSpeed < TempomatStatus.MIN_TARGET_SPEED) {
                this.setTargetSpeed(TempomatStatus.MIN_TARGET_SPEED);
            } else {
                this.setTargetSpeed(currentSpeed);
            }
        }
        System.out.println(this.targetSpeed);
    }

    public void turnOff() {
        this.isOn = false;
    }
}
