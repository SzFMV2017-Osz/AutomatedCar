package hu.oe.nik.szfmv.environment.detector;

import java.awt.Shape;

import hu.oe.nik.szfmv.common.Vector2D;

public interface IRadarSensor {
    Vector2D getCurrentSpeed();

    Shape getShape();
}