package hu.oe.nik.szfmv.environment.factory;

import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Sensor;
import hu.oe.nik.szfmv.environment.util.SensorType;

public class SensorObjectFactory {


    /*
    Ultrasound sensors:
    FL  : front left
    FR  : front right
    LSF : left side front
    LSB : left side back
    RSF : right side front
    RSB : right side back
    RL  : rear left
    RR  : rear right

            FL  FR

         LSF       RSF


         LSB       RSB

            RL  RR

     */

    final static int pixelToMeterRatio = 50;
    final static int carHeight = 290;
    final static int carWidth = 120;
    final static int radarSensorDistance = 40*pixelToMeterRatio;
    final static int cameraSensorDistance = 16*pixelToMeterRatio;
    final static int ultrasoundSensorDistance = 2*pixelToMeterRatio;
    final static double radarSensorViewAngle = Math.toRadians(60);
    final static double ultrasoundSensorViewAngle = Math.toRadians(100);
    final static double cameraSensorViewAngle = Math.toRadians(60);


    public static Sensor createSensor(Car car, SensorType type) {
        Sensor sensor = null;
        switch (type) {
            case RADAR:
                sensor = createRadar(car);
                break;
            case CAMERA:
                sensor = createCamera(car);
                break;
            case ULTRASONIC_LSF:
                sensor = createUltrasonicLSF(car);
                break;
            case ULTRASONIC_LSB:
                sensor = createUltrasonicLSB(car);
                break;
            case ULTRASONIC_RSF:
                sensor = createUltrasonicRSF(car);
                break;
            case ULTRASONIC_RSB:
                sensor = createUltrasonicRSB(car);
                break;
            case ULTRASONIC_FL:
                sensor = createUltrasonicFL(car);
                break;
            case ULTRASONIC_FR:
                sensor = createUltrasonicFR(car);
                break;
            case ULTRASONIC_RL:
                sensor = createUltrasonicRL(car);
                break;
            case ULTRASONIC_RR:
                sensor = createUltrasonicRR(car);
                break;
            default:
                break;
        }
        return sensor;
    }

    private static Sensor createUltrasonicLSF(Car car) {
        int[] XCoords = new int[] {
                0,
                -ultrasoundSensorDistance,
                -ultrasoundSensorDistance};

        int[] YCoords = new int[] {
                carHeight*1/10,
                (int)Math.round(carHeight*1/10 - ((double)ultrasoundSensorDistance) * Math.tan(ultrasoundSensorViewAngle /2)),
                (int)Math.round(carHeight*1/10 + ((double)ultrasoundSensorDistance) * Math.tan(ultrasoundSensorViewAngle /2)) };

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_LSF
        );
        return sensor;
    }

    private static Sensor createUltrasonicLSB(Car car) {
        int[] XCoords = new int[] {
                0,
                -ultrasoundSensorDistance,
                -ultrasoundSensorDistance};

        int[] YCoords = new int[] {
                carHeight*9/10,
                (int)Math.round(carHeight*9/10 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle /2)),
                (int)Math.round(carHeight*9/10 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle /2)) };

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_LSB
        );
        return sensor;
    }

    private static Sensor createUltrasonicRSF(Car car) {
        int[] XCoords = new int[] {
                carWidth,
                carWidth + ultrasoundSensorDistance,
                carWidth + ultrasoundSensorDistance};

        int[] YCoords = new int[] {
                carHeight*1/10,
                (int)Math.round(carHeight*1/10 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle /2)),
                (int)Math.round(carHeight*1/10 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle /2)) };

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_RSB
        );
        return sensor;
    }

    private static Sensor createUltrasonicRSB(Car car) {
        int[] XCoords = new int[] {
                carWidth,
                carWidth + ultrasoundSensorDistance,
                carWidth + ultrasoundSensorDistance};

        int[] YCoords = new int[] {
                carHeight*9/10,
                (int)Math.round(carHeight*9/10 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle /2)),
                (int)Math.round(carHeight*9/10 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle /2)) };

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_RSB
        );
        return sensor;
    }

    private static Sensor createUltrasonicFL(Car car) {
        int[] XCoords = new int[] {
                carWidth*1/4,
                (int)Math.round(carWidth*1/4 - ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2)),
                (int)Math.round(carWidth*1/4 + ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2))};

        int[] YCoords = new int[] {
                0,
                - ultrasoundSensorDistance,
                - ultrasoundSensorDistance};

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_FL
        );
        return sensor;
    }

    private static Sensor createUltrasonicFR(Car car) {
        int[] XCoords = new int[] {
                carWidth*3/4,
                (int)Math.round(carWidth*3/4 - ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2)),
                (int)Math.round(carWidth*3/4 + ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2))};

        int[] YCoords = new int[] {
                0,
                - ultrasoundSensorDistance,
                - ultrasoundSensorDistance};

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_FR
        );
        return sensor;
    }

    private static Sensor createUltrasonicRL(Car car) {
        int[] XCoords = new int[] {
                carWidth*1/4,
                (int)Math.round(carWidth*1/4 - ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2)),
                (int)Math.round(carWidth*1/4 + ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2))};

        int[] YCoords = new int[] {
                carHeight,
                carHeight + ultrasoundSensorDistance,
                carHeight + ultrasoundSensorDistance};

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_RL
        );
        return sensor;
    }

    private static Sensor createUltrasonicRR(Car car) {
        int[] XCoords = new int[] {
                carWidth*3/4,
                (int)Math.round(carWidth*3/4 - ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2)),
                (int)Math.round(carWidth*3/4 + ultrasoundSensorDistance*Math.tan(ultrasoundSensorViewAngle/2))};

        int[] YCoords = new int[] {
                carHeight,
                carHeight + ultrasoundSensorDistance,
                carHeight + ultrasoundSensorDistance};

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.ULTRASONIC_RR
        );
        return sensor;
    }

    private static Sensor createRadar(Car car) {
        int[] XCoords = new int[] {
                carWidth*1/2,
                (int)Math.round(carWidth*1/2 - radarSensorDistance *Math.tan(radarSensorViewAngle /2)),
                (int)Math.round(carWidth*1/2 + radarSensorDistance *Math.tan(radarSensorViewAngle /2))};

        int[] YCoords = new int[] {
                0,
                -radarSensorDistance,
                -radarSensorDistance};

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.RADAR
        );
        return sensor;
    }

    private static Sensor createCamera(Car car) {
        int[] XCoords = new int[] {
                carWidth*1/2,
                (int)Math.round(carWidth*1/2 - cameraSensorDistance *Math.tan(cameraSensorViewAngle /2)),
                (int)Math.round(carWidth*1/2 + cameraSensorDistance *Math.tan(cameraSensorViewAngle /2))};

        int[] YCoords = new int[] {
                carHeight*1/3,
                carHeight*1/3-cameraSensorDistance,
                carHeight*1/3-cameraSensorDistance};

        Sensor sensor = new Sensor(
                XCoords,
                YCoords,
                car,
                SensorType.RADAR
        );
        return sensor;
    }
}
