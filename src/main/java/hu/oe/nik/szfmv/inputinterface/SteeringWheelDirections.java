package hu.oe.nik.szfmv.inputinterface;

public enum SteeringWheelDirections {
    LEFT  (1),
    RIGHT (2);

    private final int steeringWheelId;

     SteeringWheelDirections (int id) {
        steeringWheelId = id;
    }

    public int getSteeringWheelId(){
        return steeringWheelId;
    }

}
