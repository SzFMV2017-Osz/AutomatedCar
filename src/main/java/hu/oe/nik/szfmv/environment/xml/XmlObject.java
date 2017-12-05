package hu.oe.nik.szfmv.environment.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XmlObject {

    public static final Logger log = LogManager.getLogger(XmlObject.class);

    /**
     * get the builder utility of XmlObject class
     *
     * @return the builder
     */
    public static XmlObjectBuilder builder() {
	return new XmlObject.XmlObjectBuilder();
    }

    private XmlObjectType type;
    private int x;
    private int y;
    private float rotation;

    private XmlObject() {

    }

    /**
     * the checked type of the xml object
     *
     * @return
     */
    public XmlObjectType getType() {
	return type;
    }

    /**
     * the x coordinate in pixels
     *
     * @return
     */
    public int getX() {
	return x;
    }

    /**
     * the y coordinate in pixels
     *
     * @return
     */
    public int getY() {
	return y;
    }

    /**
     * the rotation in angles IMPORTANT: 0° is horizontal right
     *
     * @return
     */
    public float getRotation() {
	return rotation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "XmlObject [type=" + type.toString() + ", x=" + x + ", y=" + y + ", rotation=" + rotation + "]";
    }

    /**
     * Applying builder pattern to build XmlObject with: - transformed rotation
     * in degrees - type checked DOM objects
     *
     * @author kalmankostenszky
     *
     */
    public static class XmlObjectBuilder {

	private boolean typeSet;
	private boolean rotationSet;
	private boolean positionSet;

	private XmlObject instance = new XmlObject();

	private XmlObjectBuilder() {
	    instance = new XmlObject();
	    typeSet = false;
	    rotationSet = false;
	    positionSet = false;
	    log.info("creating new XmlObject");
	}

	/**
	 * converts the string xml type attribute to type checked XmlObjectType
	 *
	 * @throws IllegalArgumentException if cannoit convert type attribute to
	 * enum
	 *
	 * @param type
	 * @return
	 */
	public XmlObjectBuilder type(String type) {

	    XmlObjectType x = XmlObjectType.getXmlObjectTypeOf(type);
	    instance.type = x;
	    typeSet = true;
	    log.info("setting type to " + x);
	    return this;
	}

	/**
	 * sets the position of the XmlObject
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public XmlObjectBuilder position(int x, int y) {
	    instance.x = x;
	    instance.y = y;
	    positionSet = true;
	    log.info("setting position to {" + x + "," + y + "}");
	    return this;
	}

	/**
	 * sets the position of the XmlObject
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public XmlObjectBuilder position(String x, String y) {
	    return position(Integer.parseInt(x), Integer.parseInt(y));
	}

	/**
	 * set the rotation of the XmlObject from 2x2 matrix to angle in degrees
	 *
	 * @param rotation
	 * @return
	 */
	public XmlObjectBuilder rotation(double[][] rotation) {
	    instance.rotation = (float) Utils.convertMatrixToRadians(rotation);
	    rotationSet = true;
	    log.info("setting rotation from {{" + rotation[0][0] + "," + rotation[0][1] + "},{" + rotation[1][0] + ","
		    + rotation[1][1] + "}} to " + instance.rotation);
	    return this;
	}

	/**
	 * set the rotation of the XmlObject from 2dmatrix to angle in degrees
	 *
	 * @param m11 [0][0]
	 * @param m12 [0][1]
	 * @param m21 [1][0]
	 * @param m22 [1][1]
	 * @return
	 */
	public XmlObjectBuilder rotation(double m11, double m12, double m21, double m22) {
	    return rotation(new double[][]{{m11, m12}, {m21, m22}});
	}

	/**
	 * returns the instence created from the xml
	 *
	 * @return
	 * @throws IllegalArgumentException if one of the parameters not set
	 */
	public XmlObject build() {
	    if (!typeSet || !rotationSet || !positionSet) {
		String message = "Can not create XmlObject because " + (!typeSet ? " type " : "")
			+ (!rotationSet ? " rotation " : "") + (!positionSet ? " position " : "") + " is not set";
		log.error("can not build XmlObject: " + message);
		throw new IllegalArgumentException(message);

	    }
	    log.info("building XmlObject instance");
	    return instance;
	}

    }
}
