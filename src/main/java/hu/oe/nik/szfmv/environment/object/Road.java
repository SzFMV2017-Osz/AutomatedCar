package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.RoadType;

/**
 * Utat reprezentáló osztály
 *
 * @author hunkak
 */
public class Road extends WorldObject {

	/*
     * TODO: részeltesebb leíró implementáció kidolgozása, az enum kevés
	 *
	 * meg kell adni az út síkbeli viszonítási pontján túl az út belső viszonyítási
	 * pontjait, pl felező vonal közepe
	 * 
	 */

    private final RoadType roadType;
    // zebra van-e rajta?
    private final boolean pedestrianCrossing;

    public Road(int x, int y, float rotation, int width, int height, String imageFileName, boolean pedestrianCrossing,
                RoadType roadType) {
        super(x, y, rotation, width, height, imageFileName, ModelShape.RECTENGULAR);
        this.pedestrianCrossing = pedestrianCrossing;
        this.roadType = roadType;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public boolean isPedestrianCrossing() {
        return pedestrianCrossing;
    }

}
