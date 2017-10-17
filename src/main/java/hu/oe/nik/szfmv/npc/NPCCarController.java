package hu.oe.nik.szfmv.npc;

import hu.oe.nik.szfmv.common.Vector2D;

public class NPCCarController extends AIController {

    Vector2D target = new Vector2D(500, 0);

    @Override
    public void tick() {
        getControlledObject().move();

        // TODO: implement NPCCarController tick method

    }
}
