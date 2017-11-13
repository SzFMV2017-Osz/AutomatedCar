package hu.oe.nik.szfmv.automatedcar.sensor;

import hu.oe.nik.szfmv.common.Vector2D;
import java.util.ArrayList;
import org.junit.Assert;

public class SensorTest {
    
    @org.junit.Test
    public void testRadarSensorVectorDistances(){
        ArrayList<Vector2D> points = new ArrayList<>();
        points.add(new Vector2D(60, 60));
        points.add(new Vector2D(40, 50));
        points.add(new Vector2D(20, 30));
        points.add(new Vector2D(70, 70));
        points.add(new Vector2D(10, 20)); // closest vector
        points.add(new Vector2D(50, 50));
        
        RadarSensor radarSensor = new RadarSensor();
        
        Vector2D referencePoint = new Vector2D(10, 10);        
        ArrayList<Vector2D> closests = radarSensor.getClosestVectors(points, referencePoint);
        
        Assert.assertEquals(10, closests.get(0).getX(), 0);
        Assert.assertEquals(20, closests.get(0).getY(), 0);
    }
    
}
