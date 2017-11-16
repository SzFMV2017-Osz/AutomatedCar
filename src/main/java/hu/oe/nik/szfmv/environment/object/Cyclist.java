package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * kerékpárost reprezentáló objektum TODO: maghajtás bekötése
 * 
 * @author hunkak
 *
 */
public class Cyclist extends MovingObject {

    /**
     * @deprecated The width and height of the object must be based on the size of
     *             the <code>imageName</code> referenced in the constructor
     *             <p>
     *             Use the following constructor instead:
     *             {@link #Cyclist(double x, double y, double rotation, String imageName, ModelShape shape)}
     * 
     * @param x
     * @param y
     * @param rotation
     * @param width
     * @param height
     * @param imageFileName
     * @param weight
     */
    public Cyclist(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
        super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTANGULAR);
    }

    /**
     * width and height are set based on image size
     * 
     * @param x
     * @param y
     * @param rotation
     * @param imageFileName
     * @param weight
     */
    public Cyclist(int x, int y, float rotation, String imageFileName, int weight) {
        super(x, y, rotation, imageFileName, weight, ModelShape.RECTANGULAR);
    }

    @Override
    protected void doOnCollision() {
        log.debug("Cyclist#doOnCollision");
        // TODO Auto-generated method stub

    }

}
