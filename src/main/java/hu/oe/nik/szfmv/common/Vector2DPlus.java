package hu.oe.nik.szfmv.common;

public class Vector2DPlus {

	private double x;
	private double y;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector2DPlus(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2DPlus(boolean polar, double angle, double distance) {
		this.x = distance * Math.sin(angle);
		this.y = -distance * Math.cos(angle);
	}

	public double getAngle() {
		double result = getX() < 0 ? 3 * Math.PI / 2 : Math.PI / 2;
		if (getX() != 0) {
			result += Math.atan(getY() / getX());
		} else {
			result = getY() < 0 ? 0 : Math.PI;
		}
		return result;
	}

	public double getAbs() {
		return Math.sqrt(x * x + y * y);
	}

	public void addToItself(Vector2DPlus vector2dPlus) {
		this.x += vector2dPlus.getX();
		this.y += vector2dPlus.getY();
	}

	public Vector2DPlus add(Vector2DPlus vector2dPlus) {
		return new Vector2DPlus(x + vector2dPlus.getX(), y + vector2dPlus.getY());
	}

	public Vector2DPlus add(Vector2D vector2d) {
		return new Vector2DPlus(x + vector2d.getX(), y + vector2d.getY());
	}

	public Vector2DPlus add(double x, double y) {
		return new Vector2DPlus(this.x + x, this.y + y);
	}

	public Vector2DPlus sub(Vector2DPlus vector2dPlus) {
		return new Vector2DPlus(this.x - vector2dPlus.getX(), this.y - vector2dPlus.getY());
	}

	public void setValues(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2DPlus turnOver() {
		return new Vector2DPlus(-x, -y);
	}

	public void addAngle(double angle) {
		double currentAngle = getAngle();
		double distance = getAbs();
		this.x = distance * Math.sin(currentAngle + angle);
		this.y = -distance * Math.cos(currentAngle + angle);		
	}

}
