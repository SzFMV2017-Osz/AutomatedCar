package hu.oe.nik.szfmv.environment.detector;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.RoadSign;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SignDetection extends SystemComponent {

    private WindscreenCamera camera;
    private AutomatedCar car;
    private List<WorldObject> worldObjects;
    private RoadSign latestRoadSign;
    private WorldObject lastElement;

    public SignDetection(WindscreenCamera camera, AutomatedCar car, List<WorldObject> worldObjects) {
        this.camera = camera;
        this.car = car;
        this.worldObjects = worldObjects;
    }

    public void sendSignal(){

    }
    public void getLatestRoadsign(){
        LinkedHashMap<WorldObject,Double> linkedHashMap;

        linkedHashMap = this.camera.getFilteredRoadSignObjectsHashMap();
        while (linkedHashMap.keySet().iterator().hasNext()) {
            lastElement = linkedHashMap.keySet().iterator().next();
        }
    }
    @Override
    public void loop() {
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.LASTROADSIGN,lastElement));
    }

    @Override
    public void receiveSignal(Signal s) {

    }
}

