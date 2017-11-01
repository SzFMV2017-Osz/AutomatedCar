package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.SensorType;

import java.awt.*;

public class Sensor extends WorldObject {

    public Sensor(int[] xPoints, int[] yPoints, Car car, SensorType type) {
        super(xPoints[0], yPoints[0], 0, 10, 10, null, new Polygon(xPoints, yPoints, 3));
        this.car = car;
        this.type = type;
    }

    private Car car;
    private SensorType type;

    @Override
    public int getX() {
        return car.getX();
    }

    @Override
    public int getY() {
        return car.getY();
    }

    @Override
    public float getRotation() {
        return car.getRotation();
    }
}
