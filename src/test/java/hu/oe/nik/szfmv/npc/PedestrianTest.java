package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class PedestrianTest {
    public static final Vector2D POSITION_01 = new Vector2D(100, 100);
    public static final Vector2D POSITION_02 = new Vector2D(250, -120);
//    public static final double MASS = 70;
    public static final double MAX_SPEED = 10;
//    public static final double MAX_TURN_ANGLE = 360;
    public static final int ITERATION_LIMIT = 100;

    private Pedestrian pedestrian;

    @org.junit.Before
    public void setUp() throws Exception {
        pedestrian = new Pedestrian((int) POSITION_01.getX(), (int) POSITION_01.getY(), (float) POSITION_02.getAngle(), 20, 20, "imageFileName", 10);
    }

    @Test
    public void getPositionTest() {
        assertEquals(POSITION_01, pedestrian.getPosition());
    }

    @Test
    public void getLookDirectionTest() {
        Vector2D pos02_norm = POSITION_02.copy().normalize();
        assertEquals(pos02_norm, pedestrian.getForwardVector());
    }

    @Test
    public void gettersSettersTest() {
        pedestrian.setMaxSpeed(MAX_SPEED);
        assertEquals(MAX_SPEED, pedestrian.getMaxSpeed());
    }

    @Test
    public void moveTestCase() throws Exception {
        pedestrian.setMaxSpeed(MAX_SPEED);
        int i = 0;
        while (!pedestrian.getPosition().equals(POSITION_02) && ITERATION_LIMIT > i) {
            System.out.println("Current position: " + pedestrian.getPosition().toString() +
                    ", target: " + POSITION_02.toString() +
                    ", movement angle: " + pedestrian.getCurrentSpeed().getAngle() +
                    ", movement speed: " + pedestrian.getCurrentSpeed().abs() +
                    ", distance: " + POSITION_02.copy().sub(pedestrian.getPosition()).abs());
            pedestrian.moveTo(POSITION_02);
            i++;
        }
        assertEquals(POSITION_02, pedestrian.getPosition());
    }
}
