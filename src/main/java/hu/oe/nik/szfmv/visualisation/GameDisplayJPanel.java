package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameDisplayJPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private World world;
    private double scale;

    private HashMap<String, BufferedImage>
            imageCache = new HashMap<>();
    private static final int roadWidth = 350; //350px

    public GameDisplayJPanel(World gameWorld, double scale) {
        this.world = gameWorld;
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        //filter out WorldObjects into separate groups
        // to render them in order
        ArrayList<WorldObject>
                staticObjects = new ArrayList<>(), //ie. roads
                collideableObjects = new ArrayList<>(),
                movingObjects = new ArrayList<>(),
                cars = new ArrayList<>(); //cars drawn last

        for (WorldObject object : world.getWorldObjects()) {
            if (object instanceof CollidableObject) {
                if (object instanceof MovingObject) {
                    if (object instanceof Car) {
                        cars.add(object);
                    } else movingObjects.add(object);
                } else collideableObjects.add(object);
            } else { //roads
                staticObjects.add(object);
            }
        }

        Graphics2D g2d = (Graphics2D) g;

        //roads lowest priority, draw first
        //  (so later they are drawn over)
        //cars highest priority, draw last

        drawObjects(g2d, staticObjects);
        drawObjects(g2d, collideableObjects);
        drawObjects(g2d, movingObjects);
        drawObjects(g2d, cars);
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
        BufferedImage rawImage = getBufferedImage(object);
        return rawImage.getScaledInstance(
                (int) Math.round(rawImage.getWidth() * scale),
                (int) Math.round(rawImage.getHeight() * scale),
                BufferedImage.SCALE_DEFAULT);

    }

    private BufferedImage getBufferedImage(WorldObject object) throws IOException {

        String filename = object.getImageFileName();
        //HashMap.get returns null if the key doesn't exist
        BufferedImage image = imageCache.get(filename);

        //if it exists in the HashMap, return it
        if (image != null)
            return image;


        //else, get the image file, insert it into imageCache
        // then return it
        image = ImageIO.read(new File(
                ClassLoader.getSystemResource(
                        object.getImageFileName()).getFile()));
        imageCache.put(filename, image);
        return image;
    }

    private AffineTransform getTransform(WorldObject object) throws IOException {
        Coord c = getCoord(object);

        AffineTransform translation = new AffineTransform();
        int scaledX = (int) Math.round(object.getX() * scale - c.getX());
        int scaledY = (int) Math.round(object.getY() * scale - c.getY());
        translation.translate(scaledX, scaledY);

        AffineTransform rotation;
        rotation = AffineTransform.getRotateInstance(
                object.getRotation(),
                c.getX(),
                c.getY());

        translation.concatenate(rotation);

        return translation;
    }

    private Coord getCoord(WorldObject object) {
        Coord c = RoadConstants.roadInfo.get(
                object.getImageFileName());
        if (c == null)
            c = new Coord(0, 0);
        c = new Coord((int) (c.getX() * scale), (int) (c.getY() * scale));
        return c;
    }

    private double calculateRotateBaseX(WorldObject object) throws IOException {
        String filename = object.getImageFileName();
        if (filename == "road_2lane_tjunctionright.png" || filename == "road_2lane_tjunctionleft.png")
            return getObjectWidth(object) * scale;
        else if (filename == "road_2lane_90left.png" || filename == "road_2lane_45left.png")
            return (getObjectWidth(object) - roadWidth) * scale;
        else if (filename == "road_2lane_6left.png") {
            return (roadWidth + getObjectWidth(object) - roadWidth) * scale;
        } else if (filename.contains("road"))
            return roadWidth * scale;
        else return Double.MIN_VALUE;
    }

    private double calculateRotateBaseY(WorldObject object) throws IOException {
        String filename = object.getImageFileName();
        if (filename == "road_2lane_tjunctionright.png" || filename == "road_2lane_tjunctionleft.png")
            return 0;
        else if (filename.contains("road"))
            return (getObjectHeight(object)) * scale;
        else return Double.MIN_VALUE;
    }

    //TODO: we should be able to get this from WorldObjects themselves but we cannot...
    private int getObjectHeight(WorldObject object) throws IOException {
        return getBufferedImage(object).getHeight();
    }

    private int getObjectWidth(WorldObject object) throws IOException {
        return getBufferedImage(object).getHeight();
    }
}
