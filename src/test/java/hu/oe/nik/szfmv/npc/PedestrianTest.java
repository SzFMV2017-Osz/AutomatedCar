package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PedestrianTest {
    public static final Vector2D POSITION_01 = new Vector2D(10, 10);
    public static final Vector2D POSITION_02 = new Vector2D(25, -12);
    public static final double MASS = 70;
    public static final double MAX_SPEED = 3;
    public static final double MAX_TURN_ANGLE = 360;
    public static final int ITERATION_LIMIT = 100;

    private Pedestrian pedestrian;

    @org.junit.Before
    public void setUp() throws Exception {
        pedestrian = new Pedestrian((int) POSITION_01.getX(), (int) POSITION_01.getY(), (float) POSITION_02.getAngle(), 20, 20, "imageFileName", 10, ModelShape.ELLIPSE);
    }

    @Test
    public void getPositionTest() {
        assertEquals(POSITION_01, pedestrian.getPosition());
    }

    @Test
    public void getLookDirectionTest() {
        Vector2D pos02_norm = new Vector2D(POSITION_02).normalize();
        assertEquals(pos02_norm, pedestrian.getForwardVector());
    }

    @Test
    public void gettersSettersTest() {
        pedestrian.setMass(MASS);
        pedestrian.setMaxSpeed(MAX_SPEED);
        pedestrian.setMaxTurnAngle(MAX_TURN_ANGLE);
        assertEquals(MASS, pedestrian.getMass());
        assertEquals(MAX_SPEED, pedestrian.getMaxSpeed());
        assertEquals(MAX_TURN_ANGLE, pedestrian.getMaxTurnAngle());
    }

    @Test
    public void moveTestCase() throws Exception {
        pedestrian.setMaxSpeed(2);
        int i = 0;
        while (!pedestrian.getPosition().equals(POSITION_02) && ITERATION_LIMIT > i) {
            System.out.println("Current position: " + pedestrian.getPosition().toString() +
                    ", target: " + POSITION_02.toString() +
                    ", movement angle: " + pedestrian.getVelocity().getAngle() +
                    ", movement speed: " + pedestrian.getVelocity().abs());
            pedestrian.move(POSITION_02);
            i++;
        }
        assertEquals(POSITION_02, pedestrian.getPosition());
    }
}
