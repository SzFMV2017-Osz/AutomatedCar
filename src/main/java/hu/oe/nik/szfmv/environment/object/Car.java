package hu.oe.nik.szfmv.environment.object;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * 
 * Autót repretzentáló osztály TODO: maghajtás bekötése TODO: irányítás
 * kidolgozása
 * 
 * @author hunkak
 *
 */
public class Car extends MovingObject implements IDriveable {

	private static final Logger log = LogManager.getLogger(Car.class);

	public static CarBuilder builder() {
		return new CarBuilder();
	}

	private Car(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
		super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTENGULAR);
	}

	@Override
	protected void doOnCollision() {
		// TODO Auto-generated method stub
	}

	@Override
	public void turn(float angle) {
		// TODO Auto-generated method stub
	}

	@Override
	public void accelerate(int scale) {
		log.info("acclerate car to scale: " + scale);
		Vector2D v = new Vector2D(scale, scale);
		changeDirection(v);
	}

	@Override
	public void slow(int scale) {
		// TODO Auto-generated method stub

	}

	public static class CarBuilder {
		private int x;
		private int y;
		private float rotation;
		private int width = 100;
		private int height = 100;
		private int weight = 1000;
		private String imageFileName;

		private CarBuilder() {
		}

		public CarBuilder position(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public CarBuilder rotation(float rotation) {
			this.rotation = rotation;
			return this;
		}

		public CarBuilder dimension(int width, int height) {
			// TODO: implement car width, height
			return this;
		}

		public CarBuilder weight(int weight) {
			// TODO: implement car weight
			return this;
		}

		public CarBuilder color(String color) {
			String type = "";
			switch (color.toLowerCase()) {
			case "white":
				type = ImageResource.WHITE_CAR_NAME;
				break;
			case "black":
				type = ImageResource.BLACK_CAR_NAME;
				break;
			case "blue":
				type = ImageResource.BLUE_CAR_NAME;
				break;
			case "red":
				type = ImageResource.RED_CAR_NAME;
				break;
			}
			imageFileName = ImageResource.getImageOf(type);
			return this;
		}

		public Car build() {
			log.info("building a car...");
			Car c = new Car(x, y, rotation, width, height, imageFileName, weight);
			log.info(c.toString());
			return c;
		}
	}
}
