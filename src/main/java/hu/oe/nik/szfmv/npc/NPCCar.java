package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class NPCCar extends MovingObject implements IMovable {

    private Vector2D velocity = new Vector2D();
    private double mass;
    private double maxSpeed;
    private double maxTurnAngle;

    public NPCCar(int x, int y, float rotation, int width, int height, String imageFileName, int weight, ModelShape shape) {
        super(x, y, rotation, width, height, imageFileName, weight, shape);
    }

    @Override
    public void move(Vector2D target) {
        // TODO: implement car movement
    }

    @Override
    public double getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public double getMaxTurnAngle() {
        return maxTurnAngle;
    }

    @Override
    public double getMass() {
        return mass;
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

    @Override
    protected void doOnCollision() {

    }
}
