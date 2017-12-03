package hu.oe.nik.szfmv.environment.detector;

import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.WorldObject;

public class RadarSensor extends SystemComponent{

    public double X;
    public double Y;
    public double rotation;
    public List<WorldObject> worldObjects;

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    private AutomatedCar playerCar;
    private Point leftRange;
    private Point rightRange;

    public double getX() {
        return this.X;
    }

    public double getY() {
        return this.Y;
    }

    public Vector2D getPosition() {
        return new Vector2D(this.X, this.Y);
    }

    public double getRotation() {
        return this.rotation;
    }

    public RadarSensor(AutomatedCar playerCar, List<WorldObject> worldObjects) {
        this.playerCar = playerCar;
        this.worldObjects = worldObjects;
        calculateRadarLocation(this.playerCar);
    }

    public void updateRadarPosition() {
        calculateRadarLocation(this.playerCar);
        updateDetectionRange();
    }

    @Override
    public void loop() {
        updateRadarPosition();
    }

    @Override
    public void receiveSignal(Signal s) {

    }

    private void updateDetectionRange() {
        setLeftRange();
        setRightRange();
    }

    private void calculateRadarLocation(AutomatedCar playerCar) {
        X = playerCar.getX() + (playerCar.getWidth() / 2);
        Y = playerCar.getY() + (playerCar.getHeight() / 3);
        rotation = playerCar.getRotation();
    }

    private void setLeftRange() {
        leftRange = new Point((int) this.getX() - 150, (int) this.getY() - 150);
        rotatePointAroundCameraPointByDegreeInDouble(rotation, leftRange);
    }

    private void setRightRange() {
        rightRange = new Point((int) this.getX() + 150, (int) this.getY() - 150);
        rotatePointAroundCameraPointByDegreeInDouble(rotation, leftRange);
    }

  

    private void rotatePointAroundCameraPointByDegreeInDouble(double angle, Point pointToRotate) {
        double x1 = pointToRotate.getX() - this.getX();
        double y1 = pointToRotate.getY() - this.getY();

        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

        double newPointA = x2 + this.getX();
        double newPointB = y2 + this.getY();

        pointToRotate.setLocation(newPointA, newPointB);
    }
    
    public List<IRadarSensor> getRadarObjectsInRange(){
        ObjectDetector detector = new ObjectDetector(worldObjects, new CollisionDetection());
        return detector.getRadarObjects(new Polygon(new int[] { (int) this.X, this.leftRange.x, this.rightRange.x },
                new int[] { (int) this.Y, this.leftRange.y, this.rightRange.y }, 3));
    }

}
