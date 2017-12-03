package hu.oe.nik.szfmv.automatedcar.emergencybreak;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.detector.CollisionDetection;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;
import hu.oe.nik.szfmv.environment.detector.RadarSensor;

public class EmergencyBreakSystem extends SystemComponent {

    List<EmergencyBreakListener> emercencyBreakListeners;
    private AutomatedCar car;
    private RadarSensor radar;

    /**
     * 
     * @param car
     * @param radar
     */
    public EmergencyBreakSystem(AutomatedCar car, RadarSensor radar) {

        emercencyBreakListeners = new ArrayList<>();
        registerListener(car.getPowerTrainSystem());
        this.car = car;
        this.radar = radar;

        VirtualFunctionBus.registerComponent(this);
    }

    /**
     * 
     * @param listener
     */
    public void registerListener(EmergencyBreakListener listener) {
        emercencyBreakListeners.add(listener);
    }

    /**
     * on every iteration
     */
    @Override
    public void loop() {

        detectPotentialCollision();

    }

    private void detectPotentialCollision() {
        List<IRadarSensor> objectsInRange = radar.getRadarObjectsInRange();
        double stoppingDistance = this.car.getCurrentSpeed().abs() / 9d;
        Vector2D carPosition = this.car.getPosition();

        Polygon dangerZone = new Polygon(
                new int[] { (int) carPosition.getX(), (int) carPosition.getX() + this.car.getWidth(), (int) (carPosition.getX()+stoppingDistance), (int) (carPosition.getX() + this.car.getWidth()+stoppingDistance)},
                new int[] { (int) carPosition.getY(), (int) carPosition.getY() + this.car.getWidth(),(int) (carPosition.getY()+stoppingDistance),(int) (carPosition.getY() + this.car.getWidth()+stoppingDistance) }, 4);
        
        CollisionDetection detection = new CollisionDetection();
        for (IRadarSensor iRadarSensor : objectsInRange) {
            if (detection.apply(dangerZone, iRadarSensor.getShape())) {
                onEmergency(EmergencyType.AEB_ACTIVATED);
            }
        }
    }

    /**
     * on emergency we notify all the listeners
     */
    private void onEmergency(EmergencyType type) {

        for (EmergencyBreakListener emergencyBreakListener : emercencyBreakListeners) {
            synchronized (emergencyBreakListener) {
                if (emergencyBreakListener != null) {
                    emergencyBreakListener.onEmergency(this, type);
                }
            }
        }
    }

    @Override
    public void receiveSignal(Signal s) {
        // this does not need to handle signals

    }

}
