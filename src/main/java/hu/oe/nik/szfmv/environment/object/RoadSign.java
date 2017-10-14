/**
 * 
 */
package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.RoadSignType;

/**
 * @author kalmankostenszky
 *
 */
public class RoadSign extends CollidableObject {

	private final RoadSignType roadSignType;

	/**
	 * @param x
	 * @param y
	 * @param rotation
	 * @param width
	 * @param height
	 * @param imageFileName
	 * @param weight
	 * @param shape
	 * @param roadSignType
	 */
	public RoadSign(int x, int y, float rotation, int width, int height, String imageFileName, int weight,
			ModelShape shape, RoadSignType roadSignType) {
		super(x, y, rotation, width, height, imageFileName, weight, shape);
		this.roadSignType = roadSignType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.oe.nik.szfmv.environment.model.CollidableObject#doOnCollision()
	 */
	@Override
	protected void doOnCollision() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the roadSignType
	 */
	public RoadSignType getRoadSignType() {
		return roadSignType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoadSign [roadSignType=" + roadSignType + "]";
	}

}
