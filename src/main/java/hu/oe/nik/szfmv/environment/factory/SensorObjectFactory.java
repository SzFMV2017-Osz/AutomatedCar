package hu.oe.nik.szfmv.environment.factory;

import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Sensor;
import hu.oe.nik.szfmv.environment.util.SensorType;

public class SensorObjectFactory {

    final static int pixelToMeterRatio = 50;
    final static int carHeight = 290;
    final static int carWidth = 120;
    final static int frontSensorDistance = 200*pixelToMeterRatio;
    final static int leftFrontSensorDistance = (int) Math.round(3*pixelToMeterRatio);
    final static double frontSensorViewAngle = 60;
    final static double leftFrontSensorViewAngle = 100;

    public static Sensor createSensor(Car car, SensorType type) {
        Sensor sensor = null;
        switch (type) {
            case CAMERA:
                sensor = createCamera(car);
                break;
            case ULTRASONIC_LSF:
                sensor = createUltrasonicLSF(car);
                break;
            default:
                break;
        }
        return sensor;
    }

    private static Sensor createUltrasonicLSF(Car car) {
        int[] leftFrontSensorXCoords = new int[] {
                0,
                - leftFrontSensorDistance,
                -leftFrontSensorDistance };

        int[] leftFrontSensorYCoords = new int[] {
                carHeight*1/10,
                (int)Math.round(carHeight*1/10 - leftFrontSensorDistance * Math.sin(leftFrontSensorViewAngle/2)),
                (int)Math.round(carHeight*1/10 + leftFrontSensorDistance * Math.sin(leftFrontSensorViewAngle/2)) };

        Sensor sensor = new Sensor(
                leftFrontSensorXCoords,
                leftFrontSensorYCoords,
                car,
                SensorType.ULTRASONIC_LSF
        );
        return sensor;
    }

    private static Sensor createCamera(Car car) {
        int[] frontSensorXCoords = new int[] {
                carWidth*1/2,
                (int)Math.round(carWidth*1/2 - frontSensorDistance*Math.sin(frontSensorViewAngle/2)),
                (int)Math.round(carWidth*1/2 + frontSensorDistance*Math.sin(frontSensorViewAngle/2))};

        int[] frontSensorYCoords = new int[] {
                0,
                - frontSensorDistance,
                - frontSensorDistance};

        Sensor sensor = new Sensor(
                frontSensorXCoords,
                frontSensorYCoords,
                car,
                SensorType.CAMERA
        );
        return sensor;
    }
}
