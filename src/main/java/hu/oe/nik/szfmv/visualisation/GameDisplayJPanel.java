package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GameDisplayJPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private World world;
    private double scale;

    private HashMap<String, Image>
            imageCache = new HashMap<>();
    private final RoadConstants roadConst;

    public GameDisplayJPanel(World gameWorld, double scale) {
        this.world = gameWorld;
        this.scale = scale;
        roadConst = new RoadConstants(scale);
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //roads (unmoving) lowest priority, draw first
        //  (so later they are drawn over)
        //cars highest priority, draw last

        drawObjects(g2d, world.getWorldObjectsFiltered().getUnmoving());
        drawObjects(g2d, world.getWorldObjectsFiltered().getMoving());
        drawObjects(g2d, world.getWorldObjectsFiltered().getCollidable());
        drawObjects(g2d, world.getWorldObjectsFiltered().getCars());
    }

    private void drawObjects(Graphics2D g2d, List<WorldObject> objects) {
        for (WorldObject object : objects) {
            // draw objects
            try {
                Image image = getScaledImage(object);

                AffineTransform trans = getTransform(object);

                g2d.drawImage(image, trans, this);

            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private Image getScaledImage(WorldObject object) throws IOException {
        String filename = object.getImageFileName();
        //HashMap.get returns null if the key doesn't exist
        Image image = imageCache.get(filename);

        //if it exists in the HashMap, return it
        if (image != null)
            return image;

        //else, get the image file, insert it into imageCache
        // then return it
        return addImageToCache(object, filename);

    }

    private Image addImageToCache(WorldObject object, String filename) throws IOException {
        BufferedImage rawImage = ImageIO.read(new File(
                ClassLoader.getSystemResource(
                        object.getImageFileName()).getFile()));
        Image image = rawImage.getScaledInstance(
                (int) Math.round(rawImage.getWidth() * scale),
                (int) Math.round(rawImage.getHeight() * scale),
                BufferedImage.SCALE_DEFAULT);
        imageCache.put(filename, image);
        return image;
    }

    private AffineTransform getTransform(WorldObject object) throws IOException {
        Coord offset = getOffset(object);

        AffineTransform translation = new AffineTransform();
        int scaledX = (int) Math.round(object.getX() * scale - offset.getX());
        int scaledY = (int) Math.round(object.getY() * scale - offset.getY());
        translation.translate(scaledX, scaledY);

        AffineTransform rotation;
        rotation = AffineTransform.getRotateInstance(
                object.getRotation(),
                offset.getX(),
                offset.getY());

        translation.concatenate(rotation);

        return translation;
    }

    private Coord getOffset(WorldObject object) {
        Coord c = roadConst.scaledRoadOffsets.get(object.getImageFileName());

        if (c == null)
            return new Coord(0, 0);
        else
            return c;
    }
}
