package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

import java.awt.geom.Ellipse2D;

public class Tree extends CollidableObject {

	public Tree(int x, int y, float rotation, int width, int height, int weight, String imageFileName) {
		super(x, y, rotation, width, height, imageFileName, weight, new Ellipse2D.Double(x, y, width, height));
	}

	@Override
	protected void doOnCollision() {
		// TODO Auto-generated method stub
	}

}
