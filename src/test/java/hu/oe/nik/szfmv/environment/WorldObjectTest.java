package hu.oe.nik.szfmv.environment;

import org.junit.Test;

import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.IDriveable;

public class WorldObjectTest {

	private IDriveable worldObject;

	@org.junit.Before
	public void setUp() throws Exception {
		/* stuff written here runs before the tests */
		worldObject = Car.builder().position(10, 10).rotation(0).dimension(100, 100).weight(1000).color("black")
				.build();
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
