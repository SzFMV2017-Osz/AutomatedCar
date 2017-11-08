package hu.oe.nik.szfmv;

import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv.environment.detector.WindscreenCamera;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.model.WorldObjectCollection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.model.World;
//import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    private static final int CYCLE_PERIOD = 40;
    private static int count = 0;

    public static void main(String[] args) {
        CourseDisplay vis = new CourseDisplay();

        // create the world
        // TODO: get this from xml
        World w = new World(5120, 3000);
        // create an automated car

        // !ONLY FOR TESTING!
        testInitFromXml(w);

        // init visualisation module with the world
        vis.init(w);

        Car car = Car.builder().position(500, 500).rotation(((float)Math.PI / 2)).weight(1000).color("black").build();
        AutomatedCar playerCar = new AutomatedCar(2560, 1500, Math.PI / 2, 102, 208, "car_2_white.png",
                ModelShape.RECTENGULAR);



        // add Car to the world
        w.addObjectToWorld(car);
        car.accelerate(1);

        //add WindscreenCamera to the world
        WindscreenCamera windscreenCamera = new WindscreenCamera(playerCar, w.getWorldObjects());

        // add Car to the world
        w.addObjectToWorld(playerCar);
        while (true) {
            try {
                car.move();
                playerCar.drive();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
                count++;
                if (count % 25 == 0 && count < 50) {
                    car.accelerate(1.0000001);
                }
                if (count % 20 == 0) {
                    car.turn(10);
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    // !ONLY FOR TESTING!
    @SuppressWarnings("unused")
    private static void testInitFromXml(World w) {
        logger.log(Level.WARN, "@Team1: fix this, WorldObject initialization method is only for testing");
        List<XmlObject> xmlo = new ArrayList<>();
        try {
            xmlo = XmlParser.parse("test_world.xml");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        try {
            // for (XmlObject item : xmlo) {
            // w.addObjectToWorld(new WorldObject(item.getX(), item.getY(),
            // -(float) ((double) item.getRotation() / 180 * Math.PI), 10,
            // 10,
            // item.getType().getXmlName() + ".png", null));
            // }
        } catch (Exception e) {
        }
    }
}
