package hu.oe.nik.szfmv.environment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hu.oe.nik.szfmv.environment.xml.Utils;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

public class XmlObjectTest {

    @Test
    public void buildOkTest() {
        XmlObject o = XmlObject.builder().type("road_2lane_straight").position(10, 100).rotation(0, 1, -1, 0).build();
        assertEquals(XmlObjectType.ROAD_STRAIGHT, o.getType());
        assertEquals(10, o.getX(), 0);
        assertEquals(100, o.getY(), 0);
        double rotation = Utils.radianToDegree(Utils.convertMatrixToRadians(0, 1, -1, 0));
        assertEquals(rotation, o.getRotation(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildNotExistTest() {
    		@SuppressWarnings("unused")
        XmlObject o = XmlObject.builder().type("nem_letezik").position(10, 100).rotation(0, 1, -1, 0).build();
    }

	@Test(expected = IllegalArgumentException.class)
    public void buildNotCompleteTest() {
		@SuppressWarnings("unused")
        XmlObject o = XmlObject.builder().type("road_2lane_straight").rotation(0, 1, -1, 0).build();
    }
}
