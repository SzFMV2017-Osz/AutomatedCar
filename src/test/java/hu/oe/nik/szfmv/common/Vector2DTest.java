package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.common.Vector2D;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class Vector2DTest {

    public static final Vector2D V1 = new Vector2D(5, 5);
    public static final Vector2D V2 = new Vector2D(Math.cos(Math.PI / 4), Math.sin(Math.PI / 4));
    public static final Vector2D V3 = new Vector2D(-8, 6);
    public static final Vector2D V4 = new Vector2D(Math.cos(Math.PI / 3), Math.sin(Math.PI / 3));
    public static final Vector2D NULL_VECTOR = new Vector2D(0, 0);
    public static final double V1_ANGLE = 45;
    public static final double V4_ANGLE = 60;
    public static final double V1_ABS = Math.sqrt(50);
    public static final double V1_ABS_SQUARED = 50;
    public static final double V2_ABS = 1;
    public static final double V2_ABS_SQUARED = 1;
    public static final Vector2D V1_PLUS_V3 = new Vector2D(-3, 11);
    public static final Vector2D V1_MINUS_V3 = new Vector2D(13, -1);
    public static final double DOUBLE_COMPARISON_THRESHOLD = 0.000001;


    @org.junit.Before
    public void setUp() {
    }

    @Test
    public void toStringTest() {
        assertEquals("(5.0, 5.0)", V1.toString());
    }

    @Test
    public void equalsTest() {
        assertNotSame(V1, V2);
        assertEquals(V1, new Vector2D(5.00, 5.00));
    }

    @Test
    public void absAndAbsSquaredTest() {
        assertEquals(V1_ABS, V1.abs());
        assertEquals(V2_ABS, V2.abs());
        assertEquals(V1_ABS_SQUARED, V1.absSquared());
        assertEquals(V2_ABS_SQUARED, V2.absSquared());
    }

    @Test
    public void addAndSubTest() {
        assertEquals(V1_PLUS_V3, V1.add(V3));
        assertEquals(V1_MINUS_V3, V1.sub(V3));
    }

    @Test
    public void multiplyByDoubleTest() {
        assertEquals(V1, V2.mult(V1_ABS));
    }

    @Test
    public void divByDoubleTest() {
        assertEquals(V1, V1.div(1));
        assertEquals(V2, V1.div(V1_ABS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void divByZeroTest() {
        V1.div(0);
    }

    @Test
    public void normalizeTest() {
        assertEquals(V2, V1.normalize());
        assertEquals(NULL_VECTOR, V1.mult(0).normalize());
    }

    @Test
    public void getAngleTest() {
        assertEquals(V1_ANGLE, V1.getAngle());

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
        assertEquals(V2, Vector2D.getForwardVector(45));
        assertEquals(V4, Vector2D.getForwardVector(60));
    }
}
