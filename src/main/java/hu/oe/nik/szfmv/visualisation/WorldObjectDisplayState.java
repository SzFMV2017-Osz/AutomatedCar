package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.model.WorldObject;

import java.awt.geom.AffineTransform;

//preserves a worldObject and its state
class WorldObjectDisplayState {
    private WorldObject wo;
    private Coord c;
    private double rot;
    private AffineTransform t;

    private WorldObjectDisplayState(WorldObject wo, Coord c, double rot, AffineTransform t) {
        this.wo = wo;
        this.c = c;
        this.rot = rot;
        this.t = t;
    }

    public static WorldObjectDisplayState createState(WorldObject object, AffineTransform t) {
        return new WorldObjectDisplayState(
                object,
                new Coord(object),
                object.getRotation(), t);
    }

    public AffineTransform getTransform() {
        return t;
    }

    public boolean isChanged() {
        return !(wo.getX() == c.getX()
                && wo.getY() == c.getY()
                && wo.getRotation() == rot);
    }

    public void updateState(AffineTransform t) {
        this.t = t;
        this.c.setValue(wo.getX(), wo.getY());
        this.rot = wo.getRotation();
    }
}
