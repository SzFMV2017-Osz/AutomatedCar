package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class AutomatedCar extends MovingObject {

    private PowertrainSystem powertrainSystem;
    private int wheelAngle = 0;

    // Variables for test
    private final double VISUAL_CORRECTION = 5;
    private final double CIRCULAR_TRACK_LENGTH = 1080;
    private double positionOnTrack = 0;

    public AutomatedCar(int x, int y, float rotation, String imageFileName) {
        super(x, y, rotation, imageFileName, 1337, ModelShape.RECTENGULAR);

        powertrainSystem = new PowertrainSystem(x, y, new PorscheCharacteristics());
    }

    public void drive() {
        VirtualFunctionBus.loop();
        accelerate();
        wheelAngle = powertrainSystem.getWheelAngle();
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.STEERINGWHEEL, wheelAngle));
        move();
    }

    private void accelerate() {
        if (this.getCurrentSpeed().abs() > 0) {
            Vector2D vector = this.getCurrentSpeed().normalize().mult((float)powertrainSystem.getAcceleration());
            this.changeDirection(this.getCurrentSpeed().normalize().mult((float)powertrainSystem.getAcceleration()));
            log.info("current speed rotation in rad: " + vector.getAngleRadian() + " and deg " +vector.getAngle());
        } else {
            Vector2D vector =  Vector2D.getForwardVectorRadian(this.getRotation());
            log.info("current speed rotation in rad: " + vector.getAngleRadian() + " and deg " +vector.getAngle());
            this.changeDirection(vector.mult((float)powertrainSystem.getAcceleration()));
        }
    }

    @Override
    protected void doOnCollision() {

    }
}
