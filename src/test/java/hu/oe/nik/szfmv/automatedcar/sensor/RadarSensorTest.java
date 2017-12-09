package hu.oe.nik.szfmv.automatedcar.sensor;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Ignore;

/**
 *
 * @author szum7
 */
public class RadarSensorTest {
    
    private static final AutomatedCar automatedCar;
    
    static {
        automatedCar = new AutomatedCar(0, 0, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME), (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);
    }

    @org.junit.Test
    public void testRadarSensorVectorDistances() {
        ArrayList<Vector2D> points = new ArrayList<>();
        points.add(new Vector2D(60, 60));
        points.add(new Vector2D(40, 50));
        points.add(new Vector2D(20, 30));
        points.add(new Vector2D(70, 70));
        points.add(new Vector2D(10, 20)); // closest vector
        points.add(new Vector2D(50, 50));
        
        automatedCar.getRadarSensor().updatePoints();
        
        ArrayList<Vector2D> closests = automatedCar.getRadarSensor().getClosestVectors(points);

        Assert.assertEquals(10, closests.get(0).getX(), 0);
        Assert.assertEquals(20, closests.get(0).getY(), 0);
    }

    @org.junit.Test
    @Ignore
    public void testRadarSensorVectorDistancesInRange() {
        
        double carHeight = automatedCar.getHeight();
        
        ArrayList<Vector2D> points = new ArrayList<>();
        
        points.add(new Vector2D(1000, 900));
//        points.add(new Vector2D(1000, 1000 - (carHeight / 2) + 2)); // not in range
//        points.add(new Vector2D(200, 200));
//        points.add(new Vector2D(1000, 1000 - (carHeight / 2) - 30)); // in range, but farther
//        points.add(new Vector2D(100, 100));
//        points.add(new Vector2D(1000, 1000 - (carHeight / 2) - 20)); // closest
//        points.add(new Vector2D(50, 50));
//        points.add(new Vector2D(1000, 1000 - (carHeight / 2) + 5)); // not in range
        
        automatedCar.setPosition(new Vector2D(1000, 1000));
        
        automatedCar.getRadarSensor().updatePoints();
        
        ArrayList<Vector2D> closests = automatedCar.getRadarSensor().getClosestVectorsInRange(points);

        Assert.assertEquals(1000, closests.get(0).getX(), 0);
        Assert.assertEquals(900, closests.get(0).getY(), 0);
    }

}
