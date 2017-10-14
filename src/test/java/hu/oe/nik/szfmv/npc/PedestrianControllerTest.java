package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class PedestrianControllerTest {

    public static final int PEDESTRIAN_LOCATION_X = 10;
    public static final int PEDESTRIAN_LOCATION_Y = 10;
    public static final Vector2D PEDESTRIAN_TARGET_ONE = new Vector2D(10, 10);
    public static final Vector2D PEDESTRIAN_TARGET_TWO = new Vector2D(15, 20);

    private PedestrianController pedestrianController;
    private Pedestrian pedestrian;
    private List<Vector2D> pathPoints;

    @org.junit.Before
    public void setUp() throws Exception {
        pedestrian = new Pedestrian(PEDESTRIAN_LOCATION_X, PEDESTRIAN_LOCATION_Y, "imageFileName");
        pathPoints = new ArrayList<>();
        pathPoints.add(PEDESTRIAN_TARGET_ONE);
        pathPoints.add(PEDESTRIAN_TARGET_TWO);
        pedestrianController = new PedestrianController(pedestrian, pathPoints);
    }

    @Test
    public void pedestrianPathListTest() throws Exception {
        assertEquals(pedestrianController.getCurrentTarget(), pathPoints.get(0));
        pedestrianController.nextTarget();
        assertEquals(pedestrianController.getCurrentTarget(), pathPoints.get(1));
        pedestrianController.nextTarget();
        assertEquals(pedestrianController.getCurrentTarget(), pathPoints.get(0));
    }

    @Test
    public void pedestrianMovableObjectTest() throws Exception {
        IMovable pedestrianNull = null;
        PedestrianController pedestrianControllerNull = new PedestrianController(pedestrianNull, pathPoints);
        assertEquals(pedestrianControllerNull.getMovableObject(), null);
        pedestrianControllerNull.setMovableObject(pedestrian);
        assertEquals(pedestrianControllerNull.getMovableObject(), pedestrian);
    }

    @Test(expected = NullPointerException.class)
    public void pedestrianTickMoveNullTest() throws Exception {
        pedestrian = null;
        pedestrianController = new PedestrianController(pedestrian, pathPoints);
        pedestrianController.tick();
    }

    @Test
    public void pedestrianNextTarget() throws Exception {
        assertEquals(true, pedestrianController.isAtCurrentTarget());
        pedestrianController.nextTarget();
        assertEquals(false, pedestrianController.isAtCurrentTarget());
    }

    @Test
    public void pedestrianTickMoveNextTargetTest() throws Exception {
        assertEquals(pedestrian.getPosition(), pedestrianController.getCurrentTarget());
        pedestrianController.tick();
        assertEquals(pedestrianController.getCurrentTarget(), pathPoints.get(1));
    }

}
