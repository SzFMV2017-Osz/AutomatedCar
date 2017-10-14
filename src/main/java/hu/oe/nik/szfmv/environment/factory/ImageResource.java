package hu.oe.nik.szfmv.environment.factory;

import java.io.IOException;
import java.util.Properties;

public class ImageResource {

	public static final String TREE_NAME = "automatedCar.tree";
	public static final String WHITE_CAR_NAME = "automatedCar.car.white";
	public static final String ROADSIGN_PARKING_RIGHT_NAME = "automatedCar.roadSign.parking.right";
	public static final String ROADSIGN_STOP_NAME = "automatedCar.roadSign.stop";

	private static Properties imageProperties;
	private static final String IMAGE_PROPERTIES_FILE = "imageFiles.properties";

	/**
	 * returns an image filename from properties of a given key
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String getImageOf(String key) {
		if (imageProperties == null) {
			try {
				initProperties();
			} catch (IOException e) {
				throw new RuntimeException("The image resources file with name " + IMAGE_PROPERTIES_FILE
						+ " does not exists int the system resources");
			}
		}
		return imageProperties.getProperty(key);
	}

	/**
	 * Initializes imageFiles.properties resource
	 * 
	 * @throws IOException
	 *             if image resources file not found
	 */
	private static void initProperties() throws IOException {
		imageProperties = new Properties();
		imageProperties.load(ClassLoader.getSystemResourceAsStream(IMAGE_PROPERTIES_FILE));

		// TODO Auto-generated method stub

	}

}
