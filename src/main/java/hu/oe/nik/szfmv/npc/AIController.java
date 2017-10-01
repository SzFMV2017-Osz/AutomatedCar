package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.environment.WorldObject;

public abstract class AIController {

    protected IMovable movableObject;

    public AIController() {
    }

    public AIController(IMovable object) {
        movableObject = object;
    }

    public void tick(double deltaTime) {
        movableObject.move();
    }

    public IMovable getMovableObject() {
        return movableObject;
    }

    public void setMovableObject(IMovable movableObject) {
        this.movableObject = movableObject;
    }
}
