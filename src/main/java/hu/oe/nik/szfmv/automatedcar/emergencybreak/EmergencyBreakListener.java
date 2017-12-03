package hu.oe.nik.szfmv.automatedcar.emergencybreak;

public interface EmergencyBreakListener {

    void onEmergency(EmergencyBreakSystem system, EmergencyType type);
}
