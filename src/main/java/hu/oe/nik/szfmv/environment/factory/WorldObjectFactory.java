package hu.oe.nik.szfmv.environment.factory;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Road;
import hu.oe.nik.szfmv.environment.object.RoadSign;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.RoadSignType;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

public class WorldObjectFactory {

    private static final Logger log = LogManager.getLogger(WorldObjectFactory.class);

    /**
     * creates a new world object of specific child from XmlObject
     *
     * @param xmlObject
     * @return
     * @throws IOException if image resources file not found
     */
    public static WorldObject createWorldObject(XmlObject xmlObject) throws IOException {

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
                break;

            default:
                String message = "rootType " + rootType + " does not exists";
                log.error(message);
                throw new IllegalArgumentException(message);
        }
        return rtrn;

    }

    /**
     * creates a tree
     *
     * TODO: set width and weight somehow
     *
     * @param xmlObject
     * @return
     */
    private static WorldObject createTree(XmlObject xmlObject) {
        // weight is max value assuming that object has infinite impulse on crash
        log.info("creating a Tree...");

        int width = 100;
        int height = 100;
        int weight = Integer.MAX_VALUE;
        String imageFileName = ImageResource.getImageOf(ImageResource.TREE_NAME);

        Tree t = new Tree(xmlObject.getX(), xmlObject.getY(), xmlObject.getRotation(), width, height, weight,
                imageFileName);
        log.info(t.toString());

        return t;
    }

    /**
     * creates a roadsign
     *
     * TODO: set width and weight somehow
     *
     * @param xmlObject
     * @return
     */
    private static WorldObject createRoadSign(XmlObject xmlObject) {

        log.info("creating a RoadSign of... " + xmlObject.getType());

        String imageFileName;
        RoadSignType type;
        int width = 100;
        int height = 100;
        int weight = Integer.MAX_VALUE;

        switch (xmlObject.getType()) {
            case ROADSIGN_PARKING_RIGHT:
                imageFileName = ImageResource.ROADSIGN_PARKING_RIGHT_NAME;
                type = RoadSignType.PARKING_RIGHT;
                break;
            case ROADSIGN_STOP:
                imageFileName = ImageResource.ROADSIGN_STOP_NAME;
                type = RoadSignType.STOP;
                break;
            case ROADSIGN_SPEED_60:
                imageFileName = ImageResource.ROADSIGN_SPEED_60_NAME;
                type = RoadSignType.SPEED_60;
                break;
            case ROADSIGN_SPEED_50:
                imageFileName = ImageResource.ROADSIGN_SPEED_50_NAME;
                type = RoadSignType.SPEED_50;
                break;
            case ROADSIGN_SPEED_40:
                imageFileName = ImageResource.ROADSIGN_SPEED_40_NAME;
                type = RoadSignType.SPEED_40;
                break;
            default:
                throw new IllegalArgumentException("road type :" + xmlObject.getType() + " does not exists");
        }

        // weight is max value assuming that object has infinite impulse on crash
        RoadSign r = new RoadSign(xmlObject.getX(), xmlObject.getY(), xmlObject.getRotation(), width, height,
                ImageResource.getImageOf(imageFileName), weight, type);
        log.info(r.toString());
        return r;
    }

    /**
     * creates a piece of road
     *
     * TODO: implement as Akos is ready
     *
     * @param xmlObject
     * @return
     */
    private static WorldObject createRoad(XmlObject xmlObject) {

        log.info("creating a new Road of... " + xmlObject.getType());
        int width = 100;
        int height = 100;
        boolean pedestrianCrossing = xmlObject.getType() == XmlObjectType.ROAD_CROSSWALK;

        Road rtrn = new Road(xmlObject.getX(), xmlObject.getY(), xmlObject.getRotation(), width, height,
                xmlObject.getType(), pedestrianCrossing);

        return rtrn;
    }
}
