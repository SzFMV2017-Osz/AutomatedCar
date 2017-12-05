package hu.oe.nik.szfmv.environment.detector;

import java.awt.*;
import java.awt.geom.Area;
import java.util.function.BiFunction;

public class CollisionDetection implements BiFunction<Shape, Shape, Boolean> {

    @Override
    public Boolean apply(Shape shape, Shape shape2) {
	Area areaA = new Area(shape);
	Area areaB = new Area(shape2);
	areaA.intersect(areaB);
	return !areaA.isEmpty();
    }
}
