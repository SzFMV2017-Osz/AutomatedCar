package hu.oe.nik.szfmv.automatedcar.bus;

import static org.junit.Assert.assertEquals;

public class SignalTest {

    private Signal signal;

    @org.junit.Before
    public void setUp() throws Exception {
        /* stuff written here runs before the tests */
        signal = new Signal(SignalEnum.GASPEDAL, 42);
    }

    @org.junit.Test
    public void testIdGetter() {
        assertEquals(signal.getId(), SignalEnum.GASPEDAL);
    }

    @org.junit.Test
    public void testDataGetter() {
        assertEquals(signal.getData(), 42);
    }
}
