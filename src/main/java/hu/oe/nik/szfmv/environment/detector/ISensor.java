package hu.oe.nik.szfmv.environment.detector;

import java.awt.Shape;

import hu.oe.nik.szfmv.common.Vector2D;

public interface ISensor {
    
    Vector2D getPosition();

    double getRotation();
    
    Shape getShape();
}
