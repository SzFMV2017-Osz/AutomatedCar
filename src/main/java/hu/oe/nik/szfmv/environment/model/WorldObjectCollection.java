package hu.oe.nik.szfmv.environment.model;

import hu.oe.nik.szfmv.environment.object.Car;

import java.util.ArrayList;

public class WorldObjectCollection {
    private ArrayList<WorldObject> unmoving = new ArrayList<>();
    private ArrayList<WorldObject> collidable = new ArrayList<>();
    private ArrayList<WorldObject> moving = new ArrayList<>();
    private ArrayList<WorldObject> cars = new ArrayList<>();

    public ArrayList<WorldObject> getUnmoving() {
        return unmoving;
    }

    public ArrayList<WorldObject> getCollidable() {
        return collidable;
    }

    public ArrayList<WorldObject> getMoving() {
        return moving;
    }

    public ArrayList<WorldObject> getCars() {
        return cars;
    }

    public void add(WorldObject object) {
        if (object instanceof CollidableObject) {
            if (object instanceof MovingObject) {
                if (object instanceof Car) {
                    cars.add(object);
                } else
                    moving.add(object);
            } else
                collidable.add(object);
        } else { // roads
            unmoving.add(object);
        }
    }
}
