package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * gyalogost reprezentáló osztály TODO: meghajtás bekötése
 * 
 * @author hunkak
 *
 */
public class Pedestrian extends MovingObject {

	public Pedestrian(Vector2D position, float rotation, int width, int height, String imageFileName, int weight) {
		super(position, rotation, width, height, imageFileName, weight, ModelShape.ELLIPSE);
	}

	@Override
	protected void doOnCollision() {
		log.debug("Pedestrian#doOnCollision");
		// TODO Auto-generated method stub

	}

}
