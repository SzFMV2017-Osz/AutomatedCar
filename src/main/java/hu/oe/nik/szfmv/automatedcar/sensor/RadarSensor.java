package hu.oe.nik.szfmv.automatedcar.sensor;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import java.util.ArrayList;

/**
 *
 * @author szum7
 * https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
 * http://jsfiddle.net/PerroAZUL/zdaY8/1/
 */
public class RadarSensor extends Sensor {

    /**
     * Reference point
     */
    private Vector2D a; 
    private Vector2D b, c;
    private double rotation;
    private final double DISTANCE = 200;
    private final double REFERENCE_ANGLE = 60;
    
    private SensorTestObject visualA;
    private SensorTestObject visualB;
    private SensorTestObject visualC;
    
    public RadarSensor() {        
        this.a = new Vector2D();
        this.b = new Vector2D();
        this.c = new Vector2D();
        this.rotation = -1;
    }

    /**
     * Sets all three points of the radar sensor triangle
     * @param referencePoint
     * @param carAngle in degree or radian (set isCarAngleDegree accordingly)
     * @param isCarAngleDegree degree or radian
     */
    public RadarSensor(Vector2D referencePoint, double carAngle, boolean isCarAngleDegree, World world) {
        super();
        
        this.a = new Vector2D();
        this.b = new Vector2D();
        this.c = new Vector2D();
        this.rotation = carAngle;        
        
        this.visualA = new SensorTestObject(this.a.getX(), this.a.getY());
        this.visualB = new SensorTestObject(this.a.getX(), this.a.getY());
        this.visualC = new SensorTestObject(this.a.getX(), this.a.getY());
        world.addObjectToWorld(visualA);
        world.addObjectToWorld(visualB);
        world.addObjectToWorld(visualC);

        this.setPoints(this.a, this.rotation, isCarAngleDegree);
    }

    /**
     * &nbsp;c . . b<br>
     * &nbsp;&nbsp;&nbsp;. .<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;a<br>
     * &nbsp;&nbsp;&nbsp;-^-<br>
     * &nbsp;&nbsp;&nbsp;| |<br>
     * &nbsp;&nbsp;&nbsp;---<br>
     * Sets "a" property to param referencePoint and calculates "b" and "c"
     * point coordinates.
     *
     * @param referencePoint car reference point
     * @param carAngle car angle
     * @param isCarAngleDegree
     */
    public void setPoints(Vector2D referencePoint, double carAngle, boolean isCarAngleDegree) {

        if (!isCarAngleDegree) {
            carAngle = Math.toDegrees(carAngle);
        }

        // store parameters
        this.setA(referencePoint);
        this.setRotation(carAngle);
        
        // calculations
        double bAngleCorrector = 270;
        double cAngleCorrector = 360;

        double diagonal = DISTANCE / Math.cos(REFERENCE_ANGLE);

        double bAngle = this.rotation - (REFERENCE_ANGLE / 2);
        bAngle -= bAngleCorrector;
        this.b.setX(Math.cos(Math.toRadians(bAngle)) * diagonal);
        this.b.setY(Math.sin(Math.toRadians(bAngle)) * diagonal);

        double cAngle = this.rotation + (REFERENCE_ANGLE / 2);
        cAngle = cAngleCorrector - cAngle;
        this.c.setX(Math.sin(Math.toRadians(cAngle)) * diagonal);
        this.c.setY(Math.cos(Math.toRadians(cAngle)) * diagonal);

        // add to reference point
        this.c = this.c.add(this.a);
        this.b = this.b.add(this.a);
    }

    public boolean isPointInRange(Vector2D point) {
        double A = 1 / 2 * (-b.getY() * c.getX() + point.getY() * (-b.getX() + c.getX()) + point.getX() * (b.getY() - c.getY()) + b.getX() * c.getY());
        int sign = A < 0 ? -1 : 1;
        double s = (a.getY() * c.getX() - a.getX() * c.getY() + (c.getY() - a.getY()) * point.getX() + (a.getX() - c.getX()) * point.getY()) * sign;
        double t = (a.getX() * b.getY() - a.getY() * b.getX() + (a.getY() - b.getY()) * point.getX() + (b.getX() - a.getX()) * point.getY()) * sign;

        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }

    /**
     * Get closest vectors to the given reference vector.
     * @param points
     * @param referencePoint
     * @return Returns more then one vectors if they are equally close.
     */
    public ArrayList<Vector2D> getClosestVectors(ArrayList<Vector2D> points, Vector2D referencePoint) {

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
     * Get closest vectors to the sensor's reference point
     * @param points
     * @return Returns more then one vectors if they are equally close.
     */
    public ArrayList<Vector2D> getClosestVectorsInRange(ArrayList<Vector2D> points) {
        ArrayList<Vector2D> closests = new ArrayList<>();
        int i = 0;
        double minDist = Double.POSITIVE_INFINITY;

        while (i < points.size()) {

            if (this.isPointInRange(points.get(i))) {

                double actDist = Math.sqrt(Math.pow((this.a.getX() - points.get(i).getX()), 2) + Math.pow((this.a.getY() - points.get(i).getY()), 2));

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

    public void setA(Vector2D a) {
        this.a = a;
    }

    public void setReferencePoint(Vector2D vector) {
        this.setA(vector);
    }
    
    public double getX(){
        return this.a.getX();
    }    
    
    public double getY(){
        return this.a.getY();
    }
    
    public double getRotation(){
        return this.rotation;
    }
    
    private void setRotation(double rotation){
        this.rotation = rotation;
    }
        
    public void setVisuals(){
        this.visualA.setX(this.a.getX());
        this.visualA.setY(this.a.getY());
        
        this.visualB.setX(this.b.getX());
        this.visualB.setY(this.b.getY());
        
        this.visualC.setX(this.c.getX());
        this.visualC.setY(this.c.getY());        
    }
    
    @Override
    public String toString(){
        return String.format("a: (%1d, %2d), b: (%3d, %4d), c: (%5d, %6d), rotation: %7d", this.a.getX(), this.a.getY(), this.b.getX(), this.b.getY(), this.c.getX(), this.c.getY(), this.rotation);
    }
}
