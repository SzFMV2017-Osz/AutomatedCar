package hu.oe.nik.szfmv.npc;

public abstract class AIController {

    private IMovable movableObject;

    public AIController() {
    }

    public AIController(IMovable object) {
        movableObject = object;
    }

    public void tick() {

    }

    public IMovable getMovableObject() {
        return movableObject;
    }

    public void setMovableObject(IMovable movableObject) {
        this.movableObject = movableObject;
    }
}
