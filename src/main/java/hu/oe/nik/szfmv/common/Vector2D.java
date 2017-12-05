package hu.oe.nik.szfmv.common;

public class Vector2D {

    public static final double FLOAT_COMPARE_THRESHOLD = 0.000001;
    private double x;
    private double y;

    /**
     * Base constructor, makes a null vector.
     */
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Creates a new vector, coordinates specified by the given values.
     *
     * @param x The x coordinate of the new vector.
     * @param y The y coordinate of the new vector.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor, makes a copy of the given vector.
     *
     * @param vector Vector that should be copied.
     */
    public Vector2D(Vector2D vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    /**
     * Copy method for the vector object. Makes a copy of the vector in order to
     * prevent old vector modification at further calculations.
     *
     * @return Returns a newly instanced vector copy from the original vector.
     */
    public Vector2D copy() {
        return new Vector2D(this);
    }

    /**
     * Calculates the normal vector from the given vector.
     *
     * @return Returns the same object but with normalized values. The direction
     * stays the same while the magnitude of the vector equals with 1.
     */
    public Vector2D normalize() {
        double abs = this.abs();
        if (abs != 0) {
            x /= abs;
            y /= abs;
        }
        return this;
    }

    /**
     * Calculates the magnitude of the vector.
     *
     * @return Returns the value of the magnitude of the vector.
     */
    public double abs() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Calculates the squared magnitude of the vector. It provides better
     * performance than Vector2D.abs() but the resulting value is the square of
     * the vector's magnitude.
     *
     * @return Returns the squared magnitude of the vector.
     */
    public double absSquared() {
        return x * x + y * y;
    }

    /**
     * Adds the specified vector to the base vector. Method is mutable.
     *
     * @param vector The vector which will be added to the base vector.
     * @return Returns the base vector after the addition.
     */
    public Vector2D add(Vector2D vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }

    /**
     * Subtracts the specified vector from the base vector. Method is mutable.
     *
     * @param vector The vector which will be subtracted from the base vector.
     * @return Returns the base vector after the subtraction.
     */
    public Vector2D sub(Vector2D vector) {
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    /**
     * Scalar multiplication. Multiplies the specified vector with the given
     * value. Method is mutable.
     *
     * @param value The value which will multiply the base vector by.
     * @return Returns the base vector after the multiplication.
     */
    public Vector2D mult(double value) {
        x *= value;
        y *= value;
        return this;
    }

    /**
     * Scalar division. Divides the vector with the given double value. Method
     * is mutable.
     *
     * @param value - The divider number. Throws IllegalArgumentException when
     * trying to divide with 0.
     * @return Returns the base vector after the division.
     */
    public Vector2D div(double value) {
        if (value != 0) {
            x /= value;
            y /= value;
            return this;
        } else {
            throw new IllegalArgumentException("Vector divided by 0 is not allowed!");
        }
    }

    @Deprecated
    public static Vector2D getForwardVector(double angle) {
        return new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
    }

    /**
     * Returns with a unit vector pointing in the direction specified by the
     * angle.
     *
     * @param angleRad The angle where the unit vector should point, given in
     * radians.
     * @return Unit vector pointing in the specified angle.
     */
    public static Vector2D getForwardVectorFromRad(double angleRad) {
        return new Vector2D(Math.cos(angleRad), Math.sin(angleRad));
    }

    /**
     * Returns with a unit vector pointing in the direction specified by the
     * angle.
     *
     * @param angleDeg The angle where the unit vector should point, given in
     * degrees.
     * @return Unit vector pointing in the specified angle.
     */
    public static Vector2D getForwardVectorFromDeg(double angleDeg) {
        return new Vector2D(Math.cos(Math.toRadians(angleDeg)), Math.sin(Math.toRadians(angleDeg)));
    }

    @Deprecated
    public Vector2D getForwardVector() {
        return Vector2D.getForwardVector(this.getAngle());
    }

    @Deprecated
    public double getAngle() {
        return Math.toDegrees(Math.atan(y / x));
    }

    /**
     * Calculates the vector's direction and returns the angle in radians.
     *
     * @return Returns the vector's direction angle in radians.
     */
    public double getAngleRad() {
        double angle = Math.atan2(y, x);
        if (y < 0) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    /**
     * Calculates the vector's direction and returns the angle in degrees.
     *
     * @return Returns the vector's direction angle in degrees.
     */
    public double getAngleDeg() {
        return Math.toDegrees(getAngleRad());
    }

    /**
     * Rotates the base vector by the given angle. Rotation direction is CCW for
     * positive angle, CW for negative angle.
     *
     * @param angle The amount to rotate in radians.
     * @return Returns the base vector. Mutable method.
     */
    public Vector2D rotateRad(double angle) {
        double direction = this.getAngleRad() + angle;
        double magnitude = this.abs();
        Vector2D rotatedVector = Vector2D.getForwardVectorFromRad(direction).mult(magnitude);
        this.x = rotatedVector.getX();
        this.y = rotatedVector.getY();
        return this;
    }

    /**
     * Rotates the base vector by the given angle. Rotation direction is CCW for
     * positive angle, CW for negative angle.
     *
     * @param angle The amount to rotate in degrees.
     * @return Returns the base vector. Mutable method.
     */
    public Vector2D rotateDeg(double angle) {
        double direction = this.getAngleDeg() + angle;
        double magnitude = this.abs();
        Vector2D rotatedVector = Vector2D.getForwardVectorFromDeg(direction).mult(magnitude);
        this.x = rotatedVector.getX();
        this.y = rotatedVector.getY();
        return this;
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

    /**
     * Serialize the vector's data into a string.
     *
     * @return Returns a string in the format of "(x, y)".
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Equality check for Vector2D object.
     *
     * @param obj Other object to check the equality with.
     * @return Returns true, if the other object is also Vector2D and their
     * coordinates match. Returns false otherwise.
     */
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
