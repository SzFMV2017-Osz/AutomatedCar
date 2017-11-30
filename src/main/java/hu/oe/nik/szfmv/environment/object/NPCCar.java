package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.npc.IMovable;

import java.util.ArrayList;
import java.util.List;

public class NPCCar extends MovingObject implements IMovable {

    private Vector2D velocity = new Vector2D();
    private double mass;
    private double maxSpeed;
    private double maxTurnAngle;
    private Vector2D position;


    //ez új
    List<Vector2D> Points = new ArrayList<>();
    Vector2D actualTarget;


    public NPCCar(int x, int y, float rotation, int width, int height, String imageFileName, int weight) {
        super(x, y, rotation, width, height, imageFileName, weight, ModelShape.RECTANGULAR);
        position = new Vector2D(x, y);
        maxSpeed = 50;
    }

    @Override
    public void move()
    {
        position =position.add(getCurrentSpeed());
        //this.x = (int) position.getX();
        //this.y = (int) position.getY();
    }
    public void moveTo(Vector2D target)
    {
        if (!getPosition().equals(target)) {
            Vector2D direction = target.copy().sub(getPosition());
            if ((maxSpeed * maxSpeed) < direction.absSquared()) {
                changeDirection(direction.copy().normalize().mult(maxSpeed).sub(getCurrentSpeed()));
            } else {
                changeDirection(direction.copy().sub(getCurrentSpeed()));
            }
            move();
        }

    }

    public void setPoints(List<Vector2D> points) {
        Points = points;
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
    public void setRotation(float rotation) {
        this.setRotation(rotation);
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    protected void doOnCollision() {

    }
}
