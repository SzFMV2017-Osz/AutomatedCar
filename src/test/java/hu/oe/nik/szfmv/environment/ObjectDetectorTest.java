package hu.oe.nik.szfmv.environment;

import static org.junit.Assert.*;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import hu.oe.nik.szfmv.environment.detector.*;
import org.junit.Before;
import org.junit.Test;

import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.detector.ICameraSensor;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;
import hu.oe.nik.szfmv.environment.detector.ISensor;
import hu.oe.nik.szfmv.environment.detector.ObjectDetector;
import hu.oe.nik.szfmv.environment.factory.WorldObjectFactory;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

public class ObjectDetectorTest {

	public BiFunction<Shape, Shape, Boolean> all = (a, b) -> true;
	public BiFunction<Shape, Shape, Boolean> none = (a, b) -> false;

	int counter = 0;
	public BiFunction<Shape, Shape, Boolean> every2 = (a, b) -> {
		incrementCounter();
		return counter % 2 == 0 ? true : false;
	};

	List<WorldObject> list;

	Ellipse2D triangle = new Ellipse2D.Double();

	@Before
	public void createList() throws IOException {
		list = new ArrayList<>();
		list.add(WorldObjectFactory.createWorldObject(XmlObject.builder().position(0, 0).rotation(0, 0, 0, 0)
				.type(XmlObjectType.ROAD_90_RIGHT.getXmlName()).build()));
		list.add(WorldObjectFactory.createWorldObject(XmlObject.builder().position(0, 0).rotation(0, 0, 0, 0)
				.type(XmlObjectType.ROAD_45_LEFT.getXmlName()).build()));
		list.add(WorldObjectFactory.createWorldObject(XmlObject.builder().position(0, 0).rotation(0, 0, 0, 0)
				.type(XmlObjectType.ROADSIGN_STOP.getXmlName()).build()));
		list.add(WorldObjectFactory.createWorldObject(XmlObject.builder().position(0, 0).rotation(0, 0, 0, 0)
				.type(XmlObjectType.ROADSIGN_SPEED_40.getXmlName()).build()));
		list.add(WorldObjectFactory.createWorldObject(
				XmlObject.builder().position(0, 0).rotation(0, 0, 0, 0).type(XmlObjectType.TREE.getXmlName()).build()));
		list.add(WorldObjectFactory.createWorldObject(
				XmlObject.builder().position(1, 1).rotation(0, 0, 0, 0).type(XmlObjectType.TREE.getXmlName()).build()));
		list.add(Car.builder().color("red").position(0, 0).rotation(0).weight(0).build());
		list.add(Car.builder().color("white").position(0, 0).rotation(0).weight(0).build());

		counter = 0;
	}

	private void incrementCounter() {
		counter += 1;
	}

	@Test
	public void BiFunctions() {
		Ellipse2D t = new Ellipse2D.Double();
		Ellipse2D u = new Ellipse2D.Double();
		assertTrue(all.apply(t, u));
		assertFalse(none.apply(t, u));

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
	public void getEvery2Sonar() {
		assertEquals("base list size must be 8",8,list.size());
		ObjectDetector detector = new ObjectDetector(list, every2);
		List<ISensor> sonarable = detector.getSonarObjects(triangle);
		assertEquals("number of sonar detectables should be 3", 3, sonarable.size());
	}

	@Test
	public void getEvery2Camera() {
		assertEquals("base list size must be 8",8,list.size());
		ObjectDetector detector = new ObjectDetector(list, every2);

		List<ICameraSensor> camerable = detector.getCameraObjects(triangle);
		assertEquals("number of camera detectables should be 4", 4, camerable.size());
	}

	@Test
	public void getEvery2Radar() {
		assertEquals("base list size must be 8",8,list.size());
		ObjectDetector detector = new ObjectDetector(list, every2);
		List<IRadarSensor> radarable = detector.getRadarObjects(triangle);
		assertEquals("number of radarable detectables should be 1", 1, radarable.size());
	}

    @Test
    public void getIfObjectsCollide(){
        CollisionDetection cd = new CollisionDetection();
        Shape s1 = new Ellipse2D.Double(0,0,10,10);
        Shape s2 = new Ellipse2D.Double(0,0,10,10);
        assertEquals("objects should collide (true)",true,cd.apply(s1,s2));
    }
    @Test
    public void getIfObjectsNotCollide(){
        CollisionDetection cd = new CollisionDetection();
        Shape s1 = new Ellipse2D.Double(0,0,10,10);
        Shape s2 = new Ellipse2D.Double(300,300,10,10);
        assertEquals("objects should not collide (false)",false, cd.apply(s1,s2));
    }
}
