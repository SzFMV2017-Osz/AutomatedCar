package hu.oe.nik.szfmv.environment.object;

/**
 * irányítható objekutmok vezérlésést biztosító felület
 *
 * @author kalmankostenszky
 */
public interface IDriveable {

    // TODO: vezérlést megvalósító csapttal egyeztetni.EZEK CSAK PÉLDÁK

    void turn(float angle);

    void accelerate(int scale);

    void slow(int scale);

}
