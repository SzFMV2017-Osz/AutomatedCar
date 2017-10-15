package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static final Logger logger = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 100;

	public static void main(String[] args) {
		CourseDisplay vis = new CourseDisplay();

		// create the world
        //TODO: get this from xml
        World w = new World(5120, 3000);
		// create an automated car
        Car car = Car.builder()
                .position(500, 500)
                .rotation(0)
                .dimension(100, 100)
                .weight(1000).color("black").build();
        // add car to the world

        //!ONLY FOR TESTING!
        testInitFromXml(w);


		w.addObjectToWorld(car);
        car.accelerate(-25);
        // init visualisation module with the world
		vis.init(w);

		while (true) {
			try {
				car.move();
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
