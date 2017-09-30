package hu.oe.nik.szfmv.environment.model;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.util.Vector;

/**
 * Mozgó objektumokat reprezentáló osztály
 * TODO: maximális sebesség, min-max gyorsulás beépítése
 * @author hunkak
 *
 */
public abstract class MovingObject extends CollidableObject {
	
	protected static final Logger log = LogManager.getLogger(MovingObject.class);
	
	//objektum pillantnyi sebess�ge
	private Vector currentSpeed;
	
	public MovingObject(int x, int y, float rotation, int width, int height,String imageFileName, int weight, ModelShape shape) {
		super(x, y, rotation, width, height, imageFileName, weight, shape);
		// TODO Auto-generated constructor stub
	}
	
	public void move() {
		log.debug("MovingObject#move invoked");
		//TODO: vektor irányába történő elmozdulást implementálni
		this.x += 5;
		this.y += 5;
	};
	
	protected void chageDirection(List<Vector> vectors) {
		log.debug("MovingObject#chageDirection invoked");
		//TODO: irányváltást implementálni (vektorok összeadása)
		this.currentSpeed.setAngle(0);
		this.currentSpeed.setMagnitude(0);
	}

	/**
	 * Motgzó objeltum impulzusa
	 * @return vektor melynek nagysága az impulzus nagysága, iránya egyezik az objektum sebességének irányával (szögben)
	 */
	public Vector getImpulse() {
		return new Vector(this.currentSpeed.getAngle(), 
				this.currentSpeed.getMagnitude()*this.getWeight());
	}
	/**
	 * Az objektum pillanatnyi sebessége
	 * @return vektor melynek nagysága a pillanatnyi sebesség nagysága, iránya a pillanatnyi sebesség iránya (szögben)
	 */
	public Vector getCurrentSpeed() {
		return currentSpeed;
	}
	
}
