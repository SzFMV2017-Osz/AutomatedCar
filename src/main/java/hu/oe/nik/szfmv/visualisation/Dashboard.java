package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import javax.swing.*;
import java.math.RoundingMode;

public class Dashboard extends SystemComponent {
    private JPanel jPanel;
    public JLabel GasPedalLabel;
    public JLabel BrakePedalLabel;
    public JLabel TransmissionLabel;
    public JLabel RevolutionLabel;
    public JLabel SpeedLabel;
    public JLabel SteeringWheelLabel;
    public JLabel PosXLabel;
    public JLabel PosYLabel;
    public JLabel IndexLabel;

    private String gasPedalValue;
    private String brakePedalValue;
    private String revolutionValue;
    private String speedValue;
    private String steeringWheelValue;
    private String posXValue;
    private String posYValue;
    private String transmissionValue;
    private String indexValue;


    public Dashboard() {
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    @Override
    public void loop() {
        refreshDisplayedValues();
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.INDEX,"hello"));
    }

    @Override
    public void receiveSignal(Signal s) {

        SignalEnum signalType = s.getId();

        switch (signalType) {
            case AUTOTRANSMISSION:
                transmissionValue = s.getData().toString();
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
                break;

            case STEERINGWHEEL:
                steeringWheelValue = steeringWheelDataToString(s.getData()).toString();
                break;

            case POSX:
                posXValue = s.getData().toString();
                break;

            case POSY:
                posYValue = s.getData().toString();
                break;

            case INDEX:
                indexValue = s.getData().toString();
                break;

        }
    }

    private String gasPedalDataToString(Object signalData) {
        //If gaspedal's value would need special conversion, implement here
        return roundNumberString(signalData);
    }

    private String brakePedalDataToString(Object signalData) {
        //If brake pedal's value would need special conversion, implement here
        return signalData.toString();
    }

    private String speedDataToString(Object signalData) {
        //If speed's value would need special conversion, implement here
        return roundNumberString(signalData) + " KM/H";
    }

    private String revolutionDataToString(Object signalData) {
        //If brake pedal's value would need special conversion, implement here
        return roundNumberString(signalData) + " RPM";
    }

    private String steeringWheelDataToString(Object signalData) {
        //If brake pedal's value would need special conversion, implement here
        return roundNumberString(signalData) + "°";
    }

    private void refreshDisplayedValues() {
        GasPedalLabel.setText(gasPedalValue);
        BrakePedalLabel.setText(brakePedalValue);
        TransmissionLabel.setText(transmissionValue);
        RevolutionLabel.setText(revolutionValue);
        SpeedLabel.setText(speedValue);
        SteeringWheelLabel.setText(steeringWheelValue);
        PosXLabel.setText(posXValue);
        PosYLabel.setText(posYValue);
        IndexLabel.setText(indexValue);
    }

    private String roundNumberString(Object signalData) {

        if (signalData instanceof Double) {
            return String.valueOf(Math.round((double) signalData));
        }

        if (signalData instanceof Float) {
            return String.valueOf(Math.round((float) signalData));
        }

        return String.valueOf(signalData);
    }
}
