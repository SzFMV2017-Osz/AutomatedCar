package hu.oe.nik.szfmv.environment.model;

import hu.oe.nik.szfmv.environment.detector.ISensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Ütközni képes objektumokat reprezentáló osztály TODO: ütközés detektálás
 * kialakítása (- vizualizációs csapattal egyeztetni)
 *
 * @author hunkak
 */
public abstract class CollidableObject extends WorldObject implements ISensor {

    private static final Logger log = LogManager.getLogger(CollidableObject.class);

    // mozgó objektum tömege impulzus számításhoz
    private final int weight;
    // objektum ütközött-e már
    // TODO: bináris vagy skálázható érték legyen?
    private boolean collided;

    @Deprecated
    public CollidableObject(int x, int y, float rotation, int width, int height, String imageFileName, int weight,
            ModelShape shape) {
        super(x, y, rotation, width, height, imageFileName, shape);

        this.weight = weight;
        this.collided = false;
    }

    public CollidableObject(int x, int y, float rotation, String imageFileName, int weight, ModelShape shape) {
        super(x, y, rotation, imageFileName, shape);

        this.weight = weight;
        this.collided = false;
    }

    /**
     * ütközés eseménykezelője
     */
    public void onCollision() {
        log.debug("CollidableObject#onCollision invoked");
        this.collided = true;
        doOnCollision();
    }

    /**
     * objektum viselkedése ütközéskor
     */
    protected abstract void doOnCollision();

    public boolean isCollided() {
        return collided;
    }

    public int getWeight() {
        return weight;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CollidableObject [weight=" + weight + ", collided=" + collided + ", x=" + this.getX() + ", y="
                + this.getY() + ", rotation=" + this.getRotation() + ", getWidth()=" + getWidth() + ", getHeight()="
                + getHeight() + ", getImageFileName()=" + getImageFileName() + ", getShape()=" + getShape() + "]";
    }

}
