package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;

public interface IMovable {

    void move();
    double getMaxSpeed();
    double getMaxTurnAngle();
    double getMass();
    Vector2D getPosition();
    Vector2D getForwardVector();
    Vector2D getVelocity();
}
