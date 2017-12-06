package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.environment.detector.WindscreenCamera;
import hu.oe.nik.szfmv.environment.util.DetectedRoad;
import hu.oe.nik.szfmv.environment.util.RoadType;

public class LaneKeeping implements ISystemComponent{

    private static boolean isLaneKeepingActive;
    private static boolean isLaneKeepingPossible;
    private int steeringWheelState;
    WindscreenCamera windscreenCamera;
    private double speed;

    public LaneKeeping(WindscreenCamera windscreenCamera) {
        this.windscreenCamera = windscreenCamera;
        VirtualFunctionBus.registerComponent(this);
    }

    public static void changeIsLaneKeepingTurnedOn() {
        if (!isLaneKeepingActive && isLaneKeepingPossible) {
            isLaneKeepingActive = true;
        }
        else {
            isLaneKeepingActive = false;
        }
    }

    public static boolean isIsLaneKeepingPossible() {
        return isLaneKeepingPossible;
    }

    public static boolean isIsLaneKeepingActive() {
        return isLaneKeepingActive;
    }

    public static void turnLaneKeepingOff() {
        isLaneKeepingActive = false;
    }

    @Override
    public void loop() {
        if (windscreenCamera.getDetectedRoadInfo().lineKeepingPossible) {
            isLaneKeepingPossible = true;
            if (isLaneKeepingActive) {
                calcSteeringWheelState();
                this.sendNewSteeringWheelState(this.steeringWheelState);
            }
        }
        else {
            isLaneKeepingPossible = false;
            isLaneKeepingActive = false;
        }
    }

    private void calcSteeringWheelState() {
        DetectedRoad road = windscreenCamera.getDetectedRoadInfo();
        if (road.roadDirection == RoadType.RIGHT) {
            if (speed < 30) {
                steeringWheelState = 8;
            }
            else {
                steeringWheelState = 5;
            }

        }
        else if (road.roadDirection == RoadType.LEFT){
            if (speed < 30) {
                steeringWheelState = -8;
            }
            else {
                steeringWheelState = -5;
            }
        }
        else {
            steeringWheelState = 0;
        }
    }


    @Override
    public void receiveSignal(Signal s) {
        SignalEnum signalType = s.getId();
        if (signalType == SignalEnum.SPEED) {
            speed = (double)s.getData();
        }
    }

    private void sendNewSteeringWheelState(int newSteeringWheelState) {
        VirtualFunctionBus.sendSignal(
                new Signal(
                        SignalEnum.STEERINGWHEEL,
                        newSteeringWheelState
                )
        );
    }


}
