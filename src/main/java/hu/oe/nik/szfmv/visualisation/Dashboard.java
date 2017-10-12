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

        int signalType = s.getId();

        switch(signalType)
        {
            case 104: //GEARSHIFTSTATE_ID
                transmissionLabelValue =s.getData().toString();
                break;

            case 101: //GASPEDAL_ID
                gasPedalValue = gasPedalDataToString(s.getData());
                break;

            case 102: //BRAKEPEDAL_ID
                brakePedalValue = brakePedalDataToString(s.getData());
        }
    }

    private String gasPedalDataToString(Object signalData)
    {
        //If gaspedal's value would need special conversion, implement here
        return signalData.toString();
    }

    private String brakePedalDataToString(Object signalData)
    {
        //If brake pedal's value would need special conversion, implement here
        return signalData.toString();
    }

    private void refreshDisplayedValues()
    {
        GasPedalLabel.setText(gasPedalValue);
        BrakePedalValueLabel.setText(brakePedalValue);
        TransmissionLabel.setText(transmissionLabelValue);
    }
}
