package hu.oe.nik.szfmv.environment;

import java.util.List;

import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * gyalogost reprezentáló osztály
 * TODO: meghajtás bekötése
 * @author hunkak
 *
 */
public class Pedestrian extends MovingObject {

	public Pedestrian(int x, int y, float rotation, int width, int height, int weight) {
		super(x, y, rotation, width, height, weight);
		this.setShape(ModelShape.ELLIPSE);
	}

	@Override
	public void move(List<Vector> vectors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doOnCollision() {
		// TODO Auto-generated method stub

	}

}
