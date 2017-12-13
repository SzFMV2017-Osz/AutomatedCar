package hu.oe.nik.szfmv.environment.detector;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Sensor;
import hu.oe.nik.szfmv.environment.util.SensorType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szum7
 */
public class RadarSensor extends Sensor {
    
    /**
     * Should always be in degrees, not radian!
     */
    private double angle;
    private List<WorldObject> worldObjects;
    private List<WorldObject> worldObjectsInRange;
    private AutomatedCar car;
    
    private final double DISTANCE = 200;
    private final double REFERENCE_ANGLE = 60;
    
    public RadarSensor(AutomatedCar car, List<WorldObject> worldObjects) {
        super(new double[3], new double[3], car, SensorType.RADAR);
        
        this.car = car;
        this.worldObjects = worldObjects;
        
        this.xPoints = new double[3];
        this.yPoints = new double[3];
        this.worldObjectsInRange = new ArrayList<WorldObject>();
        
        this.updatePoints();
    }
    
    public boolean isWorldObjectInRange(WorldObject object){
        return this.getShape().intersects(object.getX(), object.getY(), object.getWidth(), object.getHeight());
    } 
    
    /**
     * &nbsp;b . . c<br>
     * &nbsp;&nbsp;&nbsp;. .<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;a<br>
     * &nbsp;&nbsp;&nbsp;-^-<br>
     * &nbsp;&nbsp;&nbsp;| |<br>
     * &nbsp;&nbsp;&nbsp;---<br>
     * 1. Sets/updates "a" property to the referencePoint vector and calculates "b" and "c"
     * vector coordinates.<br>
     * 2. Sets/updates WorldObjectsInRange array property
     */
    public void update(){
        this.updatePoints();
        this.updateWorldObjectsInRange();
    }
    
    private void updateWorldObjectsInRange(){
        this.worldObjectsInRange = new ArrayList<>();
        for(WorldObject object : this.worldObjects){
            if((object instanceof CollidableObject || object instanceof MovingObject) 
                    && this.isWorldObjectInRange(object)){
                this.worldObjectsInRange.add(object);
            }
        }
    }
    
    /**
     * &nbsp;b . . c<br>
     * &nbsp;&nbsp;&nbsp;. .<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;a<br>
     * &nbsp;&nbsp;&nbsp;-^-<br>
     * &nbsp;&nbsp;&nbsp;| |<br>
     * &nbsp;&nbsp;&nbsp;---<br>
     * Sets "a" property to the referencePoint vector and calculates "b" and "c"
     * vector coordinates.
     * 
     */    
    private void updatePoints() {

        // store parameters
        this.updateAngle();
        this.updateReferencePoint();

        // calculations
        double bAngleCorrector = 90;
        double cAngleCorrector = 180;

        double diagonal = DISTANCE / Math.cos(Math.toRadians(REFERENCE_ANGLE / 2));

        // point b
        double bAngle = this.angle - (REFERENCE_ANGLE / 2);
        bAngle -= bAngleCorrector;
        this.xPoints[1] = (Math.cos(Math.toRadians(bAngle)) * diagonal);
        this.yPoints[1] = (Math.sin(Math.toRadians(bAngle)) * diagonal);

        // point c
        double cAngle = this.angle + (REFERENCE_ANGLE / 2);
        cAngle = cAngleCorrector - cAngle;
        this.xPoints[2] = (Math.sin(Math.toRadians(cAngle)) * diagonal);
        this.yPoints[2] = (Math.cos(Math.toRadians(cAngle)) * diagonal);

        // add to reference point to point b
        this.xPoints[1] += this.xPoints[0];
        this.yPoints[1] += this.yPoints[0];
        // add to reference point to point c
        this.xPoints[2] += this.xPoints[0];
        this.yPoints[2] += this.yPoints[0];
    }

    /**
     * Get closest WorldObjects to the sensor's reference point (car's front middle point)
     *
     * @param objects
     * @return Returns more then one WorldObjects if they are equally close and empty ArrayList, if no WorldObject is in range
     */
    public ArrayList<WorldObject> getClosestWorldObjectsInRange(){
        
        ArrayList<WorldObject> closests = new ArrayList<>();
        int i = 0;
        double minDist = Double.POSITIVE_INFINITY;
        
        while (i < this.worldObjectsInRange.size()) {
            
            double actDist = Math.sqrt(Math.pow((this.xPoints[0] - this.worldObjectsInRange.get(i).getX()), 2) + Math.pow((this.yPoints[0] - this.worldObjectsInRange.get(i).getY()), 2));

            if (actDist < minDist) {
                closests = new ArrayList<>();
                closests.add(this.worldObjectsInRange.get(i));
                minDist = actDist;
            } else if (actDist == minDist) {
                closests.add(this.worldObjectsInRange.get(i));
                minDist = actDist;
            }
            
            i++;
        }
        return closests;
    }

    /**
     * Private method, the reference point should be updated only in-class
     * 
     */
    private void updateReferencePoint() {
        
        this.xPoints[0] = this.car.getX();
        this.yPoints[0] = this.car.getY();

        double triAngle = this.angle;
        double carHalfDist = this.car.getHeight() / 2;

        if (triAngle <= 90) {
            this.xPoints[0] += Math.sin(Math.toRadians(triAngle)) * carHalfDist;
            this.yPoints[0] -= Math.cos(Math.toRadians(triAngle)) * carHalfDist;
        } else if (triAngle <= 180) {
            triAngle -= 90;
            this.xPoints[0] += Math.cos(Math.toRadians(triAngle)) * carHalfDist;
            this.yPoints[0] += Math.sin(Math.toRadians(triAngle)) * carHalfDist;
        } else if (triAngle <= 270) {
            triAngle -= 180;
            this.xPoints[0] -= Math.sin(Math.toRadians(triAngle)) * carHalfDist;
            this.yPoints[0] += Math.cos(Math.toRadians(triAngle)) * carHalfDist;
        } else {
            triAngle -= 270;
            this.xPoints[0] -= Math.cos(Math.toRadians(triAngle)) * carHalfDist;
            this.yPoints[0] -= Math.sin(Math.toRadians(triAngle)) * carHalfDist;
        }
    }

    private void updateAngle() {
        this.angle = Math.toDegrees(this.car.getRotation());
    }
    
    public Vector2D getA(){
        return new Vector2D(this.xPoints[0], this.yPoints[0]);
    }
    
    public Vector2D getB(){
        return new Vector2D(this.xPoints[1], this.yPoints[1]);
    }
    
    public Vector2D getC(){
        return new Vector2D(this.xPoints[2], this.yPoints[2]);
    }
    
    public List<WorldObject> getWorldObjectsInRange(){
        return this.worldObjectsInRange;
    }

    @Override
    public String toString() {
        return "a: (" + xPoints[0] + ", " + yPoints[0] + "), b: (" + xPoints[1] + ", " + yPoints[1] + "), c: (" + xPoints[2] + ", " + yPoints[2] + "), rotation: " + this.angle;
    }
    
    public String getTestPrint(){
        return "In-range world objects count = " + worldObjectsInRange.size();
    }
}