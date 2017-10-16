package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class Pedestrian extends MovingObject implements IWalkable {

    private double maxSpeed;
    // TODO: include in WorldObject
    private Vector2D position;

    Pedestrian (int x, int y, float rotation, int width, int height, String imageFileName, int weight, ModelShape shape) {
        super(x, y, rotation, width, height, imageFileName, weight, shape);
        position = new Vector2D(x, y);
    }

    @Override
    protected void doOnCollision() {
    }

    @Override
    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    @Override
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public Vector2D getPosition() {
//        return new Vector2D(getX(), getY());
        return position;
    }

    @Override
    public Vector2D getForwardVector() {
        return Vector2D.getForwardVector(getRotation());
    }

    @Override
    public void moveTo(Vector2D target) {
        if (!getPosition().equals(target)) {
            Vector2D direction = target.copy().sub(getPosition());
            if ((maxSpeed * maxSpeed) < direction.absSquared()) {
                chageDirection(direction.copy().normalize().mult(maxSpeed).sub(getCurrentSpeed()));
            } else {
                chageDirection(direction.copy().sub(getCurrentSpeed()));
            }
            move();
        }
    }

    @Override
    public void move() {
        position.add(getCurrentSpeed().copy());
    }
}
