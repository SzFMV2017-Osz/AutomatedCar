package hu.oe.nik.szfmv.environment;
/**
 * Ütközni képes objektumokat reprezentáló osztály
 * TODO: ütközés detektálás kialakítása (- vizualizációs csapattal egyeztetni)
 * @author hunkak
 *
 */
public abstract class CollidableObject extends WorldObject {
	
	//mozgó objektum tömege impulzus számításhoz
	private final int weight;
	//objektum ütközött-e már
	//TODO: bináris vagy skálázható érték legyen?
	private boolean collided;

	public CollidableObject(int x, int y, float rotation, int width, int height, int weight) {
		super(x, y, rotation, width, height);
		
		this.weight=weight;
		this.collided = false;
	}
	
	/**
	 * ütközés eseménykezelője
	 */
	public void onCollision(){
		this.collided = true;
		doOnCollision();
	}
	
	/**
	 * objektum viselkedése ütközéskor
	 */
	protected abstract void doOnCollision();

	public boolean isCollided() {
		return collided;
	}


	public int getWeight() {
		return weight;
	}
}
