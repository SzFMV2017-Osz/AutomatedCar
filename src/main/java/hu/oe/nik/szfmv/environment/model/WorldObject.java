package hu.oe.nik.szfmv.environment.model;

import java.awt.*;
import java.awt.geom.*;

import hu.oe.nik.szfmv.environment.xml.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.detector.ICameraSensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Világ elemeinek ős osztálya
 *
 * @author hunkak
 */
public abstract class WorldObject implements ICameraSensor {

    private static final Logger log = LogManager.getLogger(WorldObject.class);

    // objektum helyzete a síkon
    // TODO meghatározni, hogy az objektum melyik pontja - vizualizációs
    // csapattal
    double x;

    double y;
    // objektum forgatása
    // TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
    final double rotation;

    // objektum kiterjedése
    // TODO befoglaló négyszög? implementációs függő jelentés?
    private final int width, height;

    // objektum formája
    private final ModelShape shape;

    private final String imageFileName;

    public WorldObject(double x, double y, double rotation, int width, int height, String imageName, ModelShape shape) {
        super();

        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.imageFileName = imageName;
        this.width = width;
        this.height = height;
        this.shape = shape;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public String getImageFileName() {
        return imageFileName;
    }

    public Shape getShape() {
        Shape tempShape = null;
        switch (this.shape) {
            case ELLIPSE:
                tempShape = new Ellipse2D.Double(this.x, this.y, this.width, this.height);
                break;
            case RECTENGULAR:
                tempShape = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
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
        return "WorldObject [x=" + x + ", y=" + y + ", rotation=" + rotation + ", width=" + width + ", height=" + height
                + ", shape=" + shape + ", imageFileName=" + imageFileName + "]";
    }
}
