package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class PedestrianTest {
    private Pedestrian pd;

    @org.junit.Before
    public void setUp() throws Exception {

        pd = new Pedestrian(10,10,new Vector2D(15,10),new Vector2D(10,10),null);
    }

    @Test
    public void TestCase() throws Exception
    {
         this.pd.move();
         assertEquals(pd.getX(),11);
    }
}
