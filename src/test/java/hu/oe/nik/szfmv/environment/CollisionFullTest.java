package hu.oe.nik.szfmv.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hu.oe.nik.szfmv.environment.detector.CollisionDetection;
import hu.oe.nik.szfmv.environment.detector.ICameraSensor;
import hu.oe.nik.szfmv.environment.detector.ObjectDetector;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;

public class CollisionFullTest {
    
    public Car c0;
    public Car c45;
    public Car c90;
    public Car cAway;
    public List<WorldObject> world;
    
    @Before
    public void create() {
        c0 = Car.builder().position(1, 1).rotation(0).color("white").weight(1200).build();
        c45 = Car.builder().position(5, 10).rotation((float)Math.PI/4).color("white").weight(1200).build();
        c90 = Car.builder().position(10, 10).rotation((float)Math.PI/2).color("white").weight(1200).build();
        cAway = Car.builder().position(300, 300).rotation(0).color("white").weight(1200).build();
        world = new ArrayList<>();
        world.addAll(Arrays.asList(c0,c45,c90,cAway));
    }
    
    @Test
    public void getShapeTest(){
        
        assertEquals(1, c0.getPosition().getX(),0);
        assertEquals(1, c0.getPosition().getY(),0);
        
        assertEquals(5, c45.getPosition().getX(),0);
        assertEquals(10, c45.getPosition().getY(),0);
        
        assertEquals(10, c90.getPosition().getX(),0);
        assertEquals(10, c90.getPosition().getY(),0);
        
        assertTrue(c0.getShape().contains(108, 240));
        assertTrue(c45.getShape().contains(-100,200));
        assertTrue(c90.getShape().contains(-200,50));
        
    }
    
    @Test
    public void CollisionDetectionTest() {
        CollisionDetection cd = new CollisionDetection();
        
        assertTrue(cd.apply(c0.getShape(), c45.getShape()));
        assertFalse(cd.apply(cAway.getShape(), c45.getShape()));
        
    }
    
    @Test
    public void CameraTest() {
        Rectangle2D camera = new Rectangle2D.Double(50,100,30,30);
        ObjectDetector od = new ObjectDetector(world, new CollisionDetection());
        List<ICameraSensor> list = od.getCameraObjects(camera);
        assertEquals(2, list.size());
    }

}
