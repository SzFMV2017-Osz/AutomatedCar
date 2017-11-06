package hu.oe.nik.szfmv.visualisation;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IsKeyPressed {
    private static volatile boolean wPressed = false;
    public static boolean isWPressed() {
        synchronized (IsKeyPressed.class) {
            return wPressed;
        }
    }

    public static void main(String[] args) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (IsKeyPressed.class) {
                    switch (ke.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                wPressed = true;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                wPressed = false;
                            }
                            break;
                    }
                    return false;
                }
            }
        });
    }

    @Override
    public void keyReleased(KeyEvent userKeyRelease) {

        this.pressedKeyCodes.remove(new Integer(userKeyRelease.getKeyCode()));

        if (this.pressedKeyCodes.isEmpty()){
            this.isKeyPressingHappened = false;
            this.decreaseAllComponentStateToBase();
        }

    }
}