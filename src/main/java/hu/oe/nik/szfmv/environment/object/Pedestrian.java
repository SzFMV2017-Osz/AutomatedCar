package hu.oe.nik.szfmv.environment.object;

import java.util.List;

import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * gyalogost reprezentáló osztály
 * TODO: meghajtás bekötése
 * @author hunkak
 *
 */
public class Pedestrian extends MovingObject {
	

	public Pedestrian(int x, int y, float rotation, int width, int height,String imageFileName, int weight) {
		super(x, y, rotation, width, height, imageFileName, weight, ModelShape.ELLIPSE);
	}


	@Override
	protected void doOnCollision() {
		log.debug("Pedestrian#doOnCollision");
		// TODO Auto-generated method stub

	}

}
