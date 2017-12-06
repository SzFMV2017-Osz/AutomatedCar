package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.automatedcar.LaneKeeping;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private JLabel LastRoadSignLabel;
    private JLabel LaneKeepingPossible;
    private JLabel LaneKeepingActive;
    public JLabel EmptyLabel;

    private String gasPedalValue;
    private String brakePedalValue;
    private String revolutionValue;
    private String speedValue;
    private String steeringWheelValue;
    private String posXValue;
    private String posYValue;
    private String transmissionValue;
    private String indexValue;
    private String lastRoadSignValue;

    public Dashboard() {
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    @Override
    public void loop() {
        refreshDisplayedValues();
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.INDEX, "hello"));
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
                posXValue = roundNumberString(s.getData());
                break;

            case POSY:
                posYValue = roundNumberString(s.getData());
                break;

            case INDEX:
                indexValue = s.getData().toString();
                break;

            case LASTROADSIGN:
                lastRoadSignValue = s.getData().toString();

        }
    }

    private String gasPedalDataToString(Object signalData) {
        // If gaspedal's value would need special conversion, implement here
        return roundNumberString(signalData);
    }

    private String brakePedalDataToString(Object signalData) {
        // If brake pedal's value would need special conversion, implement here
        return signalData.toString();
    }

    private String speedDataToString(Object signalData) {
        // If speed's value would need special conversion, implement here
        return roundNumberString(signalData) + " KM/H";
    }

    private String revolutionDataToString(Object signalData) {
        // If brake pedal's value would need special conversion, implement here
        return String.format("%4s RPM", roundNumberString(signalData));
    }

    private String steeringWheelDataToString(Object signalData) {
        // If brake pedal's value would need special conversion, implement here
        return roundNumberString(signalData) + "°";
    }

    private String isLaneKeepingActiveToString() {
        return LaneKeeping.isIsLaneKeepingTurnedOn() ? "Y" : "N";
    }

    private String isLaneKeepingPossibleToString() {
        return LaneKeeping.isIsLaneKeepingPossible() ? "Y" : "N";
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
        LastRoadSignLabel.setText(lastRoadSignValue);
        LaneKeepingActive.setText(isLaneKeepingActiveToString());
        LaneKeepingPossible.setText(isLaneKeepingPossibleToString());
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

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT
     * edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        final JLabel label1 = new JLabel();
        label1.setText("Kormány:");
        jPanel.add(label1, c);
        SteeringWheelLabel = new JLabel();
        SteeringWheelLabel.setText("");
        c.gridx = 1;
        c.gridy = 0;
        jPanel.add(SteeringWheelLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        final JLabel label2 = new JLabel();
        label2.setText("Gáz:");
        jPanel.add(label2, c);
        GasPedalLabel = new JLabel();
        GasPedalLabel.setText("123");
        c.gridx = 1;
        c.gridy = 1;
        jPanel.add(GasPedalLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        final JLabel label3 = new JLabel();
        label3.setText("Fék:");
        jPanel.add(label3, c);
        BrakePedalLabel = new JLabel();
        BrakePedalLabel.setText("123");
        c.gridx = 1;
        c.gridy = 2;
        jPanel.add(BrakePedalLabel, c);
        c.gridx = 0;
        c.gridy = 3;
        final JLabel label4 = new JLabel();
        label4.setText("X:");
        jPanel.add(label4, c);
        PosXLabel = new JLabel();
        PosXLabel.setText("123");
        c.gridx = 1;
        c.gridy = 3;
        jPanel.add(PosXLabel, c);
        c.gridx = 0;
        c.gridy = 4;
        final JLabel label5 = new JLabel();
        label5.setText("Y:");
        jPanel.add(label5, c);
        PosYLabel = new JLabel();
        PosYLabel.setText("123");
        c.gridx = 1;
        c.gridy = 4;
        jPanel.add(PosYLabel, c);
        c.gridx = 0;
        c.gridy = 5;
        final JLabel label6 = new JLabel();
        label6.setText("Sebesség:");
        jPanel.add(label6, c);
        SpeedLabel = new JLabel();
        SpeedLabel.setText("123");
        c.gridx = 1;
        c.gridy = 5;
        jPanel.add(SpeedLabel, c);
        c.gridx = 0;
        c.gridy = 6;
        final JLabel label7 = new JLabel();
        label7.setText("Fordulatszám:");
        jPanel.add(label7, c);
        RevolutionLabel = new JLabel();
        RevolutionLabel.setText("123");
        c.gridx = 1;
        c.gridy = 6;
        jPanel.add(RevolutionLabel, c);
        c.gridx = 0;
        c.gridy = 7;
        final JLabel label8 = new JLabel();
        label8.setText("Irányjelző:");
        jPanel.add(label8, c);
        IndexLabel = new JLabel();
        IndexLabel.setText("123");
        c.gridx = 1;
        c.gridy = 7;
        jPanel.add(IndexLabel, c);
        c.gridx = 0;
        c.gridy = 8;
        final JLabel label9 = new JLabel();
        label9.setText("Transmission:");
        jPanel.add(label9, c);
        TransmissionLabel = new JLabel();
        TransmissionLabel.setText("Label");
        c.gridx = 1;
        c.gridy = 8;
        jPanel.add(TransmissionLabel, c);
        EmptyLabel = new JLabel();
        EmptyLabel.setText("                      ");
        c.gridx = 1;
        c.gridy = 9;
        jPanel.add(EmptyLabel, c);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel;
    }
}
