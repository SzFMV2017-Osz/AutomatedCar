package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.WorldObject;

public class Pedestrian extends WorldObject implements IMovable {

    private Vector2D dest1;
    private Vector2D dest2;
    private Vector2D actualDest;
    private boolean toDest2;
    private double mass;
    private double maxSpeed;
    private double maxTurnAngle;
    private Vector2D velocity = new Vector2D();

    public Pedestrian(int x, int y, Vector2D dest1, Vector2D dest2, String imageFileName) {
        super(x, y, imageFileName);
        this.dest1 = dest1;
        this.dest2 = dest2;
        toDest2 = true;
        this.actualDest = dest2;
    }

    public Pedestrian(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        dest1 = new Vector2D();
        dest2 = new Vector2D();
        actualDest = dest1;
    }

    @Override
    public void move(Vector2D target) {
        if (!getPosition().equals(target)) {
            Vector2D direction = target.copy().sub(getPosition());
            if ((maxSpeed * maxSpeed) < direction.absSquared()) {
                moveTowardsTarget(direction);
            } else {
                moveToTarget(target, direction);
            }
        } else {
            velocity.setX(0);
            velocity.setY(0);
        }
    }

    private void moveToTarget(Vector2D target, Vector2D direction) {
        velocity = direction;
        x = (int) target.getX();
        y = (int) target.getY();
        rotation = (float) velocity.getAngle();
    }

    private void moveTowardsTarget(Vector2D direction) {
        Vector2D newPosition;
        velocity = direction.copy().normalize().mult(maxSpeed);
        newPosition = getPosition().add(velocity);
        x = (int) newPosition.getX();
        y = (int) newPosition.getY();
        rotation = (float) direction.getAngle();
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
        return this.maxSpeed;
    }

    @Override
    public double getMaxTurnAngle() {
        return this.maxTurnAngle;
    }

    @Override
    public double getMass() {
        return this.mass;
    }

    @Override
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void setMaxTurnAngle(double maxTurnAngle) {
        this.maxTurnAngle = maxTurnAngle;
    }

    @Override
    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(this.x, this.y);
    }

    @Override
    public Vector2D getForwardVector() {
        return Vector2D.getForwardVector(this.rotation);
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }
}
