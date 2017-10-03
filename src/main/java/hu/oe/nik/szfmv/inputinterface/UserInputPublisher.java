package hu.oe.nik.szfmv.inputinterface;

import hu.oe.nik.szfmv.automatedcar.ISystemComponent;

public interface UserInputPublisher {
    void systemComponentRegister(ISystemComponent systemComponent);
    void systemComponentUnRegister(ISystemComponent systemComponent);
    void notifySubscribedSystemComponent(String userInput);
}
