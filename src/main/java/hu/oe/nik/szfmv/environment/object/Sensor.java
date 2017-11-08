package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.SensorType;
import hu.oe.nik.szfmv.environment.util.ModelShape;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Sensor extends WorldObject {

    public Sensor(int[] xPoints, int[] yPoints, Car car, SensorType type) {
        super(xPoints[0], yPoints[0], 0, 0, 0, null, /*new Polygon(xPoints, yPoints, 3)*/ ModelShape.POLYGON);
        this.car = car;
        this.type = type;
    }

    private Car car;
    private SensorType type;

    @Override
    public double getX() {
        return car.getX();
    }

    @Override
    public Shape getShape() {
        AffineTransform t = new AffineTransform();
        t.rotate(car.getRotation(), car.getX(), car.getY());

        return t.createTransformedShape(super.getShape());
    }

    @Override
    public double getY() {
        return car.getY();
    }

    @Override
    public double getRotation() {
        return car.getRotation();
    }

    public Car getCar() {
        return car;
    }
}
