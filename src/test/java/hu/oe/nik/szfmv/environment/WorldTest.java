package hu.oe.nik.szfmv.environment;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import hu.oe.nik.szfmv.environment.factory.ImageNameProperty;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.object.Car;

public class WorldTest {

	private World world;

	@org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
		world = new World(800, 600);
	}

	@Test
	public void testWidthGetterSetter() throws Exception {
		assertEquals(world.getWidth(), 800);
		world.setWidth(1000);
		assertEquals(world.getWidth(), 1000);
	}

	@Test
	public void testHeightGetterSetter() throws Exception {
		assertEquals(world.getHeight(), 600);
		world.setHeight(800);
		assertEquals(world.getHeight(), 800);
	}

	@Test
	public void getWorldObjectsTest() throws Exception {
		assertEquals(world.getWorldObjects().size(), 0);
		world.addObjectToWorld(new Car(0, 0, 0, 10, 10, ImageNameProperty.WHITE_CAR_NAME, 100));
		assertEquals(world.getWorldObjects().size(), 1);
	}

	@Test
	public void addObjectToWorldTest() throws Exception {
		world.addObjectToWorld(new Car(100, 100, 0, 10, 10, ImageNameProperty.WHITE_CAR_NAME, 100));
		assertEquals(world.getWorldObjects().size(), 1);
		assertEquals(world.getWorldObjects().get(0).getX(), 100);
		assertEquals(world.getWorldObjects().get(0).getY(), 100);
		assertEquals(world.getWorldObjects().get(0).getImageFileName(), "automatedCar.car.white");
	}

}
