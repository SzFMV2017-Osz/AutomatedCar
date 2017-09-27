package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.RoadType;

/**
 * Utat reprezentáló osztály
 * @author hunkak
 *
 */
public class Road extends WorldObject {


	final RoadType roadType;
	//zebra van-e rajta?
	final boolean pedestrianCrossing;

	public Road(int x, int y, float rotation, int width, int height, boolean pedestrianCrossing, RoadType roadType) {
		super(x, y, rotation, width, height);
		this.pedestrianCrossing = pedestrianCrossing;
		this.roadType = roadType;
		this.setShape(ModelShape.RECTENGULAR);
	}

	public RoadType getRoadType() {
		return roadType;
	}

	public boolean isPedestrianCrossing() {
		return pedestrianCrossing;
	}
	
	

}
