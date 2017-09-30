package hu.oe.nik.szfmv.environment.object;

import java.util.List;

import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * kerékpárost reprezentáló objektum TODO: maghajtás bekötése
 * 
 * @author hunkak
 *
 */
public class Cyclist extends MovingObject {

	public Cyclist(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
		super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTENGULAR);
	}

	@Override
	protected void doOnCollision() {
		log.debug("Cyclist#doOnCollision");
		// TODO Auto-generated method stub

	}

}
