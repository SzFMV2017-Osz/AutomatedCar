package hu.oe.nik.szfmv.inputinterface;

public interface IUserInputPublisher {
    void userInputSubscriberRegister(IUserInputSubscriber newUserInputSubscriber);
    void userInputSubscriberUnRegister(IUserInputSubscriber deleteUserInputSubscriber);
    void notifySubscribedSystemComponent(String userInput);
}
