package hu.oe.nik.szfmv.common;

public class Vector2D {

    public static final double FLOAT_COMPARE_THRESHOLD = 0.000001;
    private double x;
    private double y;

    public Vector2D() {
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D normalize() {
        double abs = this.abs();
        if (abs == 0) {
            return new Vector2D(0, 0);
        } else {
            return new Vector2D(x / abs, y / abs);
        }
    }

    public double abs() {
        return Math.sqrt(x * x + y * y);
    }

    public double absSquared() {
        return x * x + y * y;
    }

    public Vector2D add(Vector2D vector) {
        return new Vector2D(x + vector.getX(), y + vector.getY());
    }

    public Vector2D sub(Vector2D vector) {
        return new Vector2D(x - vector.getX(), y - vector.getY());
    }

    public Vector2D mult(double value) {
        return new Vector2D(x * value, y * value);
    }

    public Vector2D div(double value) {
        if (value != 0) {
            return new Vector2D(x / value, y / value);
        } else {
            throw new IllegalArgumentException("Vector divided by 0 is not allowed!");
        }
    }

    public static Vector2D getForwardVector(double angle) {
        return new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
    }

    public static Vector2D getForwardVectorRadian(double radian) {
        return new Vector2D(Math.cos(radian), Math.sin(radian));
    }

    public Vector2D getForwardVector() {
        return Vector2D.getForwardVector(this.getAngle());
    }

    /**
     * WARN: this may be not accurate, see: {@link java.lang.Math#toDegrees}
     * 
     * @see java.lang.Math#toDegrees
     * @return
     */
    public double getAngle() {
        return Math.toDegrees(getAngleRadian());
    }

    public double getAngleRadian() {
        double result = getX() < 0 ? 3 * Math.PI / 2 : Math.PI / 2;
        if (getX() != 0) {
            result += Math.atan(getY() / getX());
        } else {
            result = getY() < 0 ? 0 : Math.PI;
        }
        return result;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        Vector2D other = (Vector2D) obj;
        if (other == null) {
            return false;
        } else if (Math.abs(this.x - other.getX()) <= FLOAT_COMPARE_THRESHOLD
                && Math.abs(this.y - other.getY()) <= FLOAT_COMPARE_THRESHOLD) {
            return true;
        }
        return false;
    }
}
