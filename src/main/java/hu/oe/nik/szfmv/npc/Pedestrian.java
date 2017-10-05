package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.WorldObject;

public class Pedestrian extends WorldObject implements IMovable {

    private Vector2D dest1;
    private Vector2D dest2;
    private Vector2D actualDest;
    private boolean toDest2;

    public Pedestrian(int x, int y, Vector2D dest1, Vector2D dest2, String imageFileName) {
        super(x, y, imageFileName);
        this.dest1 = dest1;
        this.dest2 = dest2;
        toDest2 = true;
        this.actualDest = dest2;
    }

    @Override
    public void move() {
        switchDestinations();
        if (actualDest.getX() == this.x) {
            if ((actualDest.getY() - this.y) > 0) {
                this.y++;
            } else {
                this.y--;
            }
        } else if (actualDest.getY() == this.y) {
            if ((actualDest.getX() - this.x) > 0) {
                this.x++;
            } else {
                this.x--;
            }
        }
    }

    private void switchDestinations() {
        if (toDest2 && this.x == actualDest.getX() && this.y == actualDest.getY()) {
            toDest2 = false;
            actualDest = dest1;
        } else if (this.x == actualDest.getX() && this.y == actualDest.getY()) {
            toDest2 = true;
            actualDest = dest2;
        }
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public double getMaxSpeed() {
        return 0;
    }

    @Override
    public double getMaxTurnAngle() {
        return 0;
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(this.x, this.y);
    }

    @Override
    public Vector2D getForwardVector() {
        return null;
    }

    @Override
    public Vector2D getVelocity() {
        return null;
    }
}
