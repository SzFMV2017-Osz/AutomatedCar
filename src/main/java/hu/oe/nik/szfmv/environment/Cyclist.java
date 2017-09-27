package hu.oe.nik.szfmv.environment;

import java.util.List;

import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;


/**
 * kerékpárost reprezentáló objektum
 * TODO: maghajtás bekötése
 * @author hunkak
 *
 */
public class Cyclist extends MovingObject {

	public Cyclist(int x, int y, float rotation, int width, int height, int weight) {
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
