package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.object.*;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class NPCCarTest {

    public static final Vector2D POSITION_01 = new Vector2D(200, 200);
    public static final Vector2D POSITION_02 = new Vector2D(25, -12);
    public static final double MASS = 1300;
    public static final double MAX_SPEED = 50;
    public static final double MAX_TURN_ANGLE = 360;
    public static final int ITERATION_LIMIT = 100;

    private NPCCar car;

    @org.junit.Before
    public void setUp() throws Exception {
        car = new NPCCar((int) POSITION_01.getX(), (int) POSITION_01.getY(), (float) POSITION_02.getAngle(), 10, 10, "", 1);
    }

    @Test
    public void getPositionTest() {
        assertEquals(POSITION_01, car.getPosition());
    }

    @Test
    public void getLookDirectionTest() {
        Vector2D pos02_norm = POSITION_02.copy().normalize();
        assertEquals(pos02_norm, car.getForwardVector());
    }

    @Test
    public void gettersSettersTest() {
        car.setMaxTurnAngle(MAX_TURN_ANGLE);
        car.setMaxSpeed(MAX_SPEED);
        car.setMass(MASS);
        assertEquals(MAX_TURN_ANGLE, car.getMaxTurnAngle());
        assertEquals(MAX_SPEED, car.getMaxSpeed());
        assertEquals(MASS, car.getMass());
    }
    
    @Test
    public void moveTestCase() {
    }
}
