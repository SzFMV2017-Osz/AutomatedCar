package hu.oe.nik.szfmv.environment;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {

    @Test
    public void testParse() throws Exception {
        List<XmlObject> r = XmlParser.parse("test_world.xml");
        assertEquals(46, r.size());
        assertEquals("road_2lane_straight", r.get(0).getType());
        assertEquals(1700, r.get(0).getX());
        assertEquals(144, r.get(0).getY());
        double[][] matrix = {{0, 1}, {-1, 0}};
        Assert.assertArrayEquals(matrix, r.get(0).getMatrix());

    }

    @Test
    public void testXmlSceneParse() throws Exception {
        XmlScene scene = XmlParser.parseAsXmlScene("test_world.xml");

        assertEquals(5120, scene.getWidth());
        assertEquals(3000, scene.getHeight());
        assertEquals(46, scene.getObjectList().size());
        assertEquals("road_2lane_straight", scene.getObjectList().get(0).getType());
        assertEquals(1700, scene.getObjectList().get(0).getX());
        assertEquals(144, scene.getObjectList().get(0).getY());
        double[][] matrix = {{0, 1}, {-1, 0}};
        Assert.assertArrayEquals(matrix, scene.getObjectList().get(0).getMatrix());

    }
}
