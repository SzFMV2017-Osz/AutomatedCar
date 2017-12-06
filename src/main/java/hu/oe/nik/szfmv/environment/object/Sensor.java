package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.detector.ISensor;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.SensorType;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Sensor extends WorldObject implements ISensor {

    public Sensor(double[] xPoints, double[] yPoints, AutomatedCar car, SensorType type) {
        //TODO RENDBE TENNI AZ IAMGE RÉSZT. SENSOR ATALAKITAS SZUKSEGES AZ EGYSÉGES WORLDOBJECT KEZELESHEZ
        super(0, 0, 0, "tree.png", null);
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.car = car;
        this.type = type;
    }

    private AutomatedCar car;
    private SensorType type;
    private double[] xPoints;
    private double[] yPoints;

    @Override
    public double getX() {
        return car.getX();
    }


    @Override
    public Shape getShape() {
        AffineTransform t = new AffineTransform();
        t.rotate(car.getRotation(), car.getX(), car.getY());
        t.translate(car.getX(), car.getY());

        return t.createTransformedShape(new Polygon(
                getRoundedPoints(xPoints),
                getRoundedPoints(yPoints),
                3));
    }

    @Override
    public double getY() {
        return car.getY();
    }

    @Override
    public double getRotation() {
        return car.getRotation();
    }

    public AutomatedCar getCar() {
        return car;
    }

    public SensorType getType() {
        return type;
    }

    private int[] getRoundedPoints(double[] points) {
        int[] roundedPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            roundedPoints[i] = (int)Math.round(points[i]);
        }
        return roundedPoints;
    }
}
