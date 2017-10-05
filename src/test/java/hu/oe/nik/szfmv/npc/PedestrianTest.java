package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class PedestrianTest {
    private static final int MOVE_TEST_X_RESULT = 11;
    private static final int START_COORD_Y = 10;
    private static final int START_COORD_X = 10;
    private static final Vector2D TEST_DESTINATION_1 = new Vector2D(15, 10);
    private static final Vector2D TEST_DESTINATION_2 = new Vector2D(10, 10);

    private Pedestrian pd;

    @org.junit.Before
    public void setUp() throws Exception {
        pd = new Pedestrian(START_COORD_X, START_COORD_Y, TEST_DESTINATION_1, TEST_DESTINATION_2, null);
    }

    @Test
    public void moveTestCase() throws Exception {
        this.pd.move();
        assertEquals(pd.getX(), MOVE_TEST_X_RESULT);
    }
}
