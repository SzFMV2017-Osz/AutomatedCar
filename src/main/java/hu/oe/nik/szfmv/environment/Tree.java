package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.util.ModelShape;

public class Tree extends CollidableObject {

	
	public Tree(int x, int y, float rotation, int width, int height, int weight) {
		super(x, y, rotation, width, height, weight);
		this.setShape(ModelShape.ELLIPSE);
	}

	@Override
	protected void doOnCollision() {
		// TODO Auto-generated method stub

	}

}
