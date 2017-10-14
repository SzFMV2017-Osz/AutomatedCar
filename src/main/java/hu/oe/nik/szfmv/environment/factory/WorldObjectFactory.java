package hu.oe.nik.szfmv.environment.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Road;
import hu.oe.nik.szfmv.environment.object.RoadSign;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.ImageNameProperty;
import hu.oe.nik.szfmv.environment.util.RoadSignType;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

public class WorldObjectFactory {

	private static final Logger log = LogManager.getLogger(WorldObjectFactory.class);

	public static WorldObject createWorldObject(XmlObject xmlObject) {
		WorldObject rtrn;
		int rootType = xmlObject.getType().getRootType();

		switch (rootType) {

		case XmlObjectType.ROAD_TYPE:
			rtrn = createRoad(xmlObject);
			break;

		case XmlObjectType.ROADSIGN_TYPE:
			rtrn = createRoadSign(xmlObject);
			break;

		case XmlObjectType.MISC_TYPE:
			switch (xmlObject.getType()) {
			case TREE:
				rtrn = createTree(xmlObject);
				break;
			default:
				String message = "misc type :" + xmlObject.getType() + " does not exists";
				log.error(message);
				throw new IllegalArgumentException(message);
			}

		default:
			String message = "rootType " + rootType + " does not exists";
			log.error(message);
			throw new IllegalArgumentException(message);
		}
		return rtrn;

	}

	private static WorldObject createTree(XmlObject xmlObject) {
		// weight is max value assuming that object has infinite impulse on crash
		log.info("creating a Tree...");
		return new Tree(xmlObject.getX(), xmlObject.getY(), xmlObject.getRotation(), 100, 100, Integer.MAX_VALUE);
	}

	private static WorldObject createRoadSign(XmlObject xmlObject) {

		log.info("creating a new RoadSign of... " + xmlObject.getType());
		String imageFileName = "";
		RoadSignType type;

		switch (xmlObject.getType()) {
		case ROADSIGN_PARKING_RIGHT:
			imageFileName = ImageNameProperty.ROADSIGN_PARKING_RIGHT;
			type = RoadSignType.PARKING_RIGHT;
			break;
		case ROADSIGN_STOP:
			imageFileName = ImageNameProperty.ROADSIGN_STOP;
			type = RoadSignType.STOP;
			break;
		default:
			throw new IllegalArgumentException("road type :" + xmlObject.getType() + " does not exists");
		}

		// weight is max value assuming that object has infinite impulse on crash
		return new RoadSign(xmlObject.getX(), xmlObject.getY(), xmlObject.getRotation(), 100, 100, imageFileName,
				Integer.MAX_VALUE, type);
	}

	// TODO: implement as Akos is ready
	private static WorldObject createRoad(XmlObject xmlObject) {

		log.info("creating a new Road of... " + xmlObject.getType());
		Road rtrn = null;

		return rtrn;
	}
}
