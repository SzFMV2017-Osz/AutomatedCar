package hu.oe.nik.szfmv.automatedcar.emergencybreak;


import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;

public class Distanceparameters implements Comparable<Distanceparameters>{

    IRadarSensor object;
    double minDistance;
    double minTime;

    public static final Distanceparameters calculateDistanceParameters(AutomatedCar car, IRadarSensor radarObject) {

        double minTime = getMinTime(car, radarObject);
        double minDistance = getDistance(car, radarObject, minTime);

        return new Distanceparameters(radarObject, minDistance, minTime);
    }

    /**
     * the distance of two objects at a given time
     * @param car object 1
     * @param radarObject object 2
     * @param minTime the time to calculate
     * @return
     */
    private static double getDistance(AutomatedCar car, IRadarSensor radarObject, double minTime) {
        double carX = car.getPosition().getX();
        double carY = car.getPosition().getY();
        double carSpeedX = car.getCurrentSpeed().getX();
        double carSpeedY = car.getCurrentSpeed().getY();

        double objectX = radarObject.getPosition().getX();
        double objectY = radarObject.getPosition().getY();
        double objectSpeedX = radarObject.getCurrentSpeed().getX();
        double objectSpeedY = radarObject.getCurrentSpeed().getY();

        /**           _____________________________________
         * \        /
         *  \      / (tmin * Avx - tmin * Bvx + Ax + Bx)^2
         *   \    /  -------------------------------------
         *    \  /   (tmin * Avy - tmin * Bvy + Ay + By)^2
         *     \/
         */
        return Math.sqrt(Math.pow((minTime * carSpeedX - minTime * objectSpeedX + carX + objectX), 2)
                + Math.pow((minTime * carSpeedY - minTime * objectSpeedY + carY + objectY), 2));
    }

    /**
     * calculate the moment (t) when the distance between the route of two objects in minimal
     * 
     * https://gamedev.stackexchange.com/questions/97337/detect-if-two-objects-are-going-to-collide
     * 
     * @param car
     * @param radarObject
     * @return
     */
    private static double getMinTime(AutomatedCar car, IRadarSensor radarObject) {
        double carX = car.getPosition().getX();
        double carY = car.getPosition().getY();
        double carSpeedX = car.getCurrentSpeed().getX();
        double carSpeedY = car.getCurrentSpeed().getY();

        double objectX = radarObject.getPosition().getX();
        double objectY = radarObject.getPosition().getY();
        double objectSpeedX = radarObject.getCurrentSpeed().getX();
        double objectSpeedY = radarObject.getCurrentSpeed().getY();

        /**
         *      [Ax * Avx - Avx * Bx - (Ax - Bx) * Avx] + [Ay * Avy - Avy * By - (Ay- By) * Bvy]
         * -    ---------------------------------------------------------------------------------
         *              [Avx^2 - 2 * Avx * Bvx + Bvx^2] + [Avy^2 - 2 * Avy * Bvy + Bvy^2]
         */
        return -(carX * carSpeedX - carSpeedX * objectX - (carX - objectX) * objectSpeedX + carY * carSpeedY
                - carSpeedY * objectY - (carY - objectY) * objectSpeedY)
                / (carSpeedX * carSpeedX - 2 * carSpeedX * objectSpeedX + objectSpeedX * objectSpeedX
                        + carSpeedY * carSpeedY - 2 * carSpeedY * objectSpeedY + objectSpeedY * objectSpeedY);
    }

    private Distanceparameters(IRadarSensor object, double minDistance, double minTime) {
        super();
        this.object = object;
        this.minDistance = minDistance;
        this.minTime = minTime;
    }

    public void recalculate(AutomatedCar car) {
        this.minTime = getMinTime(car, this.getObject());
        this.minDistance = getDistance(car, this.getObject(), this.getMinTime());
    }

    public IRadarSensor getObject() {
        return object;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public double getMinTime() {
        return minTime;
    }

    @Override
    public int compareTo(Distanceparameters o) {

       return (int)(this.getMinDistance() - o.getMinDistance());
    }

}
