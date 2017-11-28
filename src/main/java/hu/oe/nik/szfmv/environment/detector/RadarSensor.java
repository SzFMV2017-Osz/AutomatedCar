package hu.oe.nik.szfmv.environment.detector;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szum7
 */
public class RadarSensor implements IRadarSensor {

    /**
     * Reference point
     */
    private Vector2D a; 
    private Vector2D b, c;
    /**
     * Should always be in degrees, not radian!
     */
    private double angle;
    private final double DISTANCE = 200;
    private final double REFERENCE_ANGLE = 60;
    
    private AutomatedCar car;
    
    /**
     * Init un-calculated radar sensor. (points uninitialized. Use this.updatePoints()!)
     * @param car
     * @param worldObjects 
     */
    public RadarSensor(AutomatedCar car){
        this.car = car;
        
        this.a = new Vector2D();
        this.b = new Vector2D();
        this.c = new Vector2D();
        
        this.updateAngle();
    }

    /**
     * &nbsp;c . . b<br>
     * &nbsp;&nbsp;&nbsp;. .<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;a<br>
     * &nbsp;&nbsp;&nbsp;-^-<br>
     * &nbsp;&nbsp;&nbsp;| |<br>
     * &nbsp;&nbsp;&nbsp;---<br>
     * Sets "a" property to the referencePoint vector and calculates "b" and "c"
     * vector coordinates.
     *
     */
    public void updatePoints() {

        // store parameters
        this.updateReferencePoint(this.car.getPosition());
        this.updateAngle();
        
        // calculations
        double bAngleCorrector = 270;
        double cAngleCorrector = 360;

        double diagonal = DISTANCE / Math.cos(REFERENCE_ANGLE);

        double bAngle = this.angle - (REFERENCE_ANGLE / 2);
        bAngle -= bAngleCorrector;
        this.b.setX(Math.cos(Math.toRadians(bAngle)) * diagonal);
        this.b.setY(Math.sin(Math.toRadians(bAngle)) * diagonal);

        double cAngle = this.angle + (REFERENCE_ANGLE / 2);
        cAngle = cAngleCorrector - cAngle;
        this.c.setX(Math.sin(Math.toRadians(cAngle)) * diagonal);
        this.c.setY(Math.cos(Math.toRadians(cAngle)) * diagonal);

        // add to reference point
        this.c = this.c.add(this.a);
        this.b = this.b.add(this.a);
    }
    
    private void updateAngle(){
        this.angle = Math.toDegrees(this.car.getRotation());
    }

    public boolean isPointInRange(Vector2D point) {
        double A = 1 / 2 * (-b.getY() * c.getX() + point.getY() * (-b.getX() + c.getX()) + point.getX() * (b.getY() - c.getY()) + b.getX() * c.getY());
        int sign = A < 0 ? -1 : 1;
        double s = (a.getY() * c.getX() - a.getX() * c.getY() + (c.getY() - a.getY()) * point.getX() + (a.getX() - c.getX()) * point.getY()) * sign;
        double t = (a.getX() * b.getY() - a.getY() * b.getX() + (a.getY() - b.getY()) * point.getX() + (b.getX() - a.getX()) * point.getY()) * sign;

        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }

    public boolean isObjectInRange(WorldObject point) {
        double A = 1 / 2 * (-b.getY() * c.getX() + point.getY() * (-b.getX() + c.getX()) + point.getX() * (b.getY() - c.getY()) + b.getX() * c.getY());
        int sign = A < 0 ? -1 : 1;
        double s = (a.getY() * c.getX() - a.getX() * c.getY() + (c.getY() - a.getY()) * point.getX() + (a.getX() - c.getX()) * point.getY()) * sign;
        double t = (a.getX() * b.getY() - a.getY() * b.getX() + (a.getY() - b.getY()) * point.getX() + (b.getX() - a.getX()) * point.getY()) * sign;

        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }

