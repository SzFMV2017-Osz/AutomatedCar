package hu.oe.nik.szfmv.inputinterface;

import java.util.ArrayList;

/*
maybe it should be final and singleton...
 */
public final class UserInputHandler implements IUserInputPublisher {

    private String userInput;
    private ArrayList<IUserInputSubscriber> userInputSubscribers;

    public UserInputHandler() {
        userInputSubscribers = new ArrayList<IUserInputSubscriber>();
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
        this.notifySubscribedSystemComponent(userInput);
    }

    // Just for the testcase
    public int getNumberOfSubscribers(){
        return this.userInputSubscribers.size();
    }

    // Just for the testcase
    public boolean isContainSubscriber(IUserInputSubscriber subscriber){
        return this.userInputSubscribers.contains(subscriber);
    }

    @Override
    public void userInputSubscriberRegister(IUserInputSubscriber newUserInputSubscriber) {
        this.userInputSubscribers.add(newUserInputSubscriber);
    }

    @Override
    public void userInputSubscriberUnRegister(IUserInputSubscriber deleteUserInputSubscriber) {
        int subscriberIndex = this.userInputSubscribers.indexOf(deleteUserInputSubscriber);
        this.userInputSubscribers.remove(subscriberIndex);
    }

    @Override
    public void notifySubscribedSystemComponent(String userInput) {
        for (IUserInputSubscriber subscriber : userInputSubscribers) {
            subscriber.updateSystemComponentState(userInput);
        }
    }
}
