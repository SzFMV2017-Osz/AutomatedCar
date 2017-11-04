package hu.oe.nik.szfmv.environment.model;

import hu.oe.nik.szfmv.common.Vector2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.util.ModelShape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Világ elemeinek ős osztálya
 * 
 * @author hunkak
 *
 */
public abstract class WorldObject {

	private static final Logger log = LogManager.getLogger(WorldObject.class);

	// objektum helyzete a síkon
	// TODO meghatározni, hogy az objektum melyik pontja - vizualizációs csapattal
	Vector2D position;
	// objektum forgatása
	// TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
	float rotation;

	// objektum kiterjedése
	// TODO befoglaló négyszög? implementációs függő jelentés?
	private final int width, height;

	// objektum formája
	private final ModelShape shape;

	private final String imageFileName;

	private BufferedImage imageFile;

	public WorldObject(Vector2D position, float rotation, int width, int height, String imageName, ModelShape shape) {
		super();

		this.position=position;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
		this.imageFileName = imageName;
		this.shape = shape;
		try {
			this.imageFile = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getX() { return (int)position.getX(); }

	public int getY() {
		return (int)position.getY();
	}

	public Vector2D getPosition(){ return position ;}

	public float getRotation() {
		return rotation;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public BufferedImage getImageFile() {
		return imageFile;
	}

	/**
	 * @return the shape
	 */
	public ModelShape getShape() {
		return shape;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorldObject [x=" + position.getX() + ", y=" + position.getY() + ", rotation=" + rotation + ", width=" + width + ", height=" + height
				+ ", shape=" + shape + ", imageFileName=" + imageFileName + "]";
	}

}
