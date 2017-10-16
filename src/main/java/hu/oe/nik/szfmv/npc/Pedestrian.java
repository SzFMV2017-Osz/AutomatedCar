package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class Pedestrian extends MovingObject implements IMovable {

    private double mass;
    private double maxSpeed;
    private double maxTurnAngle;
    private Vector2D velocity = new Vector2D();


    Pedestrian (int x, int y, float rotation, int width, int height, String imageFileName, int weight, ModelShape shape) {
        super(x, y, rotation, width, height, imageFileName, weight, shape);
    }

    @Override
    protected void doOnCollision() {

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
        // TODO: fix is to the new model
//        x = (int) target.getX();
//        y = (int) target.getY();
//        rotation = (float) velocity.getAngle();
    }

    private void moveTowardsTarget(Vector2D direction) {
        Vector2D newPosition;
        velocity = direction.copy().normalize().mult(maxSpeed);
        newPosition = getPosition().add(velocity);
        // TODO: fix is to the new model
//        x = (int) newPosition.getX();
//        y = (int) newPosition.getY();
//        rotation = (float) direction.getAngle();
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
        return new Vector2D(getX(), getY());
    }

    @Override
    public Vector2D getForwardVector() {
        return Vector2D.getForwardVector(getRotation());
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }
}
