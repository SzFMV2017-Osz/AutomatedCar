package hu.oe.nik.szfmv.inputinterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class UserInputHandler implements KeyListener{

    private String userInput;

    public UserInputHandler() {

    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D){
            this.userInput = "D";
            System.out.println("Userinput = " + getUserInput());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
