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
    List<CirclePath> referencceCircles = new ArrayList<>();
    public NPCCarController(IControllable object,World world) {
        super(object);
//        this.target = target;
        this.world =world;
        GetRoadObjects(world);
    }

    @Override
    public void tick() {
        getControlledObject().move();



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
        for (WorldObject item: roads
             ) {
            if (item.getImageFileName().contains("90left"))
            {
                referencceCircles.add(new CirclePath(item.getX(),item.getY(),116));
            }
            if (item.getImageFileName().contains("90right"))
            {

            }
            if (item.getImageFileName().contains("45left"))
            {

            }
            if (item.getImageFileName().contains("45right"))
            {

            }
        }
    }
}
