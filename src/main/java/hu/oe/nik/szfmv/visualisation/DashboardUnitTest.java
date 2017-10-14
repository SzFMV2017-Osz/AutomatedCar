package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DashboardUnitTest {

    private Dashboard dashboard;

    @Before
    public void setup() {
        this.dashboard = new Dashboard();
    }

    @Test
    public void loop_ShouldRefreshGaspedalValue_IfReceivingGaspedalSignal()
    {
        //ARRANGE
        Signal gasPedalSignal = new Signal(SignalEnum.GASPEDAL, 10);

        //ACT
        dashboard.receiveSignal(gasPedalSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.GasPedalLabel.getText(), "10");
    }

    @Test
    public void loop_ShouldRefreshBrakePedalValue_IfReceivingBrakepedalSignal()
    {
        //ARRANGE
        Signal brakePedalSignal = new Signal(SignalEnum.BREAKPEDAL, 10);

        //ACT
        dashboard.receiveSignal(brakePedalSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.BrakePedalLabel.getText(), "10");
    }

    @Test
    public void loop_ShouldRefreshTransmissionValue_IfReceivingTransmissionSignal()
    {
        //ARRANGE
        Signal transmissionSignal = new Signal(SignalEnum.AUTOTRANSMISSION, "R");

        //ACT
        dashboard.receiveSignal(transmissionSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.TransmissionLabel.getText(), "R");
    }

    @Test
    public void loop_ShouldRefreshSpeedValue_IfReceivingSpeedSignal()
    {
        //ARRANGE
        Signal speedSignal = new Signal(SignalEnum.SPEED, 120);

        //ACT
        dashboard.receiveSignal(speedSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.SpeedLabel.getText(), "120 KM/H");
    }

    @Test
    public void loop_ShouldRefreshRevolutionValue_IfReceivingRevolutionSignal()
    {
        //ARRANGE
        Signal revolutionSignal = new Signal(SignalEnum.REVOLUTION, 2500);

        //ACT
        dashboard.receiveSignal(revolutionSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.RevolutionLabel.getText(), "2500 RPM");
    }

}
