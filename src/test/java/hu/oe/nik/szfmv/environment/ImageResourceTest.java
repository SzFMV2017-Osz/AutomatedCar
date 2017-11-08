package hu.oe.nik.szfmv.environment;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.oe.nik.szfmv.environment.factory.ImageResource;

public class ImageResourceTest {

    @Test
    public void getExistingKeys() {

        assertEquals("car_3_black.png", ImageResource.getImageOf(ImageResource.BLACK_CAR_NAME));
        assertEquals("roadsign_speed_60.png", ImageResource.getImageOf(ImageResource.ROADSIGN_SPEED_60_NAME));
        assertEquals("road_2lane_90left.png", ImageResource.getImageOf(ImageResource.ROAD_90_LEFT_NAME));
    }

    @Test
    public void getNoneExistingKey() {
        assertEquals("not_exists.png", ImageResource.getImageOf("nincs ilyen"));
    }

    @Test
    public void getDimensions() {
        String imageFileName = ImageResource.getImageOf(ImageResource.BLACK_CAR_NAME);
        assertEquals(120, ImageResource.getWidth(imageFileName));
        assertEquals(289, ImageResource.getHeight(imageFileName));

        imageFileName = ImageResource.getImageOf(ImageResource.ROADSIGN_SPEED_60_NAME);
        assertEquals(80, ImageResource.getWidth(imageFileName));
        assertEquals(80, ImageResource.getHeight(imageFileName));

        imageFileName = ImageResource.getImageOf(ImageResource.ROAD_90_LEFT_NAME);
        assertEquals(525, ImageResource.getWidth(imageFileName));
        assertEquals(525, ImageResource.getHeight(imageFileName));
    }
}
