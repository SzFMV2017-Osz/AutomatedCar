package hu.oe.nik.szfmv.inputinterface;

public enum CarComponent {
    GASPEDAL      (101),
    BRAKEPEDAL    (102),
    STEERINGWHEEL (103),
    GEARSHIFT     (104)
    ;

    private final int carComponentID;

    private CarComponent(int ID) {
        this.carComponentID = ID;
    }

    public int getCarComponentID(){
        return this.carComponentID;
    }
}