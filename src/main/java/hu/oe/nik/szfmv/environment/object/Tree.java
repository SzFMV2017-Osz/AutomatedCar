package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class Tree extends CollidableObject {

    public Tree(int x, int y, float rotation, int width, int height, int weight, String imageFileName) {
        super(x, y, rotation, width, height, imageFileName, weight, ModelShape.ELLIPSE);
    }

    @Override
    protected void doOnCollision() {
        // TODO Auto-generated method stub
    }

}
