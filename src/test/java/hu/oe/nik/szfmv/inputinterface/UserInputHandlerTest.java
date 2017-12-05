package hu.oe.nik.szfmv.inputinterface;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

public class UserInputHandlerTest {

    private UserInputHandler userInputHandler;
    private int newKeyEvent;

    @Before
    public void setUp() throws Exception {
	this.userInputHandler = new UserInputHandler();
	this.newKeyEvent = KeyEvent.VK_D;
    }

    @Test
    public void testKeyPressedMethod() throws Exception {

    }
}
