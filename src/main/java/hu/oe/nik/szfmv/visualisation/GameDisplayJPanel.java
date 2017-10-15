package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameDisplayJPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private World world;

    public GameDisplayJPanel(World gameWorld) {
        this.world = gameWorld;
    }

    public void paintComponent(Graphics g) {

        for (WorldObject object : world.getWorldObjects()) {
            // draw objects
            BufferedImage image;
            try {
                image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));
                g.drawImage(image, object.getX(), object.getY(), null);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
