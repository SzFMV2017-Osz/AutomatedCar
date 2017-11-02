package hu.oe.nik.szfmv.environment;

import static org.junit.Assert.*;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.oe.nik.szfmv.environment.detector.ICameraSensor;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;
import hu.oe.nik.szfmv.environment.detector.ISensor;
import hu.oe.nik.szfmv.environment.detector.ObjectDetector;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Pedestrian;
import hu.oe.nik.szfmv.environment.object.Road;
import hu.oe.nik.szfmv.environment.object.RoadSign;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.RoadSignType;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

public class ObjectDetectorTest {

	public BiFunction<Shape, Shape, Boolean> all = (a, b) -> true;
	public BiFunction<Shape, Shape, Boolean> none = (a, b) -> false;

	int counter = 0;
	public BiFunction<Shape, Shape, Boolean> every2 = (a, b) -> counter++ % 2 == 0 ? true : false;

	List<WorldObject> list;

	Ellipse2D triangle = new Ellipse2D.Double();

	@Before
	public void createList() {
		list = new ArrayList<>();
		list.add(new Road(0, 0, 0, 0, 0, XmlObjectType.ROAD_45_LEFT, false));
		list.add(new Road(0, 0, 0, 0, 0, XmlObjectType.ROAD_90_RIGHT, false));
		list.add(new RoadSign(0, 0, 0, 0, 0, ImageResource.ROADSIGN_SPEED_50_NAME, 0, RoadSignType.PARKING_RIGHT));
		list.add(new RoadSign(0, 0, 0, 0, 0, ImageResource.ROADSIGN_STOP_NAME, 0, RoadSignType.PARKING_RIGHT));
		list.add(new Tree(0, 0, 0, 0, 0, 0, ImageResource.TREE_NAME));
		list.add(new Tree(0, 0, 0, 0, 0, 0, ImageResource.TREE_NAME));
		list.add(Car.builder().color("red").dimension(0, 0).position(0, 0).rotation(0).weight(0).build());
		list.add(Car.builder().color("white").dimension(0, 0).position(0, 0).rotation(0).weight(0).build());

		counter = 0;
	}

	@Test
	public void BiFunctions() {
		Ellipse2D t = new Ellipse2D.Double();
		Ellipse2D u = new Ellipse2D.Double();
		assertTrue(all.apply(t, u));
		assertFalse(none.apply(t, u));

		// assertFalse(every2.apply(t, u));
		// assertTrue(every2.apply(t, u));
	}

	@Test
	public void getAll() {
		ObjectDetector detector = new ObjectDetector(list, all);

		List<ISensor> sonarable = detector.getSonarObjects(triangle);
		assertEquals("number of sonar detectables should be 6", 6, sonarable.size());

		List<ICameraSensor> camerable = detector.getCameraObjects(triangle);
		assertEquals("number of camera detectables should be 8", 8, camerable.size());

		List<IRadarSensor> radarable = detector.getRadarObjects(triangle);
		assertEquals("number of radarable detectables should be 2", 2, radarable.size());
	}

	@Test
	public void getNone() {
		ObjectDetector detector = new ObjectDetector(list, none);

		List<ISensor> sonarable = detector.getSonarObjects(triangle);
		assertEquals("number of sonar detectables should be 0", 0, sonarable.size());

		List<ICameraSensor> camerable = detector.getCameraObjects(triangle);
		assertEquals("number of camera detectables should be 0", 0, camerable.size());

		List<IRadarSensor> radarable = detector.getRadarObjects(triangle);
		assertEquals("number of radarable detectables should be 0", 0, radarable.size());
	}

	@Test
	public void getEvery2() {
		ObjectDetector detector = new ObjectDetector(list, every2);

		counter = 0;
		List<ISensor> sonarable = detector.getSonarObjects(triangle);
		assertEquals("number of sonar detectables should be 3", 3, sonarable.size());

		counter = 0;
		List<ICameraSensor> camerable = detector.getCameraObjects(triangle);
		assertEquals("number of camera detectables should be 4", 4, camerable.size());

		counter = 0;
		List<IRadarSensor> radarable = detector.getRadarObjects(triangle);
		assertEquals("number of radarable detectables should be 1", 1, radarable.size());
	}

}
