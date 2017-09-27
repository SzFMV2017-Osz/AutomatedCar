package hu.oe.nik.szfmv.environment;

import java.util.List;

import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * Mozgó objektumokat reprezentáló osztály
 * TODO: maximális sebesség, min-max gyorsulás beépítése
 * @author hunkak
 *
 */
public abstract class MovingObject extends CollidableObject {
	//objektum pillantnyi sebess�ge
	Vector currentSpeed;
	
	public MovingObject(int x, int y, float rotation, int width, int height, int weight) {
		super(x, y, rotation, width, height, weight);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Objektum mozgatása a síkon sbesség és/vagy irány megváltoztatásával
	 * Úgy kell implementálni, hogy az objektum a saját pillanatnyi sebességének
	 * és a ráható sebesség vektorok eredőjének irányába mozduljon
	 * 
	 * @param vectors objektumra ható sebességvektorok
	 */
	public abstract void move(List<Vector> vectors);
	
	/**
	 * mozg�s az objektum pillanatnyi sebess�g�nek megelel� ir�nyba
	 */
	public abstract void move();

	public Vector getCurrentSpeed() {
		return currentSpeed;
	}

}
