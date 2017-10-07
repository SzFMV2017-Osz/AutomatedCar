package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Driver;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;

    public static void main(String[] args) {
        CourseDisplay vis = new CourseDisplay();

        // create the world
        World w = new World(800, 600);
        // create an automated car
        AutomatedCar car = new AutomatedCar(340, 0, "car_2_white.png");
        // place a driver into the car for demonstrating the signal sending mechanism
        Driver testDriver = new Driver();
        // add car to the world
        w.addObjectToWorld(car);
        // init visualization module with the world
        vis.init(w);
        // Test drive mode 
        testDriver.runTestDrive();
        // Enable circular test track
        car.initTestmode();
        while (true) {
            try {
                car.drive();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
