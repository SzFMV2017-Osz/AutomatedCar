package hu.oe.nik.szfmv.environment.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * helper to convert xml type attributes to checked enum 
 * 
 * @author kalmankostenszky
 *
 */
public enum XmlObjectType {	
	
    ROAD_STRAIGHT("road_2lane_straight",0),
    ROAD_90_RIGHT("road_2lane_90right",0),
    ROAD_90_LEFT("road_2lane_90left",0),
    ROAD_45_RIGHT("road_2lane_45right",0),
    ROAD_45_LEFT("road_2lane_45left",0),
    ROAD_6_RIGHT("road_2lane_6right",0),
    ROAD_6_LEFT("road_2lane_6left",0),
    ROAD_T_RIGHT("road_2lane_tjunctionright",0),
    ROAD_T_LEFT("road_2lane_tjunctionleft",0),
    ROAD_ROUNDABOUT("road_2lane_rotary",0),
    ROAD_PARKING_PARALLEL("parking_space_parallel",0),
    ROAD_CROSSWALK("crosswalk",0),

    TREE("tree",99),

    ROADSIGN_SPEED_60("roadsign_speed_60",1),
    ROADSIGN_SPEED_50("roadsign_speed_50",1),
    ROADSIGN_SPEED_40("roadsign_speed_40",1),
    ROADSIGN_STOP("roadsign_priority_stop",1),
    ROADSIGN_PARKING_RIGHT("roadsign_parking_right",1);

    private String xmlName;
    private int rootType;

    private XmlObjectType(String name, int rootType) {
        this.xmlName = name;
        this.rootType = rootType;
    }

    /*
     * the name of the enum as represented in the xml attribute
     * 
     */
    public String getXmlName() {
        return xmlName;
    }

    public int getRootType() {
        return rootType;
    }

    private static final Logger log = LogManager.getLogger(XmlObjectType.class);

    public static final int ROAD_TYPE = 0;
    public static final int ROADSIGN_TYPE = 1;
    public static final int MISC_TYPE = 99;

    /**
     * casts the xml attribute to enum
     * 
     * @param xmlName
     * @return
     * @throws IllegalArgumentException if type does not exists
     */
    public static XmlObjectType getXmlObjectTypeOf(String xmlName) {
        for (XmlObjectType x : values()) {
                if (x.xmlName.equals(xmlName)) {
                        return x;
                }
        }
        String message = "Enum to xmlName "+xmlName+" does not exist";
        log.error(message);
        throw new IllegalArgumentException("Enum to xmlName "+message+" does not exist");
    }
	
}
