package hu.oe.nik.szfmv.environment.model;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.Utils;

/**
 * Mozgó objektumokat reprezentáló osztály TODO: maximális sebesség, min-max
 * gyorsulás beépítése
 *
 * @author hunkak
 *
 */
public abstract class MovingObject extends CollidableObject implements IRadarSensor {

    protected static final Logger log = LogManager.getLogger(MovingObject.class);

    // objektum pillantnyi sebessége
    private Vector2D currentSpeed;

    /**
     * @deprecated The width and height of the object must be based on the size of
     *             the <code>imageName</code> referenced in the constructor
     *             <p>
     *             Use the following constructor instead:
     *             {@link #MovingObject(double x, double y, double rotation, String imageName, ModelShape shape)}
     * 
     * 
     * @param x
     * @param y
     * @param rotation
     * @param width
     * @param height
     * @param imageFileName
     * @param weight
     * @param shape
     */
    @Deprecated
    public MovingObject(int x, int y, float rotation, int width, int height, String imageFileName, int weight,
            ModelShape shape) {
        super(x, y, rotation, width, height, imageFileName, weight, shape);
        // TODO: this must be 0,0 so the object is steady
        this.currentSpeed = new Vector2D(0, 0);
    }

    /**
     * width and height are set based on image size
     * 
     * @param x
     * @param y
     * @param rotation
     * @param imageFileName
     * @param weight
     * @param shape
     */
    public MovingObject(int x, int y, float rotation, String imageFileName, int weight, ModelShape shape) {
        super(x, y, rotation, imageFileName, weight, shape);
        // TODO: this must be 0,0 so the object is steady
        this.currentSpeed = new Vector2D(0, 0);
    }
    
    public void move() {
        if (log.isDebugEnabled()) {
            log.debug("move called");
        }

        this.position = this.position.add(currentSpeed.div(24));
        if (currentSpeed.abs() < 0) {
            this.rotation = this.currentSpeed.getAngleRad();
        }
    }

    /**
     * change currentspeed to the resultants of currentspeed and the given vectors
     * @param vectors
     */
    protected void changeDirection(List<Vector2D> vectors) {
        for (Vector2D vector2d : vectors) {
            currentSpeed = currentSpeed.add(vector2d);

        }
    }

    /**
     * change currentspeed to the resultants of currentspeed and the given vector
     * @param vector
     */
    protected void changeDirection(Vector2D vector) {
        currentSpeed = currentSpeed.add(vector);

    }

    /**
     * Motgzó objeltum impulzusa
     * 
     * @return vektor melynek nagysága az impulzus nagysága, iránya egyezik az
     *         objektum sebességének irányával (szögben)
     */
    public Vector2D getImpulse() {
        return currentSpeed.mult(getWeight());
    }

    /**
     * Az objektum pillanatnyi sebessége
     * 
     * @return vektor melynek nagysága a pillanatnyi sebesség nagysága, iránya a
     *         pillanatnyi sebesség iránya (szögben)
     */
    public Vector2D getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * @deprecated
     * set the objects rotation via the {@link #changeDirection(Vector2D vector)} method
     * 
     * @param angle
     */
    @Deprecated
    public void setRotationAngle(double angle) {
    }

    /**
     * @deprecated
     * set the objects rotation via the {@link #changeDirection(Vector2D vector)} method
     * 
     * @param radian
     */
    @Deprecated
    public void setRotationRadian(double radian) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MovingObject [currentSpeed=" + currentSpeed + ", x=" + this.getX() + ", y=" + this.getY()
                + ", rotation=" + this.getRotation() + ", isCollided()=" + isCollided() + ", getWeight()=" + getWeight()
                + ", getWidth()=" + getWidth() + ", getHeight()=" + getHeight() + ", getImageFileName()="
                + getImageFileName() + ", getShape()=" + getShape() + "]";
    }

    public void setRot(double value) {
        this.rotation = value;
    }
}
