package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class PedestrianController extends AIController {

    private List<Vector2D> pathPoints;
    private int currentTarget;

    public PedestrianController() {
        pathPoints = new ArrayList<>();
    }

    public PedestrianController(IMovable movablePedestrian) {
        super(movablePedestrian);
        pathPoints = new ArrayList<>();
    }


    public PedestrianController(IMovable movablePedestrian, List<Vector2D> pathPoints) throws Exception {
        super(movablePedestrian);
        this.pathPoints = pathPoints;
        currentTarget = 0;
        if (pathPoints == null) {
            throw new NullPointerException("pathPoints parameter has not been initialized!");
        }
    }

    @Override
    public void tick() {
        if (getMovableObject() == null) {
            throw new NullPointerException("No IMovable object has been set!");
        }
        if (isAtCurrentTarget()) {
            nextTarget();
        }
        getMovableObject().move(pathPoints.get(currentTarget));
    }

    public Vector2D getCurrentTarget() {
        return pathPoints.get(currentTarget);
    }

    public void nextTarget() {
        currentTarget = ++currentTarget % pathPoints.size();
    }

    public boolean isAtCurrentTarget() {
        if (pathPoints.get(currentTarget).equals(getMovableObject().getPosition())) {
            return true;
        }
        return false;
    }
}
