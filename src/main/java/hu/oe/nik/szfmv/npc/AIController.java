package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.environment.model.MovingObject;

public abstract class AIController {

    protected IControllable controlledObject;

    public AIController() {
    }

    public AIController(IControllable object) {
        controlledObject = object;
    }

    public void tick() {

    }

    public IControllable getControlledObject() {
        return controlledObject;
    }

    public void setControlledObject(IControllable object) {
        this.controlledObject = object;
    }
}
