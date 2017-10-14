package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

import javax.swing.*;

public class Dashboard extends SystemComponent {
    private JPanel jPanel;
    private JLabel GasPedalLabel;
    private JLabel BrakePedalLabel;
    private JLabel TransmissionLabel;
    private JLabel RevolutionLabel;
    private JLabel SpeedLabel;

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

        int signalType = s.getId();

        switch(signalType)
        {
            case 104: //DUMMY, UNTIL ENUMS ARE FINALIZED
                transmissionValue =s.getData().toString();
                break;

            case 101: //DUMMY, UNTIL ENUMS ARE FINALIZED
                gasPedalValue = gasPedalDataToString(s.getData());
                break;

            case 102: //DUMMY, UNTIL ENUMS ARE FINALIZED
                brakePedalValue = brakePedalDataToString(s.getData());
                break;

            case 111: //DUMMY, UNTIL ENUMS ARE FINALIZED
                revolutionValue = revolutionDataToString(s.getData());
                break;

            case 1234: //DUMMY, UNTIL ENUMS ARE FINALIZED
                speedValue = speedDataToString(s.getData());
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
