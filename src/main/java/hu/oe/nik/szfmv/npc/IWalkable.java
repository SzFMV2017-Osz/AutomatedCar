package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;

public interface IWalkable extends IControllable{

    public void moveTo(Vector2D target);

    public double getMaxSpeed();

    public void setMaxSpeed(double maxSpeed);
}
