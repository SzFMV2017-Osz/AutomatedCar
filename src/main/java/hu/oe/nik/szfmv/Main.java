package hu.oe.nik.szfmv;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Driver;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;

public class Main {

	private static final Logger logger = LogManager.getLogger();

	private static final int CYCLE_PERIOD = 40;

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

		Car car = Car.builder().position(500, 500).rotation(0).dimension(100, 100).weight(1000).color("black").build();
		AutomatedCar playerCar = new AutomatedCar(2560, 1500, Math.PI / 2, 102, 208, "car_2_white.png",
				ModelShape.RECTANGULAR);
		// add Car to the world
		w.addObjectToWorld(car);
		car.accelerate(-5);

		// add Car to the world
		w.addObjectToWorld(playerCar);
		while (true) {
			try {
				car.move();
				playerCar.drive();
				vis.refreshFrame();
				Thread.sleep(CYCLE_PERIOD);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
	}

	// !ONLY FOR TESTING!
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
				w.addObjectToWorld(new WorldObject(item.getX(), item.getY(),
						-(float) ((double) item.getRotation() / 180 * Math.PI), 10, 10,
						item.getType().getXmlName() + ".png", null));
			}
		} catch (Exception e) {
		}
	}
}
