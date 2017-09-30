package hu.oe.nik.szfmv.environment.model;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Világ elemeinek ős osztálya
 * 
 * @author hunkak
 *
 */
public abstract class WorldObject {

	private static final Logger log = LogManager.getLogger(WorldObject.class);

	public static Properties imageProps;
	private static String IMAGE_PROPERTIES_FILE = "imageFiles.properties";

	{
		imageProps = new Properties();
		try {
			imageProps.load(ClassLoader.getSystemResourceAsStream(IMAGE_PROPERTIES_FILE));
		} catch (IOException e) {
			log.error("Could not load " + IMAGE_PROPERTIES_FILE + " from classpath.", e);
		}
	}

	// objektum helyzete a síkon
	// TODO meghatározni, hogy az objektum melyik pontja - vizualizációs csapattal
	int x, y;
	// objektum forgatása
	// TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
	float rotation;

	// objektum kiterjedése
	// TODO befoglaló négyszög? implementációs függő jelentés?
	private final int width, height;

	// objektum formája
	private final ModelShape shape;

	String imageFileName;

	public WorldObject(int x, int y, float rotation, int width, int height, String imageName, ModelShape shape) {
		super();

		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
		this.imageFileName = imageProps.getProperty(imageName);
		this.shape = shape;
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

	public String getImageFileName() {
		return imageFileName;
	}

}
