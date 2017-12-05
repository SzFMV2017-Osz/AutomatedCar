package hu.oe.nik.szfmv.inputinterface;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarComponentStateCalculatorTest {

    private CarComponentStateCalculator carComponentStateCalculator;
    private int gaspedalState;

    @Before
    public void setUp() throws Exception {
        this.carComponentStateCalculator = new CarComponentStateCalculator();
        this.gaspedalState = 0;
    }

    @Test
    public void testAddGasMethod() throws Exception {
        assertEquals(this.carComponentStateCalculator.addGas(this.gaspedalState), 1);
        this.gaspedalState = 100;
        assertEquals(this.carComponentStateCalculator.addGas(this.gaspedalState), 100);
    }
}
