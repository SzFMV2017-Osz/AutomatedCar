package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.npc.AIController;
import hu.oe.nik.szfmv.npc.NPCCar;
import hu.oe.nik.szfmv.npc.NPCCarController;
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
        // AutomatedCar car = new AutomatedCar(20, 20, "car_2_white.png");
        // add car to the world
        // w.addObjectToWorld(car);

        // testing NPCCar
        AIController npcCarController = new NPCCarController();
        NPCCar npcCar = new NPCCar(500, 500, "car_2_white.png");
        npcCarController.setMovableObject(npcCar);
        npcCar.setRotation(90);
        w.addObjectToWorld(npcCar);
        //

        // init visualisation module with the world
        vis.init(w);

        while (true) {
            try {
//                car.drive();
                npcCarController.tick();
                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
