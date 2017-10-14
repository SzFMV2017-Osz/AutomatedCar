package hu.oe.nik.szfmv.environment.xml;

/**
 * helper to convert xml type attributes to checked enum 
 * 
 * @author kalmankostenszky
 *
 */
public enum XmlObjectType {
	
	ROAD_STRAIGHT("road_2lane_straight"),
	ROAD_90_RIGHT("road_2lane_90right"),
	ROAD_90_LEFT("road_2lane_90left"),
	ROAD_45_RIGHT("road_2lane_45right"),
	ROAD_45_LEFT("road_2lane_45left"),
	ROAD_6_RIGHT("road_2lane_6right"),
	ROAD_6_LEFT("road_2lane_6left"),
	ROAD_T_RIGHT("road_2lane_tjunctionright"),
	ROAD_T_LEFT("road_2lane_tjunctionleft"),
	ROAD_ROUNDABOUT("road_2lane_rotary"),
	ROAD_PARKING_PARALLEL("parking_space_parallel"),
	ROAD_CROSSWALK("crosswalk"),
	
	TREE("tree"),
	
	ROADSIGN_SPEED_60("roadsign_speed_60"),
	ROADSIGN_SPEED_50("roadsign_speed_50"),
	ROADSIGN_SPEED_40("roadsign_speed_40"),
	ROADSIGN_STOP("roadsign_priority_stop"),
	ROADSIGN_PARKING_RIGHT("roadsign_parking_right");
	
	private String xmlName;
	
	private XmlObjectType(String name) {
		this.xmlName = name;
	}
	
	/*
	 * the name of the enum as represented in the xml attribute
	 * 
	 */
	public String getXmlName() {
		return xmlName;
	}
	
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
		throw new IllegalArgumentException("Enum to xmlName "+xmlName+" does not exist");
	}
	
}
