package hu.oe.nik.szfmv.environment;

import org.junit.Test;

import hu.oe.nik.szfmv.environment.xml.Utils;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;
import hu.oe.nik.szfmv.environment.xml.XmlParser;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {

    @Test
    public void testParse() throws Exception {
	List<XmlObject> r = XmlParser.parse("test_world.xml");
	assertEquals(46, r.size());
	assertEquals(XmlObjectType.ROAD_STRAIGHT, r.get(0).getType());
	assertEquals(1700, r.get(0).getX(), 0);
	assertEquals(144, r.get(0).getY(), 0);
	double[][] matrix = {{0, 1}, {-1, 0}};
	assertEquals(Utils.convertMatrixToRadians(matrix), r.get(0).getRotation(), 3);
    }
}
