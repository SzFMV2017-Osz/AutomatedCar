package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class Tree extends CollidableObject {

	public Tree(Vector2D position, float rotation, int width, int height, int weight, String imageFileName) {
		super(position, rotation, width, height, imageFileName, weight, ModelShape.ELLIPSE);
	}

	@Override
	protected void doOnCollision() {
		// TODO Auto-generated method stub
	}

}
