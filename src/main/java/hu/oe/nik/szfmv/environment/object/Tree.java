package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

import java.awt.geom.Ellipse2D;

public class Tree extends CollidableObject {

    /**
     * @deprecated The width and height of the object must be based on the size of
     *             the <code>imageName</code> referenced in the constructor
     *             <p>
     *             Use the following constructor instead:
     *             {@link #Tree(double x, double y, double rotation, String imageName, ModelShape shape)}
     *
     * @param x
     * @param y
     * @param rotation
     * @param width
     * @param height
     * @param weight
     * @param imageFileName
     */
    @Deprecated
    public Tree(int x, int y, float rotation, int width, int height, int weight, String imageFileName) {
        super(x, y, rotation, width, height, imageFileName, weight, ModelShape.ELLIPSE);
    }

    public Tree(int x, int y, float rotation, int weight, String imageFileName) {
        super(x, y, rotation, imageFileName, weight, ModelShape.ELLIPSE);
    }

    @Override
    protected void doOnCollision() {
        // TODO Auto-generated method stub
    }

}
