package hu.oe.nik.szfmv.automatedcar.emergencybreak;

public enum EmergencyType {

    SPEED_OVER_70("WARNING! SPEED IS OVER 70, AEB IS OFF"),
    DANGER("POTENTIAL COLLISION, SLOW DOWN"),
    AEB_ACTIVATED("ACTIVATE");
    
    private String message;

    EmergencyType(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return this.message;
    }
}
