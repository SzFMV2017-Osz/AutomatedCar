package hu.oe.nik.szfmv.environment.model;

import java.awt.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.detector.ICameraSensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Világ elemeinek ős osztálya
 * 
 * @author hunkak
 *
 */
public abstract class WorldObject implements ICameraSensor {

	private static final Logger log = LogManager.getLogger(WorldObject.class);

	// objektum helyzete a síkon
	// TODO meghatározni, hogy az objektum melyik pontja - vizualizációs csapattal
	double x;

	double y;
	// objektum forgatása
	// TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
	double rotation;

	// objektum kiterjedése
	// TODO befoglaló négyszög? implementációs függő jelentés?
	private final int width, height;

	// objektum formája
	private final ModelShape shape;

	private final String imageFileName;

	public WorldObject(double x, double y, double rotation, int width, int height, String imageName, ModelShape shape) {
		super();

		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
		this.imageFileName = imageName;
		this.shape = shape;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRotation() {
		return rotation;
	}

    @Deprecated
    public int getWidth() {
        return width;
    }

    @Deprecated
    public int getHeight() {
        return height;
    }

	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @return the shape
	 * 
	 * TODO: change to Shape type
	 */
	public Shape getShape() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorldObject [x=" + x + ", y=" + y + ", rotation=" + rotation + ", width=" + width + ", height=" + height
				+ ", shape=" + shape + ", imageFileName=" + imageFileName + "]";
	}
}
