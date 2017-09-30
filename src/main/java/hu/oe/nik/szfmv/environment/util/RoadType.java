package hu.oe.nik.szfmv.environment.util;

/**
 * lehets�ges �t t�pusok �s azok jellemz�i
 * TODO: viszony�t�si pont k�rd�se az ir�ny
 * @author hunkak
 * 
 * Osztályokkal kell megvalósítani
 */
@Deprecated()
public enum RoadType {

	STRAIGHT_2LANE(0,0),
	LEFT_45_2LANE(45,-1),
	RIGHT_45_2LANE(45,1),
	T_JUNCTION(0,0),
	CROSS_ROAD(0,0);
	
	public static final int STRAIGHT_ROAD = 0;
	public static final int LEFT_TURN = -1;
	public static final int RIGHT_TURN = 1;
	
	private RoadType(int angle, int direction){
		this.angle = angle;
		this.direction = direction;
	}
	
	private final int angle;
	private final int direction;

	public int getAngle() {
		return angle;
	}

	public int getDirection() {
		return direction;
	}

	
	
	
	
}
