package hu.oe.nik.szfmv.visualisation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CourseDisplay {

	private static final Logger logger = LogManager.getLogger();
	private JFrame frame = new JFrame("OE NIK Automated Car Project");

	public void refreshFrame() {
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	public void init(World world) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {

				for (WorldObject object : world.getWorldObjects()) {
					// draw objects
					BufferedImage image;
					try {
						image = ImageIO
								.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));
						g.drawImage(image, object.getX(), object.getY(), null);
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		});

		frame.validate();
		frame.setSize(world.getWidth(), world.getHeight());
		frame.setVisible(true);
	}

}