    /**
     * Get closest vectors to the sensor's reference point
     * @param points
     * @return Returns more then one vectors if they are equally close.
     */
    public ArrayList<Vector2D> getClosestVectors(ArrayList<Vector2D> points) {
        
        Vector2D referencePoint = this.a;

        ArrayList<Vector2D> closests = new ArrayList<>();
        int i = 0;
        double minDist = Double.POSITIVE_INFINITY;

        while (i < points.size()) {

            double actDist = Math.sqrt(Math.pow((referencePoint.getX() - points.get(i).getX()), 2) + Math.pow((referencePoint.getY() - points.get(i).getY()), 2));

            if (actDist < minDist) {
                closests = new ArrayList<>();
                closests.add(points.get(i));
                minDist = actDist;
            } else if (actDist == minDist) {
                closests.add(points.get(i));
                minDist = actDist;
            }
            i++;
        }

        return closests;
    }

    /**
     * Get closest vectors to the sensor's reference point, in the sensor's range
     * @param points
     * @return Returns more then one vectors if they are equally close.
     */
    public ArrayList<Vector2D> getClosestVectorsInRange(ArrayList<Vector2D> points) {
        ArrayList<Vector2D> closests = new ArrayList<>();
        int i = 0;
        double minDist = Double.POSITIVE_INFINITY;
        Vector2D referencePoint = this.a;

        while (i < points.size()) {

            if (this.isPointInRange(points.get(i))) {

                double actDist = Math.sqrt(Math.pow((referencePoint.getX() - points.get(i).getX()), 2) + Math.pow((referencePoint.getY() - points.get(i).getY()), 2));

                if (actDist < minDist) {
                    closests = new ArrayList<>();
                    closests.add(points.get(i));
                    minDist = actDist;
                } else if (actDist == minDist) {
                    closests.add(points.get(i));
                    minDist = actDist;
                }
            }            
            i++;
        }

        return closests;
    }
    
    /**
     * Get closest WorldObjects to the sensor's reference point
     * @param objects
     * @return Returns more then one WorldObjects if they are equally close.
     */
    public List<WorldObject> getClosestWorldObjects(List<WorldObject> objects){
        
        Vector2D referencePoint = this.a;

        ArrayList<WorldObject> closests = new ArrayList<>();
        int i = 0;
        double minDist = Double.POSITIVE_INFINITY;

        while (i < objects.size()) {

            double actDist = Math.sqrt(Math.pow((referencePoint.getX() - objects.get(i).getX()), 2) + Math.pow((referencePoint.getY() - objects.get(i).getY()), 2));

            if (actDist < minDist) {
                closests = new ArrayList<>();
                closests.add(objects.get(i));
                minDist = actDist;
            } else if (actDist == minDist) {
                closests.add(objects.get(i));
                minDist = actDist;
            }
            i++;
        }

        return closests;
    }
    
    /**
     * Get closest WorldObjects to the sensor's reference point, in the sensor's range
     * @param objects
     * @return Returns more then one WorldObjects if they are equally close.
     */
    public List<WorldObject> getClosestWorldObjectsInRange(List<WorldObject> objects){
        
        ArrayList<WorldObject> closests = new ArrayList<>();
        int i = 0;
        double minDist = Double.POSITIVE_INFINITY;
        Vector2D referencePoint = this.a;

        while (i < objects.size()) {

            if (this.isObjectInRange(objects.get(i))) {

                double actDist = Math.sqrt(Math.pow((referencePoint.getX() - objects.get(i).getX()), 2) + Math.pow((referencePoint.getY() - objects.get(i).getY()), 2));

                if (actDist < minDist) {
                    closests = new ArrayList<>();
                    closests.add(objects.get(i));
                    minDist = actDist;
                } else if (actDist == minDist) {
                    closests.add(objects.get(i));
                    minDist = actDist;
                }
            }            
            i++;
        }

        return closests;
    }
    
    /**
     * Private method, only in class should the reference point be updated
     * @param a 
     */
    private void updateReferencePoint(Vector2D a){
        this.a = a;
    }
    
    public double getX(){
        return this.a.getX();
    }    
    
    public double getY(){
        return this.a.getY();
    }
    
    public double getAngle(){
        return this.angle;
    }
    
    @Override
    public Vector2D getCurrentSpeed(){
        return this.car.getCurrentSpeed();
    }
    
    @Override
    public String toString(){
        return String.format("a: (%1d, %2d), b: (%3d, %4d), c: (%5d, %6d), rotation: %7d", this.a.getX(), this.a.getY(), this.b.getX(), this.b.getY(), this.c.getX(), this.c.getY(), this.angle);
    }
}
