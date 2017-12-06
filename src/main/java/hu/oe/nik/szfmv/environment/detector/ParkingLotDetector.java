package hu.oe.nik.szfmv.environment.detector;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.IndexEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotDetector extends SystemComponent {

    public ParkingLotDetector(AutomatedCar playerCar, List<WorldObject> worldObjects) {
        this.playerCar = playerCar;
        this.worldObjects = worldObjects;
        this.isActive = false;
    }

    private static final Logger log = LogManager.getLogger();

    private Boolean isActive;
    private String sideToCheck;
    private String lastRoadSignValue;
    private IndexEnum indexValue;
    private int posX;
    private int posY;
    private WindscreenCamera detector;
    private AutomatedCar playerCar;
    private List<WorldObject> worldObjects;

    @Override
    public void loop() {
      /* if (indexValue != IndexEnum.STRAIGHT)
            SearchParkingLot(indexValue);
            */
    }

    public ArrayList<WorldObject> getDetectedObjectsRelevantToParking()
    {
        this. detector = new WindscreenCamera(this.playerCar, this.worldObjects, true);

        if (this.detector != null) {
            return this.detector.GetWorldObjectsInRangeForPatkingLotDetection();
        }

        else
            return null;

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

            case LASTROADSIGN:
                lastRoadSignValue = s.getData().toString();

            case PARKINGDETECTION:
                turnOnParkingDetectionIfIndexIsOn();
        }
    }



    private void turnOnParkingDetectionIfIndexIsOn()
    {
        if (!isActive)  //Would need information about the index's state, but it's not implemented...
        {
            isActive = true;
            log.error("TURNED ON PARK DETECT");
        }
        else
        {
           isActive = false;
        }
    }

}
