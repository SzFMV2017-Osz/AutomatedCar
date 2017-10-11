package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

import javax.swing.*;

public class Dashboard extends SystemComponent {
    private JPanel jPanel;
    private JLabel GasPedalLabel;
    private JLabel BrakePedalValueLabel;
    private JLabel TransmissionLabel;

    private String gasPedalValue;
    private String brakePedalValue;
    private String transmissionLabelValue;

    public Dashboard() {
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    @Override
    public void loop() {
        refreshDisplayedValues();
    }

    @Override
    public void receiveSignal(Signal s) {

    }

    private void refreshDisplayedValues()
    {
        GasPedalLabel.setText(gasPedalValue);
        BrakePedalValueLabel.setText(brakePedalValue);
        TransmissionLabel.setText(transmissionLabelValue);
    }
}
