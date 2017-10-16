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

    public Vector2D(Vector2D vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2D copy() {
        return new Vector2D(this);
    }

    public Vector2D normalize() {
        double abs = this.abs();
        if (abs != 0) {
            x /= abs;
            y /= abs;
        }
        return this;

    }

    public double abs() {
        return Math.sqrt(x * x + y * y);
    }

    public double absSquared() {
        return x * x + y * y;
    }

    public Vector2D add(Vector2D vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vector2D sub(Vector2D vector) {
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vector2D mult(double value) {
        x *= value;
        y *= value;
        return this;
    }

    public Vector2D div(double value) {
        if (value != 0) {
            x /= value;
            y /= value;
            return this;
        } else {
            throw new IllegalArgumentException("Vector divided by 0 is not allowed!");
        }
    }

    public static Vector2D getForwardVector(double angle) {
        return new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
    }

    public Vector2D getForwardVector() {
        return Vector2D.getForwardVector(this.getAngle());
    }

    public double getAngle() {
        return Math.toDegrees(Math.atan(y / x));
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
        } else if (
                Math.abs(this.x - other.getX()) <= FLOAT_COMPARE_THRESHOLD &&
                        Math.abs(this.y - other.getY()) <= FLOAT_COMPARE_THRESHOLD) {
            return true;
        }
        return false;
    }
}
