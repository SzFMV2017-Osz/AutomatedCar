package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

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

        GameDisplayJPanel displayPanel = new GameDisplayJPanel(world);
        frame.add(displayPanel);

        frame.validate();
        frame.setSize(world.getWidth(), world.getHeight());
        frame.setVisible(true);
    }

}
