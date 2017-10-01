package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

public class Dashboard extends SystemComponent{

    public void loop()
    {

    }

    @Override
    public void receiveSignal(Signal s) {
        //Implement logic to receive signals important for the dashboard
    }
}
