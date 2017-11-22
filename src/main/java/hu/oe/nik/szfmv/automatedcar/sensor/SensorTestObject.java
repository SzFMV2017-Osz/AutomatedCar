package hu.oe.nik.szfmv.automatedcar.sensor;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class SensorTestObject extends WorldObject {

    public SensorTestObject(double x, double y) {
        super(x, y, 0, 10, 10, "not_exists.png", ModelShape.ELLIPSE);
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
}
