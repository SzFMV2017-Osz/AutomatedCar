package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.RoadType;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

import java.util.Objects;

/**
 * Utat reprezentáló osztály
 */
public class Road extends WorldObject {

    private static String getImageName(XmlObjectType objectType) {
        String imageFileName = "";
        switch (objectType) {
        case ROAD_45_LEFT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_45_LEFT_NAME);
            break;
        case ROAD_45_RIGHT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_45_RIGHT_NAME);
            break;
        case ROAD_6_LEFT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_6_LEFT_NAME);
            break;
        case ROAD_6_RIGHT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_6_RIGHT_NAME);
            break;
        case ROAD_90_LEFT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_90_LEFT_NAME);
            break;
        case ROAD_90_RIGHT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_90_RIGHT_NAME);
            break;
        case ROAD_ROUNDABOUT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_ROUNDABOUT_NAME);
            break;
        case ROAD_CROSSWALK:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_CROSSWALK_NAME);
            break;
        case ROAD_PARKING_PARALLEL:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_PARKING_PARALLEL_NAME);
            break;
        case ROAD_STRAIGHT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_STRAIGHT_NAME);
            break;
        case ROAD_T_LEFT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_T_LEFT_NAME);
            break;
        case ROAD_T_RIGHT:
            imageFileName = ImageResource.getImageOf(ImageResource.ROAD_T_RIGHT_NAME);
            break;
        default:
            break;
        }
        return imageFileName;
    }

    private RoadType roadType;
    private int curvature;
    private boolean pedestrianCrossing;

    public Road(int x, int y, float rotation, int width, int height, XmlObjectType objectType,
            boolean pedestrianCrossing) {
        super(x, y, rotation, width, height, getImageName(objectType), ModelShape.RECTENGULAR);
        this.pedestrianCrossing = pedestrianCrossing;
        String[] explodedString = objectType.getXmlName().split("_");
        this.curvature = 0;
        if (Objects.equals(explodedString[0], "road")) {
            switch (explodedString[2]) {
            case "45left":
                this.roadType = RoadType.LEFT;
                this.curvature = 45;
                break;
            case "45right":
                this.roadType = RoadType.RIGHT;
                this.curvature = 45;
                break;
            case "6left":
                this.roadType = RoadType.LEFT;
                this.curvature = 6;
                break;
            case "6right":
                this.roadType = RoadType.RIGHT;
                this.curvature = 6;
                break;
            case "90left":
                this.roadType = RoadType.LEFT;
                this.curvature = 90;
                break;
            case "90right":
                this.roadType = RoadType.RIGHT;
                this.curvature = 90;
                break;
            case "straight":
                this.roadType = RoadType.STRAIGHT;
                break;
            case "rotary":
                this.roadType = RoadType.ROTARY;
                break;
            case "tjunctionleft":
                this.roadType = RoadType.TJUNCTIONLEFT;
                break;
            case "tjunctionright":
                this.roadType = RoadType.TJUNCTIONRIGHT;
                break;
            }
        } else if (Objects.equals(explodedString[0], "parking")) {
            this.roadType = RoadType.PARKINGPARALLEL;
        } else if (Objects.equals(explodedString[0], "crosswalk")) {
            this.roadType = RoadType.CROSSWALK;
        }
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public boolean isPedestrianCrossing() {
        return pedestrianCrossing;
    }

    public int getCurvature() {
        return curvature;
    }
}
