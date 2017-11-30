package hu.oe.nik.szfmv.environment.object;

import hu.oe.nik.szfmv.npc.IControllable;

public interface IWalkable extends IControllable{

    public double getMaxSpeed();

    public void setMaxSpeed(double maxSpeed);
}
