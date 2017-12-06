package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.environment.detector.WindscreenCamera;
import hu.oe.nik.szfmv.environment.util.DetectedRoad;
import hu.oe.nik.szfmv.environment.util.RoadType;

public class LaneKeeping implements ISystemComponent{

    private static boolean isLaneKeepingTurnedOn;
    private static boolean isLaneKeepingPossible;
    private int steeringWheelState = 40;
    WindscreenCamera windscreenCamera;

    public LaneKeeping(WindscreenCamera windscreenCamera) {
        this.windscreenCamera = windscreenCamera;
        VirtualFunctionBus.registerComponent(this);
    }

    public static void changeIsLaneKeepingTurnedOn() {
        if (isLaneKeepingTurnedOn) {
            isLaneKeepingTurnedOn = false;
        }
        else {
            isLaneKeepingTurnedOn = true;
        }
    }

    public static boolean isIsLaneKeepingPossible() {
        return isLaneKeepingPossible;
    }

    public static boolean isIsLaneKeepingTurnedOn() {
        return isLaneKeepingTurnedOn;
    }

    public static void turnLaneKeepingOff() {
        isLaneKeepingTurnedOn = false;
    }

    @Override
    public void loop() {
        if (windscreenCamera.getDetectedRoadInfo().lineKeepingPossible) {
            isLaneKeepingPossible = true;
            if (isLaneKeepingTurnedOn) {
                calcSteeringWheelState();
                this.sendNewSteeringWheelState(this.steeringWheelState);
            }
        }
        else {
            isLaneKeepingPossible = false;
        }
    }

    private void calcSteeringWheelState() {
        DetectedRoad road = windscreenCamera.getDetectedRoadInfo();
        if (road.roadDirection == RoadType.RIGHT) {
            // LANE KEEPING MEGVALÓSÍTÁS....

        }
        else if (road.roadDirection == RoadType.LEFT){
            // LANE KEEPING MEGVALÓSÍTÁS....
        }
    }


    @Override
    public void receiveSignal(Signal s) {

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
