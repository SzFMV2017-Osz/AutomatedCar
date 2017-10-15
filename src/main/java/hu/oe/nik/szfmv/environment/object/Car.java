package hu.oe.nik.szfmv.environment.object;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * 
 * Autót repretzentáló osztály TODO: maghajtás bekötése TODO: irányítás
 * kidolgozása
 * 
 * @author hunkak
 *
 */
public class Car extends MovingObject implements IDriveable {

	public Car(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
		super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTENGULAR);
	}

	@Override
	protected void doOnCollision() {
		log.debug("Car#doOnCollision invoked");
		// TODO Auto-generated method stub
	}

	@Override
	public void turn(float angle) {
		log.debug("Car#turn invoked");
		// TODO Auto-generated method stub
	}

	@Override
	public void accelerate(int scale) {
		log.debug("Car#accelerate invoked");
		// TODO Auto-generated method stub

	}

	@Override
	public void slow(int scale) {
		log.debug("Car#slow invoked");
		// TODO Auto-generated method stub

	}

}
