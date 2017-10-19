package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;

import javax.swing.*;

public class Dashboard extends SystemComponent {
    private JPanel jPanel;
    public JLabel GasPedalLabel;
    public JLabel BrakePedalLabel;
    public JLabel TransmissionLabel;
    public JLabel RevolutionLabel;
    public JLabel SpeedLabel;

    private String gasPedalValue;
    private String brakePedalValue;
    private String transmissionValue;
    private String revolutionValue;
    private String speedValue;


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

        SignalEnum signalType = s.getId();

        switch(signalType)
        {
            case AUTOTRANSMISSION:
                transmissionValue =s.getData().toString();
                break;

            case GASPEDAL:
                gasPedalValue = gasPedalDataToString(s.getData());
                break;

            case BREAKPEDAL:
                brakePedalValue = brakePedalDataToString(s.getData());
                break;

            case REVOLUTION:
                revolutionValue = revolutionDataToString(s.getData());
                break;

            case SPEED:
                speedValue = speedDataToString(s.getData());
        }
    }

    private String gasPedalDataToString(Object signalData)
    {
        //If gaspedal's value would need special conversion, implement here
        return signalData.toString() + "%";
    }

    private String brakePedalDataToString(Object signalData)
    {
        //If brake pedal's value would need special conversion, implement here
        return signalData.toString() + "%";
    }

    private String speedDataToString(Object signalData)
    {
        //If speed's value would need special conversion, implement here
        return signalData.toString()+ " KM/H";
    }

    private String revolutionDataToString(Object signalData)
    {
        //If brake pedal's value would need special conversion, implement here
        return signalData.toString()+ " RPM";
    }

    private void refreshDisplayedValues()
    {
        GasPedalLabel.setText(gasPedalValue);
        BrakePedalLabel.setText(brakePedalValue);
        TransmissionLabel.setText(transmissionValue);
        RevolutionLabel.setText(revolutionValue);
        SpeedLabel.setText(speedValue);
    }
}
