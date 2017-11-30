package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;

public interface IControllable {
    void move();
    Vector2D getPosition();
    Vector2D getForwardVector();
    void setRotation(float rotation);
    public void moveTo(Vector2D target);
}
