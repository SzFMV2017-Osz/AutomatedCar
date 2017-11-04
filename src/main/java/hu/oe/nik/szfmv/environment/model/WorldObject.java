package hu.oe.nik.szfmv.environment.model;

import java.awt.*;
import java.awt.geom.*;

import hu.oe.nik.szfmv.environment.xml.Utils;
import java.awt.Shape;

import hu.oe.nik.szfmv.common.Vector2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.detector.ICameraSensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Világ elemeinek ős osztálya
 * 
 * @author hunkak
 *
 */
public abstract class WorldObject implements ICameraSensor {

    private static final Logger log = LogManager.getLogger(WorldObject.class);

    // objektum helyzete a síkon
    // TODO meghatározni, hogy az objektum melyik pontja - vizualizációs
    // csapattal
    private Vector2D position;
    // objektum forgatása
    // TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
    private final double rotation;

    // objektum kiterjedése
    // TODO befoglaló négyszög? implementációs függő jelentés?
    private final int width, height;

    // objektum formája
    private final ModelShape shape;

    private final String imageFileName;

    public WorldObject(double x, double y, double rotation, int width, int height, String imageName, ModelShape shape) {
        super();

        this.position = new Vector2D(x, y);
        this.rotation = rotation;
        this.width = width;
        this.height = height;
        this.imageFileName = imageName;
        this.shape = shape;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public double getRotation() {
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getWidthInMeters() {
        return Utils.convertPixelToMeter(this.getWidth());
    }

    public double getHeightInMeters() {
        return Utils.convertPixelToMeter(this.getHeight());
    }

    public double getRotationRadian() {
        return this.getRotation() * Math.PI / 180;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }



    public String getImageFileName() {
        return imageFileName;
    }

    public Shape getShape() {
        Shape tempShape = null;
        switch (this.shape) {
            case ELLIPSE:
                tempShape = new Ellipse2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
                break;
            case RECTENGULAR:
                tempShape = new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
                break;
        }
        AffineTransform affineTransform = AffineTransform.getRotateInstance(this.getRotationRadian(), this.getX(), this.getY());
        PathIterator pathIterator = tempShape.getPathIterator(affineTransform);
        Polygon polygon = new Polygon();
        while (pathIterator.isDone()) {
            double[] xy = new double[2];
            pathIterator.currentSegment(xy);
            polygon.addPoint((int) xy[0], (int) xy[1]);
            pathIterator.next();
        }
        return polygon;
    }

    @Override
    public String toString() {
        return "WorldObject [x=" + this.getX() + ", y=" + this.getY() + ", rotation=" + rotation + ", width=" + width + ", height=" + height
                + ", shape=" + shape + ", imageFileName=" + imageFileName + "]";
    }
}
