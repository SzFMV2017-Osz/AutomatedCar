package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;

import java.util.ArrayList;
import java.util.List;

public class PedestrianController extends AIController {

    private List<Vector2D> pathPoints;
    private int currentTarget;

    public PedestrianController() {
        pathPoints = new ArrayList<>();
    }

    public PedestrianController(IControllable controlledPedestrian) {
        super(controlledPedestrian);
        pathPoints = new ArrayList<>();
    }

    public PedestrianController(IControllable controlledPedestrian, List<Vector2D> pathPoints) throws Exception {
        super(controlledPedestrian);
        this.pathPoints = pathPoints;
        currentTarget = 0;
        if (pathPoints == null) {
            throw new NullPointerException("pathPoints parameter has not been initialized!");
        }
    }

    @Override
    public IWalkable getControlledObject() {
        return (IWalkable) this.controlledObject;
    }

    @Override
    public void tick() {
        if (getControlledObject() == null) {
            throw new NullPointerException("No IMovable object has been set!");
        }
        if (isAtCurrentTarget()) {
                nextTarget();
        }
        getControlledObject().moveTo(pathPoints.get(currentTarget));
    }

    public Vector2D getCurrentTarget() {
        return pathPoints.get(currentTarget);
    }

    public void nextTarget() {
        currentTarget = ++currentTarget % pathPoints.size();
    }

    public boolean isAtCurrentTarget() {
        if (pathPoints.get(currentTarget).equals(getControlledObject().getPosition())) {
//            TODO: Forduljon meg ha átért a zebrán.
//            controlledObject.setRotation((float)3.14);
            return true;
        }
        return false;
    }
}
