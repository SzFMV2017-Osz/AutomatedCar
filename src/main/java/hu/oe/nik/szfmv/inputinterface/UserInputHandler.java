package hu.oe.nik.szfmv.inputinterface;

import java.util.ArrayList;

/*
maybe it should be final and singleton...
 */
public class UserInputHandler {

    private String userInput;
    private ArrayList<UserInputSubscriber> userInputSubscribers;

    public UserInputHandler() {
        userInputSubscribers = new ArrayList<UserInputSubscriber>();
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }
}
