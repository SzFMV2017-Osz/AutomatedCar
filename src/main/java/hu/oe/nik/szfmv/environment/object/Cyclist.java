package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * kerékpárost reprezentáló objektum TODO: maghajtás bekötése
 * 
 * @author hunkak
 *
 */
public class Cyclist extends MovingObject {

	public Cyclist(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
		super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTANGULAR);
	}

	@Override
	protected void doOnCollision() {
		log.debug("Cyclist#doOnCollision");
		// TODO Auto-generated method stub

	}

}
