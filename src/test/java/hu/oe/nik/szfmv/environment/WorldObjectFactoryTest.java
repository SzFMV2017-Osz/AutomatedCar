package hu.oe.nik.szfmv.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import hu.oe.nik.szfmv.environment.object.Road;
import hu.oe.nik.szfmv.environment.util.RoadType;
import org.junit.Before;
import org.junit.Test;

import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.factory.WorldObjectFactory;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.RoadSign;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.xml.XmlObject;

public class WorldObjectFactoryTest {

    XmlObject tree;
    XmlObject road;
    XmlObject roadSign;

    @Before
    public void initTest() {
        tree = XmlObject.builder().type("tree").position(304, 2632).rotation(1, 0, 0, 1).build();
        road = XmlObject.builder().type("road_2lane_90right").position(304, 2632).rotation(1, 0, -1, 0).build();
        roadSign = XmlObject.builder().type("roadsign_priority_stop").position(304, 2632).rotation(1, 0, 0, 1).build();
    }

    @Test
    public void createTreeTest() throws IOException {
        WorldObject o = WorldObjectFactory.createWorldObject(tree);

        assertTrue(o instanceof Tree);
        Tree t = (Tree) o;
        assertEquals(ImageResource.getHeight("tree.png"), t.getHeight());
        assertEquals(ImageResource.getWidth("tree.png"), t.getWidth());
        assertEquals(tree.getRotation(), t.getRotation(), 0);
        assertEquals(Integer.MAX_VALUE, t.getWeight());
        assertEquals("tree.png", t.getImageFileName());
    }

    @Test
    public void createRoadTest() throws IOException {
        WorldObject o = WorldObjectFactory.createWorldObject(road);

        assertTrue(o instanceof Road);
        Road r = (Road) o;
        assertEquals(ImageResource.getHeight("road_2lane_90right.png"), r.getHeight());
        assertEquals(ImageResource.getWidth("road_2lane_90right.png"), r.getWidth());
        assertEquals(road.getRotation(), r.getRotation(), 0);
        assertEquals("road_2lane_90right.png", r.getImageFileName());
        assertEquals(90, r.getCurvature());
        assertEquals(RoadType.RIGHT, r.getRoadType());
    }

    @Test
    public void createRoadSign() throws IOException {
        WorldObject o = WorldObjectFactory.createWorldObject(roadSign);

        assertTrue(o instanceof RoadSign);
        RoadSign s = (RoadSign) o;
        assertEquals(ImageResource.getHeight("roadsign_priority_stop.png"), s.getHeight());
        assertEquals(ImageResource.getWidth("roadsign_priority_stop.png"), s.getWidth());
        assertEquals(tree.getRotation(), s.getRotation(), 0);
        assertEquals(Integer.MAX_VALUE, s.getWeight());
        assertEquals("roadsign_priority_stop.png", o.getImageFileName());
    }
}
