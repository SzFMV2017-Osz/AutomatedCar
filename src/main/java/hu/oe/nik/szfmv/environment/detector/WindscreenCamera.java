package hu.oe.nik.szfmv.environment.detector;


import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Road;
import hu.oe.nik.szfmv.environment.util.DetectedRoad;
import hu.oe.nik.szfmv.environment.util.RoadType;
import hu.oe.nik.szfmv.environment.xml.Utils;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.DoubleToLongFunction;

public class WindscreenCamera extends SystemComponent implements ISensor {

    public double X;
    public double Y;
    public double rotation;
    public List<WorldObject> worldObjects;

    private static Double ID;

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    private AutomatedCar playerCar;
    private Point leftRange;
    private Point rightRange;
    private HashMap<Double,WorldObject> objectsInRange = new HashMap<Double, WorldObject>();
    private HashMap<WorldObject, Double> filteredRoadObjects = new HashMap<WorldObject, Double>();
    private LinkedHashMap<WorldObject, Double> filteredRoadSignObjectsHashMap = new LinkedHashMap<WorldObject, Double>();
    private Boolean isParkingLotDetector;

    public LinkedHashMap<WorldObject, Double> getFilteredRoadSignObjectsHashMap(){ return this.filteredRoadSignObjectsHashMap; }

    public double getX() {
        return this.X;
    }

    public double getY() {
        return this.Y;
    }

    public Vector2D getPosition(){return new Vector2D(this.X,this.Y);}

    public double getRotation() {
        return this.rotation;
    }

    public WindscreenCamera(AutomatedCar playerCar, List<WorldObject> worldObjects, Boolean isParkingLotDetector) {
        this.playerCar = playerCar;
        this.worldObjects = worldObjects;
        this.isParkingLotDetector = isParkingLotDetector;
        calculateCameraLocation(this.playerCar);
    }

    public void updateCameraPosition() {
        calculateCameraLocation(this.playerCar);
        updateDetectionRange();
    }

    @Override
    public void loop() {
        updateCameraPosition();
        filterObjectsInRange();
        removeOldObjectsFromObjectsInRange();
    }

    @Override
    public void receiveSignal(Signal s) {

    }

    private void updateDetectionRange() {
        setLeftRange();
        setRightRange();
    }

    private void calculateCameraLocation(AutomatedCar playerCar) {
        X = playerCar.getX() + (playerCar.getWidth() / 2);
        Y = playerCar.getY() + (playerCar.getHeight() / 3);
        rotation = playerCar.getRotation();
    }

    private void setLeftRange() {
        leftRange = new Point((int)this.getX() -150, (int)this.getY() -150);

        if (isParkingLotDetector) {
            rotatePointAroundCameraPointByDegreeInDouble(rotation + 90, leftRange);
            return;
        }
        rotatePointAroundCameraPointByDegreeInDouble(rotation, leftRange);
    }

    private void setRightRange() {
        rightRange = new Point((int) this.getX() + 150, (int) this.getY() - 150);

        if (isParkingLotDetector) {
            rotatePointAroundCameraPointByDegreeInDouble(rotation + 90, leftRange);
            return;
        }
        rotatePointAroundCameraPointByDegreeInDouble(rotation, leftRange);
    }

    private double calculateDistanceOfRoadSign(Point roadSignsBottomLeftPoint)
    {
        double distance = Math.hypot(
                Math.abs(this.getX() - roadSignsBottomLeftPoint.getX()),
                Math.abs(this.getY() - roadSignsBottomLeftPoint.getY()));

        return distance;
    }

    private void rotatePointAroundCameraPointByDegreeInDouble(double angle, Point pointToRotate)
    {
        double x1 = pointToRotate.getX() - this.getX();
        double y1 = pointToRotate.getY() - this.getY();

        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

        double newPointA = x2 + this.getX();
        double newPointB = y2 + this.getY();

        pointToRotate.setLocation(newPointA, newPointB);
    }

    public ArrayList<WorldObject> GetWorldObjectsInRangeForPatkingLotDetection()
    {
        ArrayList<WorldObject> filteredObjectsForParkingLotDetector = new ArrayList<WorldObject>();

        for (WorldObject worldObject : objectsInRange.values())
        {
            if(worldObject instanceof CollidableObject)
            {
                filteredObjectsForParkingLotDetector.add(worldObject);
            }
        }

        return filteredObjectsForParkingLotDetector;
    }

