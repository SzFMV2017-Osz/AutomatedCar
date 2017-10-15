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
import java.util.List;

public class GameDisplayJPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private World world;
    private double scale;

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
            if (object.getClass().equals(CollidableObject.class)) {
                if (object.getClass().equals(MovingObject.class)) {
                    if (object.getClass().equals(Car.class)) {
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
        BufferedImage rawImage = //TODO: get this from WorldObject itself
                ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));
        return rawImage.getScaledInstance(
                (int) Math.round(rawImage.getWidth() * scale),
                (int) Math.round(rawImage.getHeight() * scale),
                BufferedImage.SCALE_DEFAULT);

    }

    private AffineTransform getTransform(WorldObject object) {
        AffineTransform trans = new AffineTransform();
        trans.translate(
                (int) Math.round(object.getX() * scale),
                (int) Math.round(object.getY() * scale));
        trans.rotate(object.getRotation());
        return trans;
    }
}
