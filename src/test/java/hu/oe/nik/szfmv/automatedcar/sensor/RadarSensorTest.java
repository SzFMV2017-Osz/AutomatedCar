package hu.oe.nik.szfmv.automatedcar.sensor;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.detector.RadarSensor;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Road;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Ignore;

/**
 *
 * @author szum7
 */
public class RadarSensorTest {
    
    private static final AutomatedCar automatedCar;
    private static ArrayList<WorldObject> worldObjects;
    
    static {
        automatedCar = new AutomatedCar(0, 0, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME), (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);
        worldObjects = new ArrayList<>();
    }

    @org.junit.Test
    @Ignore
    public void testWorldObjectsInRangeArray() {
        
        // set car position
        automatedCar.setPosition(new Vector2D(1000, 1000));
        
        // inner properties
        int radRefX = (int)automatedCar.getX();
        int radRefY = (int)automatedCar.getY() - ((automatedCar.getHeight() / 2));
        
        // add world objects
        worldObjects = new ArrayList<>();
        worldObjects.add(new Road(radRefX, radRefY - 30, 0f, XmlObjectType.ROAD_6_LEFT, false)); // out
        worldObjects.add(new Tree(radRefX, radRefY - 20, 0f, "tree.png", 50)); // in
        worldObjects.add(new Car(radRefX, radRefY + 30, 0f, "car_1_blue.png", 50)); // out
        worldObjects.add(new Tree(radRefX, radRefY - 30, 0f, "tree.png", 50)); // in
        worldObjects.add(new Tree(radRefX + 5, radRefY - 50, 0f, "tree.png", 50)); // in
        worldObjects.add(new Car(radRefX + 10, radRefY, 0f, "car_1_blue.png", 50)); // out
        
        // init RadarSensor
        RadarSensor radar = new RadarSensor(automatedCar, worldObjects);
        
        // test
        Assert.assertEquals(3, radar.getWorldObjectsInRange().size(), 0);
    }

    @org.junit.Test
    @Ignore
    public void testClosestWorldObjectsInRange() {
        
        // set car position
        automatedCar.setPosition(new Vector2D(1000, 1000));
        
        // inner properties
        int radRefX = (int)automatedCar.getX();
        int radRefY = (int)automatedCar.getY() - ((automatedCar.getHeight() / 2));
        
        // add world objects
        worldObjects = new ArrayList<>();
        WorldObject closestObject = new Tree(radRefX, radRefY + 20, 0f, "tree.png", 50);
        worldObjects.add(closestObject);
        worldObjects.add(new Tree(radRefX, radRefY + 30, 0f, "tree.png", 50));
        worldObjects.add(new Car(radRefX, radRefY + 40, 0f, "car_1_blue.png", 50));
        
        // init RadarSensor
        RadarSensor radar = new RadarSensor(automatedCar, worldObjects);
        
        // test
        Assert.assertEquals(closestObject, radar.getClosestWorldObjectsInRange().get(0));
    }

}
