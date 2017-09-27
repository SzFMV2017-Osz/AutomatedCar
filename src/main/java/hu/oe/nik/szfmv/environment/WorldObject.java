package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Világ elemeinek ős osztálya
 * 
 * @author hunkak
 *
 */
public abstract class WorldObject {

	//objektum helyzete a síkon
	//TODO meghatározni, hogy az objektum melyik pontja - vizualizációs csapattal
	int x, y;
	//objektum forgatása
	//TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
	float rotation;
	
	//objektum kiterjedése
	//TODO befoglaló négyszög? implementációs függő jelentés?
	final int width, height;
	
	//objektum formája
	ModelShape shape;
	
	public WorldObject(int x, int y, float rotation, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getRotation() {
		return rotation;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public ModelShape getShape(){
		return shape;
	}
	
	protected final void setShape(ModelShape shape){
		if (shape == null)
			this.shape = shape;
	}
}
