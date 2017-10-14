package hu.oe.nik.szfmv.environment.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Road;
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
		}

		return rtrn;

	}

	// TODO: implement as Akos is ready
	private static WorldObject createRoad(XmlObject xmlObject) {

		Road rtrn = null;

		return rtrn;
	}
}
