package hu.oe.nik.szfmv.environment.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageResource {

    public static final String TREE_NAME = "automatedCar.tree";

    public static final String WHITE_CAR_1_NAME = "automatedCar.car.white.1";
    public static final String BLUE_CAR_1_NAME = "automatedCar.car.blue.1";
    public static final String RED_CAR_1_NAME = "automatedCar.car.red.1";

    public static final String WHITE_CAR_2_NAME = "automatedCar.car.white.2";
    public static final String BLUE_CAR_2_NAME = "automatedCar.car.blue.2";
    public static final String RED_CAR_2_NAME = "automatedCar.car.red.2";

    public static final String BLACK_CAR_NAME = "automatedCar.car.black";

    public static final String ROADSIGN_PARKING_RIGHT_NAME = "automatedCar.roadSign.parking.right";
    public static final String ROADSIGN_STOP_NAME = "automatedCar.roadSign.stop";
    public static final String ROADSIGN_SPEED_60_NAME = "automatedCar.roadSign.speed.60";
    public static final String ROADSIGN_SPEED_50_NAME = "automatedCar.roadSign.speed.50";
    public static final String ROADSIGN_SPEED_40_NAME = "automatedCar.roadSign.speed.40";

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
    
    private static final String NOT_EXISTS = "automatedCar.notExists";

    private static final String IMAGE_PROPERTIES_FILE = "imageFiles.properties";

    private static final Logger log = LogManager.getLogger(ImageResource.class);

    private static Properties imageProperties;

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
                String message = "The image resources file with name " + IMAGE_PROPERTIES_FILE
                        + " does not exists int the system resources";
                log.error(message);
                throw new RuntimeException(message);
            }
        }
        return imageProperties.getProperty(key, imageProperties.getProperty(NOT_EXISTS));
    }

    /**
     * Initializes imageFiles.properties resource
     * 
     * @throws IOException
     *             if image resources file not found
     */
    private static void initProperties() throws IOException {
        log.info("initializing imageProperties...");
        imageProperties = new Properties();
        imageProperties.load(ClassLoader.getSystemResourceAsStream(IMAGE_PROPERTIES_FILE));
        log.info("imageProperties initialized succesfully");
    }

    /**
     * the width of the image file in pixels referenced by the imageFileName i
     * 
     * @param imageFileName
     * @return
     */
    public static int getWidth(String imageFileName) {
        try {
            return getBufferedImage(imageFileName).getWidth();
        } catch (IOException e) {
            log.error("Can not find image: " + imageFileName + " in system resources... width is set to 0");
            return 0;
        }
    }

    /**
     * the height of the image file in pixels referenced by the imageFileName i
     * 
     * @param imageFileName
     * @return
     */
    public static int getHeight(String imageFileName) {
        try {
            return getBufferedImage(imageFileName).getHeight();
        } catch (IOException e) {
            log.error("Can not find image: " + imageFileName + " in system resources... height is set to 0");
            return 0;

        }
    }

    private static BufferedImage getBufferedImage(String imageFileName) throws IOException {
        log.info("getBufferedImage invoked with params: " + imageFileName);
        //if (imageFileName != null)
            return ImageIO.read(new File(ClassLoader.getSystemResource(imageFileName).getFile()));
        //else
        //    return new BufferedImage (1, 1, BufferedImage.TYPE_INT_ARGB);
    }
}
