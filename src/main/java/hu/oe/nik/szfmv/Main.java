package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.environment.detector.WindscreenCamera;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.model.WorldObjectCollection;
import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.factory.SensorObjectFactory;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.factory.WorldObjectFactory;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.object.Sensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
//import hu.oe.nik.szfmv.npc.*;
import hu.oe.nik.szfmv.environment.object.*;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import hu.oe.nik.szfmv.environment.model.WorldObject;

import java.util.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    private static final int CYCLE_PERIOD = 40;
    private static CourseDisplay userInterFace;
    private static AutomatedCar playerCar;

	public static void main(String[] args) {
		init();

        mainLoop();
    }

    private static void init() {
        userInterFace = new CourseDisplay();

        // create the world
        List<XmlObject> xmlObjects = readXmlObjects();
        // create the world
        //TODO: get this from xml
        //World w = new World(5120, 3000);

        World world = new World(XmlParser.getWorldDimensions()[0], XmlParser.getWorldDimensions()[1]);

        populateWorld(xmlObjects, world);

        userInterFace.init(world);

        playerCar = new AutomatedCar(2500, 1500, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME),
                (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);




        //add WindscreenCamera to the world
        WindscreenCamera windscreenCamera = new WindscreenCamera(playerCar, world.getWorldObjects());

        world.addObjectToWorld(playerCar);

        addSensorsToWorld(playerCar, world);

        // create an automated car
        Car car = Car.builder()
                .position(1000, 1000)
                .rotation(0)
                .dimension(100, 100)
                .weight(1000).color("black").build();
        // add car to the world
        //!ONLY FOR TESTING!
//        testInitFromXml(w);
        Pedestrian p = new Pedestrian(1555, 486, 0, 5, 5, "man.png", 80);
        List<Vector2D> path = new ArrayList<>();
        path.add(new Vector2D(1555,80));
        path.add(new Vector2D(1555,450));
        PedestrianController pc = null;
        try {
             pc = new PedestrianController(p, path);
        } catch (Exception e){

        }
        NPCCar nc = new NPCCar(3720,1500, 0, 10,10, "car_1_white.png",2000);
        System.out.println(nc.getForwardVector());
        NPCCarController ncc = null;
        try {
            ncc = new NPCCarController(nc,world);
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        world.addObjectToWorld(p);
        world.addObjectToWorld(car);
        world.addObjectToWorld(nc);
        car.accelerate(-25);
        // init visualisation module with the world
        //vis.init(w);
    }

    private static void addSensorsToWorld(AutomatedCar playerCar, World world) {
        List<Sensor> sensors = SensorObjectFactory.createAllSensor(playerCar);

        for (Sensor item : sensors) {
            world.addObjectToWorld(item);
        }
    }

    private static void mainLoop() {
        while (true) {
            try {
                playerCar.drive();
                //car.move();
//                if (pc != null)
//                pc.tick();
//                if(nc != null)
//                ncc.tick();
                userInterFace.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private static void populateWorld(List<XmlObject> xmlObjects, World world) {
        for (XmlObject item : xmlObjects) {
            try {
                world.addObjectToWorld(WorldObjectFactory.createWorldObject(item));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<XmlObject> readXmlObjects() {
        List<XmlObject> xmlObjects = new ArrayList<>();
        try {
            xmlObjects = XmlParser.parse("test_world.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return xmlObjects;
    }
//        while (true) {
//            try {
//                //car.move();
//                if (pc != null)
//                pc.tick();
//                if(nc != null)
//                ncc.tick();
//                vis.refreshFrame();
//                Thread.sleep(CYCLE_PERIOD);
//            } catch (InterruptedException e) {
//                logger.error(e.getMessage());
//            }
//        }
//    }

    //!ONLY FOR TESTING!
//    private static void testInitFromXml(World w) {
//        logger.log(Level.WARN, "@Team1: fix this, WorldObject initialization method is only for testing");
//        List<XmlObject> xmlo = new ArrayList<>();
//        try {
//            xmlo = XmlParser.parse("test_world.xml");
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        try {
//            for (XmlObject item : xmlo) {
//                w.addObjectToWorld(
//                        new WorldObject(
//                                item.getX(),
//                                item.getY(),
//                                -(float) ((double) item.getRotation() / 180 * Math.PI),
//                                10,
//                                10,
//                                item.getType().getXmlName() + ".png",
//                                null));
//            }
//        } catch (Exception e) {
//        }
//    }
}
