package hu.oe.nik.szfmv.environment;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WorldObjectTest {

    private WorldObject worldObject;

    @org.junit.Before
    public void setUp() throws Exception {
        /* stuff written here runs before the tests */
        worldObject = new WorldObject(21, 42, "test.jpg");
    }

    @Test
    public void testXGetterSetter() {
        assertEquals(worldObject.getX(), 21);
        worldObject.setX(42);
        assertEquals(worldObject.getX(), 42);
    }

    @Test
    public void testYGetterSetter() {
        assertEquals(worldObject.getY(), 42);
        worldObject.setY(84);
        assertEquals(worldObject.getY(), 84);
    }

    @Test
    public void testRotationGetterSetter() {
        assertEquals(worldObject.getRotation(), 0f);
        worldObject.setRotation(84f);
        assertEquals(worldObject.getRotation(), 84f);
    }

    @Test
    public void testWidthGetterSetter() {
        assertEquals(worldObject.getWidth(), 0);
        worldObject.setWidth(120);
        assertEquals(worldObject.getWidth(), 120);
    }

    @Test
    public void testHeightGetterSetter() {
        assertEquals(worldObject.getHeight(), 0);
        worldObject.setHeight(60);
        assertEquals(worldObject.getHeight(), 60);
    }

    @Test
    public void testImageFileNameGetterSetter() {
        assertEquals(worldObject.getImageFileName(), "test.jpg");
        worldObject.setImageFileName("test.png");
        assertEquals(worldObject.getImageFileName(), "test.png");
    }
}