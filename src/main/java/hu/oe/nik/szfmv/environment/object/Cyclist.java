package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * kerékpárost reprezentáló objektum TODO: maghajtás bekötése
 * 
 * @author hunkak
 *
 */
public class Cyclist extends MovingObject {

	public Cyclist(Vector2D position, float rotation, int width, int height, String imageFileName, int weight) {
		super(position, rotation, width, height, imageFileName, weight, ModelShape.RECTENGULAR);
	}

	@Override
	protected void doOnCollision() {
		log.debug("Cyclist#doOnCollision");
		// TODO Auto-generated method stub

	}

}
