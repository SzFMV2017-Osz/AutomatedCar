package hu.oe.nik.szfmv.visualisation;

import java.util.HashMap;
import java.util.Map;

public class RoadConstants {

    public static final int roadWidth = 350;

    private static final HashMap<String, Coord> roadOffsets;
    public final HashMap<String, Coord> scaledRoadOffsets;

    public RoadConstants(double scale) {
	scaledRoadOffsets = new HashMap<String, Coord>();

	for (Map.Entry<String, Coord> entry : roadOffsets.entrySet()) {
	    String key = entry.getKey();
	    Coord value = entry.getValue();

	    scaledRoadOffsets.put(key, new Coord((int) (value.getX() * scale), (int) (value.getY() * scale)));
	}
    }

    static {
	roadOffsets = new HashMap<String, Coord>();
	roadOffsets.put("road_2lane_45left.png", new Coord(401 - RoadConstants.roadWidth, 371));
	roadOffsets.put("road_2lane_45right.png", new Coord(RoadConstants.roadWidth, 371));
	roadOffsets.put("road_2lane_90left.png", new Coord(525 - RoadConstants.roadWidth, 525));
	roadOffsets.put("road_2lane_90right.png", new Coord(RoadConstants.roadWidth, 525));
	roadOffsets.put("road_2lane_6left", new Coord(367 - RoadConstants.roadWidth, 367));
	roadOffsets.put("road_2lane_6right", new Coord(RoadConstants.roadWidth, 367));
	roadOffsets.put("road_2lane_rotary.png", new Coord(0, 1400 / 2 + 350 / 2));
	roadOffsets.put("road_2lane_straight.png", new Coord(0, 0));
	roadOffsets.put("road_2lane_tjunctionleft.png", new Coord(875, 0));
	roadOffsets.put("road_2lane_tjunctionright.png", new Coord(0, 0));
	roadOffsets.put("2_crossroad_1.png", new Coord(0, 1400 / 2 + 350 / 2));
	roadOffsets.put("2_crossroad_2.png", new Coord(0, 1400 / 2 + 350 / 2));
    }
}
