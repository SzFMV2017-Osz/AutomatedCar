package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Driver;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;

import java.util.LinkedHashMap;
import java.util.Map;

@Ignore("ezt így nem lehet buildelni")
public class PowertrainSystemTest {

    private static final Logger logger = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;
    private static final int REFRESH_RATE = 25;
    private static final CourseDisplay vis;
    private static final World w;
    private static final AutomatedCar playerCar;
    private static final Driver driver;

    static {
        vis = new CourseDisplay();

        // create the world
        w = new World(800, 600);

        // create an automated car
        playerCar = new AutomatedCar(340, 0, 0, "car_2_white.png", ModelShape.RECTENGULAR);

        // place a driver into the car for demonstrating the signal sending
        // mechanism
        driver = new Driver();

        // init visualisation module with the world
        vis.init(w);

        // add playerCar to the world
        w.addObjectToWorld(playerCar);

        // Enable circular test track
        //playerCar.initTestmode();
    }

    private static final Map<Integer, Integer> gasPedalProgram = new LinkedHashMap<Integer, Integer>();
    private static final Map<Integer, Integer> brakePedalProgram = new LinkedHashMap<Integer, Integer>();
    private static final Map<Integer, AutoTransmissionEnum> autoTransmissionProgram = new LinkedHashMap<Integer, AutoTransmissionEnum>();

    @org.junit.Before
    public void carInitialConditions() {
        System.out.println("\n*** Stopping the car ***");
        clearAllPrograms();

        brakePedalProgram.put(1, 100);
        autoTransmissionProgram.put(2, AutoTransmissionEnum.N);

        // Test drive mode
        driver.runTestDrive(brakePedalProgram, gasPedalProgram, autoTransmissionProgram);

        while (driver.getLoopCounter() < elapsedSeconds(3)) {
            try {
                playerCar.drive();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        clearAllPrograms();
        System.out.println("");
    }

    @org.junit.Test
    public void forwardAccelerationAndBrakingTest() {
        System.out.println("*** Forward acceleration and braking test ***");

        // Forward acceleration and braking program
        autoTransmissionProgram.put(1, AutoTransmissionEnum.D);
        brakePedalProgram.put(2, 0);
        gasPedalProgram.put(elapsedSeconds(1), 100);
        brakePedalProgram.put(elapsedSeconds(48), 90);
        gasPedalProgram.put(elapsedSeconds(52), 0);

        // Test drive mode
        driver.runTestDrive(brakePedalProgram, gasPedalProgram, autoTransmissionProgram);

        while (driver.getLoopCounter() < elapsedSeconds(59)) {
            try {
                playerCar.drive();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @org.junit.Test
    public void reverseAccelerationAndBrakingTest() {
        System.out.println("*** Reverse acceleration and braking test ***");

        // Reverse acceleration program
        autoTransmissionProgram.put(1, AutoTransmissionEnum.R);
        brakePedalProgram.put(2, 0);
        gasPedalProgram.put(elapsedSeconds(1), 100);
        brakePedalProgram.put(elapsedSeconds(8), 90);
        gasPedalProgram.put(elapsedSeconds(10), 0);

        // Test drive mode
        driver.runTestDrive(brakePedalProgram, gasPedalProgram, autoTransmissionProgram);

        while (driver.getLoopCounter() < elapsedSeconds(15)) {
            try {
                playerCar.drive();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @org.junit.Test
    public void forwardAccelerationAndEngineBrakingTest() {
        System.out.println("*** Forward acceleration and engine braking test ***");

        // Forward acceleration and engine braking program
        autoTransmissionProgram.put(1, AutoTransmissionEnum.D);
        brakePedalProgram.put(2, 0);
        gasPedalProgram.put(elapsedSeconds(1), 100);
        gasPedalProgram.put(elapsedSeconds(48), 0);

        // Test drive mode
        driver.runTestDrive(brakePedalProgram, gasPedalProgram, autoTransmissionProgram);

        while (driver.getLoopCounter() < elapsedSeconds(138)) {
            try {
                playerCar.drive();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void clearAllPrograms() {
        brakePedalProgram.clear();
        gasPedalProgram.clear();
        autoTransmissionProgram.clear();
    }

    private int elapsedSeconds(int value) {
        return value * REFRESH_RATE;
    }

}
