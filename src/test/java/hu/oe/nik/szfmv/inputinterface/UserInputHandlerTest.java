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
        this.userInputHandler = new UserInputHandler();
        this.gearShift = new GearShift(userInputHandler);
    }

    @Test
    public void testIsUserInputSetted() throws Exception {
        this.userInputHandler.setUserInput(this.newGearState);
        assertEquals(this.userInputHandler.getUserInput(),"D");
    }

    @Test
    public void testRegisterMethod() throws Exception {
        assertEquals(this.userInputHandler.getNumberOfSubscribers(),1);
        assertEquals(this.userInputHandler.isContainSubscriber(this.gearShift),true);
    }

    @Test
    public void testUnRegisterMethod() throws Exception {
        this.userInputHandler.userInputSubscriberUnRegister(this.gearShift);
        assertEquals(this.userInputHandler.getNumberOfSubscribers(),0);
        assertEquals(this.userInputHandler.isContainSubscriber(this.gearShift), false);
    }

    @Test
    public void testUpdateSystemComponentStateAndNotifySubscribedSystemComponentIsWorking() throws Exception {
        this.userInputHandler.setUserInput("R");
        assertEquals(this.gearShift.getGearState(),"R");
    }

    class GearShift implements IUserInputSubscriber {

        private String gearState = "N";
        private static final int componentID = 1;
        private IUserInputPublisher userInputPublisher;

        public GearShift(IUserInputPublisher userInputPublisher) {
            this.userInputPublisher = userInputPublisher;
            System.out.println("Gearshift is in position: " + this.gearState + "\nComponent id: " + this.componentID);
            this.userInputPublisher.userInputSubscriberRegister(this);
        }

        @Override
        public void updateSystemComponentState(String newGearStateByUser) {
            this.gearState = newGearStateByUser;
            // assertEquals(newGearStateByUser, "D");
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
