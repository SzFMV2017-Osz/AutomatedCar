package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.RoadType;
import hu.oe.nik.szfmv.environment.xml.XmlObjectType;

/**
 * Utat reprezentáló osztály
 *
 * @author hunkak
 */
public class Road extends WorldObject {
    private RoadType roadType;
    private int curvature;
    private boolean pedestrianCrossing;

    public Road(int x, int y, float rotation, int width, int height, XmlObjectType objectType, boolean pedestrianCrossing) {
        super(x, y, rotation, width, height, objectType.getXmlName(), ModelShape.RECTENGULAR);
        this.pedestrianCrossing = pedestrianCrossing;
        String[] explodedString = objectType.getXmlName().split("_");
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
