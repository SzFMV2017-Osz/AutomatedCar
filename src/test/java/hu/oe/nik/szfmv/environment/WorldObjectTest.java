package hu.oe.nik.szfmv.environment;

import org.junit.Test;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.IDriveable;
import hu.oe.nik.szfmv.environment.util.ImageNameProperty;

import static junit.framework.Assert.assertEquals;

public class WorldObjectTest {

    private IDriveable worldObject;

    @org.junit.Before
    public void setUp() throws Exception {
        /* stuff written here runs before the tests */
        worldObject = new Car(0, 0, 0, 10, 10, ImageNameProperty.WHITE_CAR_NAME, 100);
    }

    @Test
    public void testXGetterSetter() {

    }

    @Test
    public void testYGetterSetter() {
    }

    @Test
    public void testRotationGetterSetter() {
    }

    @Test
    public void testWidthGetterSetter() {
    }

    @Test
    public void testHeightGetterSetter() {
    }

    @Test
    public void testImageFileNameGetterSetter() {
    }
}
