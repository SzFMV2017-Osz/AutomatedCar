package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * gyalogost reprezentáló osztály TODO: meghajtás bekötése
 *
 * @author hunkak
 *
 */
public class Pedestrian extends MovingObject {

    /**
     * @deprecated The width and height of the object must be based on the size of
     *             the <code>imageName</code> referenced in the constructor
     *             <p>
     *             Use the following constructor instead:
     *             {@link #Pedestrian(double x, double y, double rotation, String imageName, ModelShape shape)}
     * 
     * @param x
     * @param y
     * @param rotation
     * @param width
     * @param height
     * @param imageFileName
     * @param weight
     */
    public Pedestrian(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
        super(x, y, rotation, width, height, imageFileName, weight, ModelShape.ELLIPSE);
    }

    /**
     * width and height are set based on images size
     * 
     * @param x
     * @param y
     * @param rotation
     * @param imageFileName
     * @param weight
     */
    public Pedestrian(int x, int y, float rotation, String imageFileName, int weight) {
        super(x, y, rotation, imageFileName, weight, ModelShape.ELLIPSE);
    }

    @Override
    protected void doOnCollision() {
        log.debug("Pedestrian#doOnCollision");
        // TODO Auto-generated method stub
    }

}
