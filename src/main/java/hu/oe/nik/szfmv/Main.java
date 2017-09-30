package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.util.ImageNameProperty;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
    private static final Logger logger = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 200;

    public static void main(String[] args) {
        CourseDisplay vis = new CourseDisplay();

        // create the world
        World w = new World(800, 600);
        // create an automated car
        Car car = new Car(0, 0, 0, 10, 10, ImageNameProperty.WHITE_CAR_NAME, 100);
        // add car to the world
        w.addObjectToWorld(car);
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
