package hu.oe.nik.szfmv.visualisation;

import java.util.HashMap;

public class RoadConstants {
    public static final int roadWidth = 350;

    public static final HashMap<String, Coord> roadOffsets;

    static {
        roadOffsets = new HashMap<String, Coord>();
        roadOffsets.put(
                "road_2lane_45left.png",
                new Coord(401 - RoadConstants.roadWidth, 371));
        roadOffsets.put(
                "road_2lane_45right.png",
                new Coord(RoadConstants.roadWidth, 371));
        roadOffsets.put(
                "road_2lane_90left.png",
                new Coord(525 - RoadConstants.roadWidth, 525));
        roadOffsets.put(
                "road_2lane_90right.png",
                new Coord(RoadConstants.roadWidth, 525));
        roadOffsets.put(
                "road_2lane_6left",
                new Coord(367 - RoadConstants.roadWidth, 367));
        roadOffsets.put(
                "road_2lane_6right",
                new Coord(RoadConstants.roadWidth, 367));
        roadOffsets.put(
                "road_2lane_rotary.png",
                new Coord(0, 1400 / 2 + 350 / 2));
        roadOffsets.put(
                "road_2lane_straight.png",
                new Coord(0, 0));
        roadOffsets.put(
                "road_2lane_tjunctionleft.png",
                new Coord(875, 0));
        roadOffsets.put(
                "road_2lane_tjunctionright.png",
                new Coord(0, 0));
        roadOffsets.put(
                "2_crossroad_1.png",
                new Coord(0, 1400 / 2 + 350 / 2));
        roadOffsets.put(
                "2_crossroad_2.png",
                new Coord(0, 1400 / 2 + 350 / 2));
    }
}
