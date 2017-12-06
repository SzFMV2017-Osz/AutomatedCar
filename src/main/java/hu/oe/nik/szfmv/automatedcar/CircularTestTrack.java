package hu.oe.nik.szfmv.automatedcar;

public final class CircularTestTrack {

    // Circular track attributes
    private final static double CIRCULAR_TRACK_LENGTH = 1080;
    private final static int CENTER_X = 340;
    private final static int CENTER_Y = 177;
    private final static double TRACK_RADIUS = 172;

    // Getter for X
    public static int getX(double positionOnTrack) {
        // Calculating x
        return CENTER_X
                + (int) Math.round(TRACK_RADIUS * Math.sin(positionOnTrack * 2. * Math.PI / CIRCULAR_TRACK_LENGTH));
    }

    // Getter for Y
    public static int getY(double positionOnTrack) {
        // Calculating y
        return CENTER_Y
                - (int) Math.round(TRACK_RADIUS * Math.cos(positionOnTrack * 2. * Math.PI / CIRCULAR_TRACK_LENGTH));
    }
}
