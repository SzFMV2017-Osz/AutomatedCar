package hu.oe.nik.szfmv.inputinterface;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserInputHandlerTest {

    private UserInputHandler userInputHandler;
    private GearShift gearShift;
    private String newGearState = "D";

    @Before
    public void setUp() throws Exception {
        this.gearShift = new GearShift();
    }

    @Test
    public void testIsUserInputSetted() throws Exception {

    }

    class GearShift {

        private String gearState = "N";

        public GearShift() {
            this.printGearState();
        }

        private void printGearState(){
            System.out.println("Gearshift is in position: " + this.gearState );
        }

        public String getGearState() {
            return this.gearState;
        }
    }
}
