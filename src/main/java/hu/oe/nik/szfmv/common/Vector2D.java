package hu.oe.nik.szfmv.common;

public class Vector2D {

    private float x;
    private float y;

    public Vector2D() {
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    Vector2D normalize() {
        return null;
    }

    Vector2D abs() {
        return null;
    }

    Vector2D add(Vector2D vector) {
        return null;
    }

    Vector2D sub(Vector2D vector) {
        return null;
    }

    Vector2D mult(Vector2D vector) {
        return null;
    }

    Vector2D div(Vector2D vector) {
        return null;
    }

    Vector2D getForwardVector(float angle) {
        return null;
    }

    float getAimingAngle(Vector2D forwardVector) {
        return 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
