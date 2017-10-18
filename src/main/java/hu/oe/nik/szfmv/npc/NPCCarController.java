package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Road;

import java.util.ArrayList;
import java.util.List;

public class NPCCarController extends AIController {

   /* Vector2D target = new Vector2D(500, 0);*/
    World world;
    List<WorldObject> roads = new ArrayList<>();
    private List<Vector2D> pathPoints = new ArrayList<>();
    private int currentTarget;
    public NPCCarController(IControllable object,World world) {
        super(object);
//        this.target = target;
        this.world =world;
        currentTarget = 0;
        GetRoadObjects(world);
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
    public void nextTarget() {
        currentTarget = ++currentTarget % pathPoints.size();
    }
    public boolean isAtCurrentTarget() {
        if (pathPoints.get(currentTarget).equals(getControlledObject().getPosition())) {
          controlledObject.setRotation((float)3.14);
            return true;
        }
        return false;
    }
    void GetRoadObjects(World world)
    {
        for (WorldObject item :world.getWorldObjects()
             ) {
                if (item.getImageFileName().contains("road_2lane"))
            {
                roads.add(item);
            }
        }
        pathPoints.add(new Vector2D(roads.get(27).getX(),roads.get(27).getY()));
        pathPoints.add(new Vector2D(roads.get(25).getX(),roads.get(25).getY()));
        pathPoints.add(new Vector2D(roads.get(1).getX(),roads.get(1).getY()));
        pathPoints.add(new Vector2D(roads.get(6).getX(),roads.get(6).getY()));
        pathPoints.add(new Vector2D(roads.get(8).getX(),roads.get(8).getY()));
        pathPoints.add(new Vector2D(roads.get(23).getX(),roads.get(23).getY()));
    }
}
