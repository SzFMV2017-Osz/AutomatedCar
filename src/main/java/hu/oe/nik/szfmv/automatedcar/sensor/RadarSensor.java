package hu.oe.nik.szfmv.automatedcar.sensor;

import hu.oe.nik.szfmv.common.Vector2D;

/**
 *
 * @author szum7
 * https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
 * http://jsfiddle.net/PerroAZUL/zdaY8/1/
 */
public class RadarSensor extends Sensor {
    
    private Vector2D a, b, c;
    private final double DISTANCE = 200;
    private final double REFERENCE_ANGLE = 60;
    
    public RadarSensor(Vector2D referencePoint, double carAngle, boolean isCarAngleDegree){
        super();
        
        this.a = referencePoint;
        this.setPoints(this.a, carAngle, isCarAngleDegree);
    }
    
    /**
     *&nbsp;c . . b<br>
     *&nbsp;&nbsp;&nbsp;.   .<br>
     *&nbsp;&nbsp;&nbsp;&nbsp;a<br>
     *&nbsp;&nbsp;&nbsp;-^-<br>
     *&nbsp;&nbsp;&nbsp;| |<br>
     *&nbsp;&nbsp;&nbsp;---<br>
     * Sets "a" property to param referencePoint and calculates "b" and "c" point coordinates.
     * @param referencePoint car reference point
     * @param carAngle car angle 
     * @param isCarAngleDegree
     */
    public void setPoints(Vector2D referencePoint, double carAngle, boolean isCarAngleDegree){
        
        if(!isCarAngleDegree){
            carAngle = Math.toDegrees(carAngle);
        }
        
        this.setA(referencePoint);
        
        double diagonal = DISTANCE / Math.cos(REFERENCE_ANGLE);
        
        double bAngle = carAngle - (REFERENCE_ANGLE / 2);
        bAngle -= 270;
        this.b.setX(Math.cos(Math.toRadians(bAngle)) * diagonal);
        this.b.setY(Math.sin(Math.toRadians(bAngle)) * diagonal);
        
        double cAngle = carAngle + (REFERENCE_ANGLE / 2);
        cAngle = 360 - cAngle;
        this.c.setX(Math.sin(Math.toRadians(bAngle)) * diagonal);
        this.c.setY(Math.cos(Math.toRadians(bAngle)) * diagonal);
        
        // add to reference point
        this.c.add(this.a);
        this.b.add(this.a);
    }
    
    public boolean isPointInRange(Vector2D point){
        double A = 1/2 * (-b.getY() * c.getX() + point.getY() * (-b.getX() + c.getX()) + point.getX() * (b.getY() - c.getY()) + b.getX() * c.getY());
        int sign = A < 0 ? -1 : 1;
        double s = (a.getY() * c.getX() - a.getX() * c.getY() + (c.getY() - a.getY()) * point.getX() + (a.getX() - c.getX()) * point.getY()) * sign;
        double t = (a.getX() * b.getY() - a.getY() * b.getX() + (a.getY() - b.getY()) * point.getX() + (b.getX() - a.getX()) * point.getY()) * sign;
    
        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }
    
    public void setA(Vector2D a){
        this.a = a;
    }
    
    public void setReferencePoint(Vector2D vector){
        this.setA(vector);
    }
}
