package hu.oe.nik.szfmv.environment.util;

/**
 * sebességet ábrázoló osztáy
 * 
 * @author hunkak
 *
 */
public class Vector {

	// sebesség vektor szöge
	// TODO: mi legyen a mi legeyen a mértékegysége és a bázis?
	float angle;
	// sebesség nagysága
	int magnitude;

	public Vector(float angle, int magnitude) {
		super();
		this.angle = angle;
		this.magnitude = magnitude;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public int getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(int magnitude) {
		this.magnitude = magnitude;
	}

}
