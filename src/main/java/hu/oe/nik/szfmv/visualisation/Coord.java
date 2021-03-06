package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.model.WorldObject;

public class Coord {
    public static final Coord origoPoint = new Coord(0, 0);
    private double x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord(WorldObject wo) {
        this.x = wo.getX();
        this.y = wo.getY();
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setValue(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Coord) && ((Coord) obj).x == this.x && ((Coord) obj).y == this.y;
    }
}
