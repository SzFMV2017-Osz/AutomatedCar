package hu.oe.nik.szfmv.common;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class Vector2DTest {

    private static final Vector2D V1 = new Vector2D(5, 5);
    private static final Vector2D V2 = new Vector2D(Math.cos(Math.PI / 4), Math.sin(Math.PI / 4));
    private static final Vector2D V3 = new Vector2D(-8, 6);
    private static final Vector2D V4 = new Vector2D(Math.cos(Math.PI / 3), Math.sin(Math.PI / 3));
    private static final Vector2D V5 = new Vector2D(-Math.cos(Math.PI / 4), -Math.sin(Math.PI / 4));
    private static final Vector2D VX = new Vector2D(1, 0);
    private static final Vector2D NULL_VECTOR = new Vector2D(0, 0);
    private static final double V2_ANGLE = 45;
    private static final double V4_ANGLE = 60;
    private static final double V5_ANGLE = 225;
    private static final double HALF_CIRCLE = 180;
    private static final double FULL_CIRCLE = 360;
    private static final double FULL_CIRCLERAD = Math.toRadians(FULL_CIRCLE);
    private static final double V5_ANGLERAD = Math.toRadians(V5_ANGLE);
    private static final double V1_ABS = Math.sqrt(50);
    private static final double V1_ABS_SQUARED = 50;
    private static final double V2_ABS = 1;
    private static final double V2_ABS_SQUARED = 1;
    private static final Vector2D V1_PLUS_V3 = new Vector2D(-3, 11);
    private static final Vector2D V1_MINUS_V3 = new Vector2D(13, -1);
    private static final double DOUBLE_COMPARISON_THRESHOLD = 0.000001;
    private static final double V1_XY_POSITION = 5.00;

    @Test
    public void toStringTest() {
        Vector2D v = new Vector2D(V1);
        assertEquals("(5.0, 5.0)", v.toString());
    }

    @Test
    public void equalsTest() {
        Vector2D v1 = V1.copy();
        Vector2D v2 = V2.copy();
        assertNotSame(v1, v2);
        assertEquals(v1, new Vector2D(V1_XY_POSITION, V1_XY_POSITION));
    }

    @Test
    public void absAndAbsSquaredTest() {
        Vector2D v1 = V1.copy();
        Vector2D v2 = V2.copy();
        assertEquals(V1_ABS, v1.abs());
        assertEquals(V2_ABS, v2.abs());
        assertEquals(V1_ABS_SQUARED, v1.absSquared());
        assertEquals(V2_ABS_SQUARED, v2.absSquared());
    }

    @Test
    public void addAndSubTest() {
        Vector2D vadd = V1.copy();
        Vector2D vsub = V1.copy();
        Vector2D v3 = V3.copy();
        assertEquals(V1_PLUS_V3, vadd.add(v3));
        assertEquals(V1_MINUS_V3, vsub.sub(v3));
    }

    @Test
    public void multiplyByDoubleTest() {
        Vector2D v1 = V1.copy();
        Vector2D v2 = V2.copy();
        assertEquals(v1, v2.mult(V1_ABS));
    }

    @Test
    public void divByDoubleTest() {
        Vector2D v1 = V1.copy();
        Vector2D v2 = V2.copy();
        assertEquals(v1, new Vector2D(v1).div(1));
        assertEquals(v2, v1.div(V1_ABS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void divByZeroTest() {
        Vector2D v1 = V1.copy();
        v1.div(0);
    }

    @Test
    public void normalizeTest() {
        Vector2D v1 = V1.copy();
        Vector2D v2 = V2.copy();
        assertEquals(v2, v1.normalize());
        assertEquals(NULL_VECTOR, v1.mult(0).normalize());
    }

    @Deprecated
    @Test
    public void getAngleTest() {
        Vector2D v1 = V1.copy();
        assertEquals(V2_ANGLE, v1.getAngle());

        // this would fail caused by double precision
        // assertEquals(V4_ANGLE, V4.getAngle());
        // using threshold to compare double values
        if (Math.abs(V4_ANGLE - V4.getAngle()) <= DOUBLE_COMPARISON_THRESHOLD) {
            assertEquals(true, true);
        } else {
            assertEquals(true, false);
        }
//        assertEquals(V5.getAngle(), V5_ANGLE);
    }

    @Test
    public void getAngleRadTest() {
        assertEquals(V1.getAngleRad(), V2.getAngleRad());
        assertEquals(V5.getAngleRad(), V5_ANGLERAD);
        assertEquals(NULL_VECTOR.getAngleRad(), 0.0);
    }

    @Test
    public void getAngleDegTest() {
        assertEquals(V1.getAngleDeg(), V2.getAngleDeg());
        assertEquals(V5.getAngleDeg(), V5_ANGLE);
        assertEquals(NULL_VECTOR.getAngleRad(), 0.0);
    }

    @Deprecated
    @Test
    public void getForwardVectorTest() {
        assertEquals(V2, Vector2D.getForwardVector(V2_ANGLE));
        assertEquals(V4, Vector2D.getForwardVector(V4_ANGLE));
    }

    @Test
    public void getForwardVectorRadTest() {
        assertEquals(V2, Vector2D.getForwardVectorFromRad(Math.toRadians(V2_ANGLE)));
        assertEquals(V4, Vector2D.getForwardVectorFromRad(Math.toRadians(V4_ANGLE)));
    }

    @Test
    public void getForwardVectorDegTest() {
        assertEquals(V2, Vector2D.getForwardVectorFromDeg(V2_ANGLE));
        assertEquals(V4, Vector2D.getForwardVectorFromDeg(V4_ANGLE));
    }

    @Test
    public void rotateRad() throws Exception {
        assertEquals(V1, V5.copy().normalize().mult(V1.abs()).rotateRad(Math.PI));
        assertEquals(NULL_VECTOR, NULL_VECTOR.copy().rotateRad(V5_ANGLERAD));
        assertEquals(V5, VX.copy().rotateRad(V5_ANGLERAD - FULL_CIRCLERAD));
    }

    @Test
    public void rotateDeg() throws Exception {
        assertEquals(V1, V5.copy().normalize().mult(V1.abs()).rotateDeg(HALF_CIRCLE));
        assertEquals(NULL_VECTOR, NULL_VECTOR.copy().rotateDeg(V5_ANGLE));
        assertEquals(V5, VX.copy().rotateDeg(V5_ANGLE - FULL_CIRCLE));
    }

}
