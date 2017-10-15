package hu.oe.nik.szfmv.environment.factory;

import java.io.IOException;
import java.util.Properties;

public class ImageResource {

	public static final String TREE_NAME = "automatedCar.tree";

	public static final String WHITE_CAR_NAME = "automatedCar.car.white";
	public static final String BLACK_CAR_NAME = "automatedCar.car.black";
	public static final String BLUE_CAR_NAME = "automatedCar.car.blue";
	public static final String RED_CAR_NAME = "automatedCar.car.red";

	public static final String ROADSIGN_PARKING_RIGHT_NAME = "automatedCar.roadSign.parking.right";
	public static final String ROADSIGN_STOP_NAME = "automatedCar.roadSign.stop";
	public static final String ROADSIGN_SPEED_60 = "automatedCar.roadSign.speed.60";
	public static final String ROADSIGN_SPEED_50 = "automatedCar.roadSign.speed.50";
	public static final String ROADSIGN_SPEED_40 = "automatedCar.roadSign.speed.40";

	public static final String ROAD_45_LEFT_NAME = "automatedCar.road.45.left";
	public static final String ROAD_45_RIGHT_NAME = "automatedCar.road.45.right";
	public static final String ROAD_6_LEFT_NAME = "automatedCar.road.6.left";
	public static final String ROAD_6_RIGHT_NAME = "automatedCar.road.6.right";
	public static final String ROAD_90_LEFT_NAME = "automatedCar.road.90.left";
	public static final String ROAD_90_RIGHT_NAME = "automatedCar.road.90.right";
	public static final String ROAD_ROUNDABOUT_NAME = "automatedCar.road.roundabout";
	public static final String ROAD_CROSSWALK_NAME = "automatedCar.road.crosswalk";
	public static final String ROAD_PARKING_PARALLEL_NAME = "automatedCar.road.parking.parallel";
	public static final String ROAD_STRAIGHT_NAME = "automatedCar.road.straight";
	public static final String ROAD_T_LEFT_NAME = "automatedCar.road.t.left";
	public static final String ROAD_T_RIGHT_NAME = "automatedCar.road.t.right";

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
		return imageProperties.getProperty(key, "automatedCar.notExist");
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
