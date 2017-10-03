package hu.oe.nik.szfmv.inputinterface;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserInputHandlerTest {

    private UserInputHandler userInputHandler;

    @Before
    public void setUp() throws Exception {
        userInputHandler = new UserInputHandler();
        userInputHandler.setUserInput("a");
    }

    @Test
    public void testIsUserInputSetted() throws Exception {
        assertEquals(userInputHandler.getUserInput(),"a");
    }
}