    private void filterObjectsInRange()
    {
        for (WorldObject object : worldObjects ) {
            if (objectIsInRange(object))
            {
                objectsInRange.put(ID, object);
                ID++;

                if (object.getImageFileName().contains("roadsign"))
                {
                    filteredRoadSignObjectsHashMap.put(object, calculateDistanceOfRoadSign(
                            new Point((int)object.getPosition().getX(), (int)object.getPosition().getY())
                    ));
                }
                else if (object.getImageFileName().contains("road"))
                {
                    filteredRoadObjects.put(object, new Double(0));
                }
            }
        }
    }

    private void removeOldObjectsFromObjectsInRange()
    {
        for (WorldObject objectToRemove : objectsInRange.values())
        {
            if (!(objectIsInRange(objectToRemove)))
            {
                objectsInRange.remove(objectToRemove);
            }
        }
    }

    private Boolean objectIsInRange(WorldObject object){
        return (pointIsInRange(new Point((int)object.getX(), (int)object.getY())) ||
                pointIsInRange(new Point((int)object.getX()+object.getWidth(),
                        (int)object.getY()+object.getHeight())) ||
                        pointIsInRange(new Point((int)object.getX(), (int)object.getY() + object.getHeight() )));

    }

    private Boolean pointIsInRange(Point objectPoint)
    {
        Point objectPosition = new Point((int)objectPoint.getX(), (int)objectPoint.getY());
        Point cameraPosition = new Point((int)this.getX(), (int)this.getY());

        double A = 1/2 * (-leftRange.getY()*rightRange.getX() + cameraPosition.getY()* (-leftRange.getX() + rightRange.getX())
                + cameraPosition.getX() * (leftRange.getY() - rightRange.getY()) +leftRange.getX() * rightRange.getX());

        int sign = A < 0 ? -1 : 1;

        double s = (cameraPosition.getY()*rightRange.getX() - cameraPosition.getX() * rightRange.getY() +
                (rightRange.getY() - cameraPosition.getY())*objectPosition.getX()
        + (cameraPosition.getX() - rightRange.getX() * objectPosition.getY())) * sign;

        double t = (cameraPosition.getX() * leftRange.getY() - cameraPosition.getY() * leftRange.getX() + (cameraPosition.getY() - leftRange.getY())
        *objectPosition.getX() + (leftRange.getX() - cameraPosition.getX()) * objectPosition.getY()) * sign;

        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }

    public DetectedRoad getDetectedRoadInfo()
    {
        Road road = null;
        double distance = Double.MAX_VALUE;
        DetectedRoad detectedRoad = new DetectedRoad();

     //   for (WorldObject r : filteredRoadObjects){
        for (Map.Entry<WorldObject, Double> entry: filteredRoadObjects.entrySet())
        {
            WorldObject r = entry.getKey();

            double tempDistance = Utils.getVectorDistance(r.getPosition(),this.getPosition());
            if (road == null || tempDistance<distance)
            {
                road = (Road)r;
                distance = tempDistance;
            }
        }

        if (road!=null) {

            String imageName = road.getImageFileName();

            if (imageName.contains("_6right"))
                detectedRoad.roadDirection = RoadType.RIGHT;

            if (imageName.contains("_6left"))
                detectedRoad.roadDirection = RoadType.LEFT;

            switch (imageName) {
                case "road_2lane_6left.png":
                    detectedRoad.radiusLeft = 601.9930553878453;
                    detectedRoad.radiusMiddle = 643.9930553878453;
                    detectedRoad.radiusRight = 685.9930553878453;;
                    detectedRoad.lineKeepingPossible = true;
                    detectedRoad.angleInDegrees = 6;
                    break;
                case "road_2lane_6right.png":
                    detectedRoad.radiusLeft = 685.9930553878453;
                    detectedRoad.radiusMiddle = 643.9930553878453;
                    detectedRoad.radiusRight = 601.9930553878453;
                    detectedRoad.lineKeepingPossible = true;
                    detectedRoad.angleInDegrees = 6;
                    break;
                case "road_2lanestraight.png":
                    detectedRoad.radiusLeft = 0;
                    detectedRoad.radiusMiddle = 0;
                    detectedRoad.radiusRight = 0;
                    detectedRoad.lineKeepingPossible = true;
                    detectedRoad.angleInDegrees = 0;
                default:
                    detectedRoad.radiusLeft = 0;
                    detectedRoad.radiusMiddle = 0;
                    detectedRoad.radiusRight = 0;
                    detectedRoad.lineKeepingPossible = false;
                    detectedRoad.angleInDegrees = 0;
            }
        }
        return detectedRoad;
    }
}
