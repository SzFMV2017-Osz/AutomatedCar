package hu.oe.nik.szfmv.environment.model;

import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Sensor;

import java.util.ArrayList;

public class WorldObjectCollection {
    private ArrayList<WorldObject> unmoving = new ArrayList<>();
    private ArrayList<WorldObject> collidable = new ArrayList<>();
    private ArrayList<WorldObject> moving = new ArrayList<>();
    private ArrayList<WorldObject> cars = new ArrayList<>();
    private ArrayList<WorldObject> sensors = new ArrayList<>();

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

    public ArrayList<WorldObject> getSensors() {
        return sensors;
    }

    public void add(WorldObject object) {
        if (object instanceof CollidableObject) {
            if (object instanceof MovingObject) {
                if (object instanceof Car) {
                    cars.add(object);
                } else moving.add(object);
            } else collidable.add(object);
        } else {
            if (object instanceof Sensor) {
                sensors.add(object);
            } else unmoving.add(object);
        }
    }

    public void remove(WorldObject object) {
        if (object instanceof CollidableObject) {
            if (object instanceof MovingObject) {
                if (object instanceof Car) {
                    cars.remove(object);
                } else
                    moving.remove(object);
            } else
                collidable.remove(object);
        } else { // roads
            unmoving.remove(object);
        }
    }
}
