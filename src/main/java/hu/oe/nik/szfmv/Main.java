package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Driver;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 17;

    public static void main(String[] args) {

        // create the world        
        World w = new World(5120, 3000); //TODO: get this from xml
        
        // create course
        CourseDisplay vis = new CourseDisplay();
        
        // create an npc car
        Car car = Car.builder()
                .position(500, 500)
                .rotation(0)
                .dimension(100, 100)
                .weight(1000).color("black").build();

        //!ONLY FOR TESTING!
        testInitFromXml(w);

        w.addObjectToWorld(car);
        car.accelerate(-5);
        
        // Ego car
        int refreshRate = 25;
        Map<Integer, Integer> gasMap = new LinkedHashMap<Integer, Integer>();
        Map<Integer, Integer> breakMap = new LinkedHashMap<Integer, Integer>();
        Map<Integer, AutoTransmissionEnum> autoTMap = new LinkedHashMap<Integer, AutoTransmissionEnum>();
        
        autoTMap.put(1, AutoTransmissionEnum.D);
        breakMap.put(2, 0);
        gasMap.put(refreshRate, 20);
        breakMap.put(refreshRate * 48, 90);
        gasMap.put(refreshRate * 52, 0);
        
        AutomatedCar ego = new AutomatedCar(1000, 2000, 0, 102, 208, "car_2_white.png", ModelShape.RECTANGULAR);
        w.addObjectToWorld(ego);
        Driver driver = new Driver();
        driver.runTestDrive(breakMap, gasMap, autoTMap);
        //ego.initTestmode();
        
        // init visualisation module with the world
        vis.init(w);

        while (true) {
            try {
                
                // npc
                car.move();
                
                // ego car
                ego.drive();
                
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
                
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    //!ONLY FOR TESTING!
    private static void testInitFromXml(World w) {
        logger.log(Level.WARN, "@Team1: fix this, WorldObject initialization method is only for testing");
        List<XmlObject> xmlo = new ArrayList<>();
        try {
            xmlo = XmlParser.parse("test_world.xml");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        try {
            for (XmlObject item : xmlo) {
                w.addObjectToWorld(
                        new WorldObject(
                                item.getX(),
                                item.getY(),
                                -(float) ((double) item.getRotation() / 180 * Math.PI),
                                10,
                                10,
                                item.getType().getXmlName() + ".png",
                                null));
            }
        } catch (Exception e) {
        }
    }
}
