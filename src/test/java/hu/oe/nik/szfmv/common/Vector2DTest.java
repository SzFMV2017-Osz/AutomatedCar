package hu.oe.nik.szfmv.common;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class Vector2DTest {

    public static final Vector2D V1 = new Vector2D(5, 5);
    public static final Vector2D V2 = new Vector2D(Math.cos(Math.PI / 4), Math.sin(Math.PI / 4));
    public static final Vector2D V3 = new Vector2D(-8, 6);
    public static final Vector2D V4 = new Vector2D(Math.cos(Math.PI / 3), Math.sin(Math.PI / 3));
    public static final Vector2D NULL_VECTOR = new Vector2D(0, 0);
    public static final double V2_ANGLE = 45;
    public static final double V4_ANGLE = 60;
    public static final double V1_ABS = Math.sqrt(50);
    public static final double V1_ABS_SQUARED = 50;
    public static final double V2_ABS = 1;
    public static final double V2_ABS_SQUARED = 1;
    public static final Vector2D V1_PLUS_V3 = new Vector2D(-3, 11);
    public static final Vector2D V1_MINUS_V3 = new Vector2D(13, -1);
    public static final double DOUBLE_COMPARISON_THRESHOLD = 0.000001;
    public static final double V1_XY_POSITION = 5.00;


    @org.junit.Before
    public void setUp() {
    }

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
    }

    @Test
    public void getForwardVectorTest() {
        assertEquals(V2, Vector2D.getForwardVector(V2_ANGLE));
        assertEquals(V4, Vector2D.getForwardVector(V4_ANGLE));
    }
}
