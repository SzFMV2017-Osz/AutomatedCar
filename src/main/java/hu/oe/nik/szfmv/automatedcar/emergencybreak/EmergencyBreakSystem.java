package hu.oe.nik.szfmv.automatedcar.emergencybreak;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;
import hu.oe.nik.szfmv.environment.detector.RadarSensor;

public class EmergencyBreakSystem extends SystemComponent {

    private static final Logger log = LogManager.getLogger(EmergencyBreakSystem.class);
    /**
     * it is 70 km/h
     */
    private static final double MAX_ALLOWED_SPEED = 19.5;
    
    /**
     * it is 2,5 m
     * a regular traffic lane's width is between 2,75 and 3 m 
     */
    private static final double MIN_ALLOWED_DISTANCE = 125;
    
    /**
     * it is 9 m/s^2
     */
    private static final double MAX_BREAK_SLOW = 9.0;
    
    private AutomatedCar car;
    private RadarSensor radar;
    private List<Distanceparameters> distanceParameters;

    /**
     * creates a new emergencybreaksystem
     * @param car that is handled by the break system
     * @param radar the radr on front of the car
     */
    public EmergencyBreakSystem(AutomatedCar car, RadarSensor radar) {
        this.distanceParameters = new ArrayList<>();
        this.car = car;
        this.radar = radar;

        VirtualFunctionBus.registerComponent(this);

    }

    /**
     * on every iteration
     */
    @Override
    public void loop() {
        if (car.getCurrentSpeed().abs() > MAX_ALLOWED_SPEED) {
            VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AEB_OFF, this));
            return;
        }

        detectPotentialCollision();

    }

    /**
     * check if objects in range are on a collision track
     */
    private void detectPotentialCollision() {
        List<IRadarSensor> inRange = radar.getRadarObjectsInRange();
        this.distanceParameters.clear();
        for (IRadarSensor iRadarSensor : inRange) {
            this.distanceParameters.add(Distanceparameters.calculateDistanceParameters(car, iRadarSensor));
        }
        
        /**
         * no objects in sight of the radar --> AEB reamins inactive
         */
        if (distanceParameters.isEmpty()){
            return;
        }

        /**
         * remove objects where min distance is in the past:
         *  that means the objects are moving away from each other
         */
        distanceParameters.removeIf(dp -> dp.minTime < 0);
        distanceParameters.sort(null);
        
        /**
         * if the min distance of the route is less than 0,75 m than it is a potential threat
         * 
         * it may avoid the oncoming traffic
         * and also wh may avoid calculating the dimensions
         * 
         */
        List<Distanceparameters> potentialThreats = distanceParameters.stream().filter(o -> o.minDistance < MIN_ALLOWED_DISTANCE).collect(Collectors.toList());
        
        /**
         * no potential threat -->  AEB remains inactive
         */
        if (potentialThreats.isEmpty()){
            return;
        }
        
        /*
         * the stopping time is calculated regarding the car's current speed and max break deceleration
         * an additional 5% is used just to be sure we break in time
         */
        double stopTime = (car.getCurrentSpeed().abs()/MAX_BREAK_SLOW) *1.05;
        
        /*
         * if there is an object whitin the break distance we activate the AEB
         */
        if (potentialThreats.stream().anyMatch(o -> o.getMinTime() <= stopTime)){ 
            VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AEB_INTERVENE,this));
        } else {
        /*
         * else we just warn
         */
            VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AEB_WARN,this));
        }

    }
    
    public boolean DetectPotentialCollision() {
        List<IRadarSensor> inRange = radar.getRadarObjectsInRange();
        this.distanceParameters.clear();
        for (IRadarSensor iRadarSensor : inRange) {
            this.distanceParameters.add(Distanceparameters.calculateDistanceParameters(car, iRadarSensor));
        }

        /**
         * no objects in sight of the radar --> AEB reamins inactive
         */
        if (distanceParameters.isEmpty()){
            return false;
        }

        /**
         * remove objects where min distance is in the past:
         *  that means the objects are moving away from each other
         */
        distanceParameters.removeIf(dp -> dp.minTime < 0);
        distanceParameters.sort(null);

        /**
         * if the min distance of the route is less than 0,75 m than it is a potential threat
         *
         * it may avoid the oncoming traffic
         * and also wh may avoid calculating the dimensions
         *
         */
        List<Distanceparameters> potentialThreats = distanceParameters.stream().filter(o -> o.minDistance < MIN_ALLOWED_DISTANCE).collect(Collectors.toList());

        /**
         * no potential threat -->  AEB remains inactive
         */
        if (potentialThreats.isEmpty()){
            return false;
        }

        /*
         * the stopping time is calculated regarding the car's current speed and max break deceleration
         * an additional 5% is used just to be sure we break in time
         */
        double stopTime = (car.getCurrentSpeed().abs()/MAX_BREAK_SLOW) *1.05;

        /*
         * if there is an object whitin the break distance we activate the AEB
         */
        if (potentialThreats.stream().anyMatch(o -> o.getMinTime() <= stopTime)){
            VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AEB_INTERVENE,this));
            return true;
        } else {
        /*
         * else we just warn
         */
            VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AEB_WARN,this));
        }
        return false;
    }


    @Override
    public void receiveSignal(Signal s) {
        // this does not need to handle signals

    }

}
