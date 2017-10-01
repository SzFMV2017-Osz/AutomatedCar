package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

        for (WorldObject object : world.getWorldObjects()) {
            // draw objects
            BufferedImage image;
            try {
                image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));

                Graphics2D g2d = (Graphics2D)g;
                AffineTransform trans = new AffineTransform();
                trans.translate(
                        (int) Math.round(object.getX() *  scale),
                        (int) Math.round(object.getY() *  scale) );
                trans.rotate( object.getRotation() );
                g2d.drawImage(
                        image.getScaledInstance(
                                (int) Math.round(image.getWidth()* scale),
                                (int) Math.round(image.getHeight() * scale),
                                image.SCALE_DEFAULT),
                        trans,
                        this);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
