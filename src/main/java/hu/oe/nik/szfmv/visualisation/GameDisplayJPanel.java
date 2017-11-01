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
    private HashMap<WorldObject, WorldObjectDisplayState>
            transformCache = new HashMap<>();
    private Image staticBackground = null;

    private final RoadConstants roadConst;

    //need height/width because this.getHeight/Width is 0 at constructor time
    public GameDisplayJPanel(World gameWorld, double scale, int width, int height) {
        this.world = gameWorld;
        this.scale = scale;
        roadConst = new RoadConstants(scale);
        //draw background once into an Image
        staticBackground = generateStaticBackground(width, height);
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //only redraw the background as a whole
        g2d.drawImage(
                staticBackground,
                0,0,
                null);


        drawObjects(g2d,
                world.getWorldObjectsFiltered().getCollidable(),
                false);
        drawObjects(g2d,
                world.getWorldObjectsFiltered().getMoving(),
                true);
        drawObjects(g2d,
                world.getWorldObjectsFiltered().getCars(),
                true);
    }

    private Image generateStaticBackground(int width, int height) {
        BufferedImage bg = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bg.createGraphics();
        drawObjects(g2d,
                world.getWorldObjectsFiltered().getUnmoving(),
                false);
        return bg;
    }

    private void drawObjects(Graphics2D g2d, List<WorldObject> objects, boolean centerAnchorPoint) {
        for (WorldObject object : objects) {
            // draw objects
            try {
                Image image = getScaledImage(object);

                AffineTransform trans = getTransform(object, centerAnchorPoint);

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
        image = makeScaledImage(object, filename);
        imageCache.put(filename, image);
        return image;
    }

    private Image makeScaledImage(WorldObject object, String filename) throws IOException {
        ;
        BufferedImage rawImage = ImageIO.read(
                GameDisplayJPanel.class.getClassLoader().getResourceAsStream(filename));
        Image image = rawImage.getScaledInstance(
                (int) Math.round(rawImage.getWidth() * scale),
                (int) Math.round(rawImage.getHeight() * scale),
                BufferedImage.SCALE_DEFAULT);
        return image;
    }

    private AffineTransform getTransform(WorldObject object, boolean centerAnchorPoint) throws IOException {
        WorldObjectDisplayState prevState = transformCache.get(object);
        //not in cache yet
        if (prevState == null) {
            AffineTransform t = makeTransform(object, centerAnchorPoint);
            WorldObjectDisplayState state =
                    WorldObjectDisplayState.createState(object, t);
            transformCache.put(object, state);
            return t;
        } else if (prevState.isChanged()) {
            AffineTransform t = makeTransform(object, centerAnchorPoint);
            prevState.updateState(t);
            return t;
        } else {
            return prevState.getTransform();
        }
    }

    private AffineTransform makeTransform(WorldObject object, boolean centerAnchorPoint) throws IOException {
        Coord offset;
        if (!centerAnchorPoint) {
            offset = getOffset(object);
        } else {
            Image img = getScaledImage(object);
            offset = new Coord(
                    (int) Math.round(img.getWidth(null) / 2.0),
                    (int) Math.round(img.getHeight(null) / 2.0));
        }

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
            return Coord.origoPoint;
        else
            return c;
    }
}
