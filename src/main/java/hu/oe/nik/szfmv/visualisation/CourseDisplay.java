package hu.oe.nik.szfmv.visualisation;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.inputinterface.UserInputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CourseDisplay {

    private static final Logger logger = LogManager.getLogger();
    private JFrame frame = new JFrame("OE NIK Automated Car Project");

    private static final int maxHeight = 900, maxWidth = 1280;
    private static final double idealRatio = (double) maxWidth / (double) maxHeight;
    private static double scale = 1;

    public void refreshFrame() {
        //frame.invalidate();
        //frame.validate();
        frame.repaint();

    }
    double distance = 0;
    Vector2D prev;
    Vector2D current;

    public void init(World world) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFrameSize(world);
        frame.addKeyListener(new UserInputHandler());
        GameDisplayJPanel displayPanel = new GameDisplayJPanel(
                world,
                scale,
                frame.getWidth(),
                frame.getHeight());

        displayPanel.setLayout(null);
        JPanel dashboard = new Dashboard().getJPanel();
        dashboard.setBounds(1100, 0, 180, 160);
        dashboard.setVisible(true);
        displayPanel.add(dashboard);

        frame.add(displayPanel);
        frame.validate();
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                System.out.println(x+","+y);//these co-ords are relative to the component


                current = new Vector2D(x,y);

                if (prev !=null)
                {
                    System.out.println("Curr dist: "+Math.abs(Math.hypot(prev.getX()-current.getX(),prev.getY()-current.getY())));//these co-ords are relative to the component
                    distance+= Math.abs(Math.hypot(prev.getX()-current.getX(),prev.getY()-current.getY()));
                }


                prev = current;
                System.out.println("dist: "+distance);//these co-ords are relative to the component
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void setFrameSize(World world) {
        //if we need to scale the game field
        if (world.getHeight() > maxHeight || world.getWidth() > maxWidth) {
            scaleGameFieldToFrame(world);
        } else {
            frame.setSize(world.getWidth(), world.getHeight());
        }
    }

    private void scaleGameFieldToFrame(World world) {
        //ratio > 1: horizontal
        //ratio < 1: vertical
        double worldRatio = (double) world.getWidth() / (double) world.getHeight();
        int scaledHeight, scaledWidth;
        //worldRatio > idealRatio: we have to scale height
        //else we have to scale width

        if (worldRatio > idealRatio) {
            scaledWidth = maxWidth;
            calculateScale(scaledWidth, world.getWidth());
            scaledHeight = (int) Math.round(
                    world.getHeight() * ((double) maxWidth / (double) world.getWidth()));

        } else {
            scaledHeight = maxHeight;
            calculateScale(scaledHeight, world.getHeight());
            scaledWidth = (int) Math.round(
                    world.getWidth() * ((double) maxHeight / (double) world.getHeight()));
        }
        frame.setSize(scaledWidth, scaledHeight);
    }

    private void calculateScale(int sizeFrom, int sizeTo) {
        scale = ((double) sizeFrom) / sizeTo;
    }

}