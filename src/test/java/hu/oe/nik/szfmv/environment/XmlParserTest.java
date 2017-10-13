package hu.oe.nik.szfmv.environment;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {

    @Test
    public void testParse() throws Exception {
        List<XmlObject> r = XmlParser.parse("test_world.xml");
        assertEquals(r.size(), 46);
        assertEquals(r.get(0).getX(), 1700);

    }
}
