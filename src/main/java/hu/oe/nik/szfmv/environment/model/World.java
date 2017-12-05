package hu.oe.nik.szfmv.environment.model;

import java.util.ArrayList;
import java.util.List;

public class World {

    private int width = 0;
    private int height = 0;
    private List<WorldObject> worldObjects = new ArrayList<>();
    private WorldObjectCollection worldObjectsFiltered = new WorldObjectCollection();

    public World(int width, int height) {
	this.width = width;
	this.height = height;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public List<WorldObject> getWorldObjects() {
	return worldObjects;
    }

    public void addObjectToWorld(WorldObject o) {
	worldObjects.add(o);
	worldObjectsFiltered.add(o);
    }

    public void removeObjectFromWorld(WorldObject o) {
	worldObjects.remove(o);
	worldObjectsFiltered.remove(o);
    }

    public WorldObjectCollection getWorldObjectsFiltered() {
	return worldObjectsFiltered;
    }
}
