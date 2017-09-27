package hu.oe.nik.szfmv.environment;

import java.util.List;

import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * 
 * Autót repretzentáló osztály
 * TODO: maghajtás bekötése
 * TODO: irányítás kidolgozása
 * @author hunkak
 *
 */
public class Car extends MovingObject {

	public Car(int x, int y, float rotation, int width, int height, int weight) {
		super(x, y, rotation, width, height, weight);
		this.setShape(ModelShape.RECTENGULAR);
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
