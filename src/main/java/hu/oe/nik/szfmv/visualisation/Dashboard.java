package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.ISystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

import javax.swing.*;

public class Dashboard implements ISystemComponent {
    private JPanel jPanel;

    public Dashboard() {

    }

    public JPanel getJPanel() {
        return jPanel;
    }

    @Override
    public void loop() {

    }

    @Override
    public void receiveSignal(Signal s) {

    }
}
