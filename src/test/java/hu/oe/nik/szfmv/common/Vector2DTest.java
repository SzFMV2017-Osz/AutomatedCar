package hu.oe.nik.szfmv.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Vector2DTest {

    @Test
    public void vectorXTest() {
        Vector2D vector = new Vector2D(1,1);
        //length
        assertEquals(1.4142,vector.abs(),0.1);
        //length squared
        assertEquals(2.0, vector.absSquared(),0);
        //angle to X
        assertEquals(45, vector.getAngle(),0);
        
        Vector2D forwardVector = vector.getForwardVector();
        
        assertEquals(0.7021, forwardVector.getX(),0.1);
        assertEquals(0.7021, forwardVector.getY(),0.1);
        assertEquals(1.0, forwardVector.abs(),0);
        assertEquals(45, forwardVector.getAngle(),0);
        
    }
}
