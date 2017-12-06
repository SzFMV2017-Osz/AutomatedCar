package hu.oe.nik.szfmv.environment.detector;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.environment.util.IndexEnum;

public class ParkingLotDetector extends SystemComponent{

    public  ParkingLotDetector(ObjectDetector objDetector)
    {
        this.objectDetector = objDetector;
    }

    private ObjectDetector objectDetector;
    private String lastRoadSignValue;
    private IndexEnum indexValue;
    private int posX;
    private int posY;

    @Override
    public void loop() {
        if(indexValue!= IndexEnum.STRAIGHT)
            SearchParkingLot(indexValue);
    }

    private void SearchParkingLot(IndexEnum indexValue) {

    }

    @Override
    public void receiveSignal(Signal s) {
        SignalEnum signalType = s.getId();

        switch (signalType) {

            case POSX:
                posX = Integer.parseInt(s.getData().toString());
                break;

            case POSY:
                posY = Integer.parseInt(s.getData().toString());
                break;

            case INDEX:
                indexValue = (IndexEnum)s.getData();
                break;

            case LASTROADSIGN:
                lastRoadSignValue = s.getData().toString();

        }
    }
}
