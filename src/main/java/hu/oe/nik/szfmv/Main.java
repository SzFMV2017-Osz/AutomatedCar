package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static final Logger logger = LogManager.getLogger();
	private static final int CYCLE_PERIOD = 200;

	public static void main(String[] args) {
		CourseDisplay vis = new CourseDisplay();

		// create the world
		World w = new World(5120, 3000);
		// create an automated car
		Car car = Car.builder().position(500, 500).rotation(0).dimension(100, 100).weight(1000).color("black").build();
		// add car to the world
		w.addObjectToWorld(car);
		car.accelerate(-5);
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
}
