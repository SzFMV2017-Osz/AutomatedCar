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

    @Test
    public void loop_ShouldRefreshSteeringWheelValue_IfReceivingRevolutionSignal()
    {
        //ARRANGE
        Signal steeringWheelSignal = new Signal(SignalEnum.STEERINGWHEEL, (double)78.2);

        //ACT
        dashboard.receiveSignal(steeringWheelSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.SteeringWheelLabel.getText(), "78°");
    }

    @Test
    public void loop_ShouldRefreshPosX_IfReceivingRevolutionSignal()
    {
        //ARRANGE
        Signal posXSignal = new Signal(SignalEnum.POSX, 100);

        //ACT
        dashboard.receiveSignal(posXSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.PosXLabel.getText(), "100");
    }

    @Test
    public void loop_ShouldRefreshPosY_IfReceivingRevolutionSignal()
    {
        //ARRANGE
        Signal posYSignal = new Signal(SignalEnum.POSY, 100);

        //ACT
        dashboard.receiveSignal(posYSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.PosYLabel.getText(), "100");
    }

    @Test
    public void loop_ShouldRefreshIndex_IfReceivingRevolutionSignal()
    {
        //ARRANGE
        Signal indexSignal = new Signal(SignalEnum.INDEX, "R");

        //ACT
        dashboard.receiveSignal(indexSignal);
        dashboard.loop();

        //ASSERT
        Assert.assertEquals(dashboard.IndexLabel.getText(), "R");
    }

}
