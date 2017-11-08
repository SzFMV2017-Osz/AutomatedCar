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

    /**
     * @deprecated This constructor should not be referenced at all!
     *
     * @param x
     * @param y
     * @param rotation
     * @param width
     * @param height
     * @param imageFileName
     * @param weight
     */
    @Deprecated
    private Car(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
        super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTENGULAR);
    }

    /**
     * width and height are based on the image size
     *
     * @param x
     * @param y
     * @param rotation
     * @param imageFileName
     * @param weight
     */
    public Car(int x, int y, float rotation, String imageFileName, int weight) {
        super(x, y, rotation, imageFileName, weight, ModelShape.RECTENGULAR);
    }

    @Override
    protected void doOnCollision() {
        // TODO Auto-generated method stub
    }

    @Override
    public void turn(float angle) {
       double rotation = this.getRotation()+angle;
       Vector2D vector = Vector2D.getForwardVector(rotation).mult(this.getCurrentSpeed().abs());
       this.chageDirection(vector);

    }

    @Override
    public void accelerate(double scale) {
        log.info("acclerate car to scale: " + scale);
        if (this.getCurrentSpeed().abs() > 0) {
            this.chageDirection(this.getCurrentSpeed().mult(scale));
        } else {
            Vector2D vector =  Vector2D.getForwardVector(this.getRotation());
            this.chageDirection(vector.mult(scale));
        }
    }

    @Override
    public void slow(double scale) {
        // TODO Auto-generated method stub

    }

    public static class CarBuilder {
        private int x;
        private int y;
        private float rotation;
        private String imageFileName;
        private int weight;

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

        /**
         * @deprecated This method does nothing!
         *             <p>
         *             Width and height are now calculated from the size of the image
         *             that is referenced by the car object. The image is set based on
         *             the {@link #color(String)}.
         *
         */
        @Deprecated
        public CarBuilder dimension(int width, int height) {
            // TODO: implement car width, height
            return this;
        }

        public CarBuilder weight(int weight) {
            // TODO: implement car weight
            this.weight = weight;
            return this;
        }

        /**
         * sets the image of the car based on a color value
         *
         * @param color
         *            allowed values:
         *            <ul>
         *            <li>white,
         *            <li>black,
         *            <li>blue,
         *            <li>red,
         *            <li>red2,
         *            <li>blue2,
         *            <li>white2
         *            </ul>
         * @return
         */
        public CarBuilder color(String color) {
            String type = "";
            switch (color.toLowerCase()) {
                case "white":
                    type = ImageResource.WHITE_CAR_1_NAME;
                    break;
                case "black":
                    type = ImageResource.BLACK_CAR_NAME;
                    break;
                case "blue":
                    type = ImageResource.BLUE_CAR_1_NAME;
                    break;
                case "red":
                    type = ImageResource.RED_CAR_1_NAME;
                    break;
                case "white2":
                    type = ImageResource.WHITE_CAR_2_NAME;
                    break;
                case "blue2":
                    type = ImageResource.BLUE_CAR_2_NAME;
                    break;
                case "red2":
                    type = ImageResource.RED_CAR_2_NAME;
                    break;

            }
            imageFileName = ImageResource.getImageOf(type);
            return this;
        }

        public Car build() {
            log.info("building a car...");
            Car c = new Car(x, y, rotation, imageFileName, weight);
            log.info(c.toString());
            return c;
        }
    }
}
