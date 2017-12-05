package hu.oe.nik.szfmv.environment.factory;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Sensor;
import hu.oe.nik.szfmv.environment.util.SensorType;

import java.util.ArrayList;
import java.util.List;

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
    final static double pixelToMeterRatio = 50;
    final static double carHeight = 290;
    final static double carWidth = 120;
    final static double radarSensorDistance = 40 * pixelToMeterRatio;
    final static double cameraSensorDistance = 16 * pixelToMeterRatio;
    final static double ultrasoundSensorDistance = 2 * pixelToMeterRatio;
    final static double radarSensorViewAngle = Math.toRadians(60);
    final static double ultrasoundSensorViewAngle = Math.toRadians(100);
    final static double cameraSensorViewAngle = Math.toRadians(60);

    public static List<Sensor> createAllSensor(AutomatedCar car) {
	List<Sensor> sensors = new ArrayList<Sensor>();

	Sensor radar = SensorObjectFactory.createSensor(car, SensorType.RADAR);
	sensors.add(radar);

	Sensor camera = SensorObjectFactory.createSensor(car, SensorType.CAMERA);
	sensors.add(camera);

	Sensor ultrasonicLSF = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_LSF);
	sensors.add(ultrasonicLSF);

	Sensor ultrasonicLSB = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_LSB);
	sensors.add(ultrasonicLSB);

	Sensor ultrasonicRSF = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_RSF);
	sensors.add(ultrasonicRSF);

	Sensor ultrasonicRSB = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_RSB);
	sensors.add(ultrasonicRSB);

	Sensor ultrasonicFL = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_FL);
	sensors.add(ultrasonicFL);

	Sensor ultrasonicRL = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_RL);
	sensors.add(ultrasonicRL);

	Sensor ultrasonicRR = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_RR);
	sensors.add(ultrasonicRR);

	Sensor ultrasonicFR = SensorObjectFactory.createSensor(car, SensorType.ULTRASONIC_FR);
	sensors.add(ultrasonicFR);

	return sensors;
    }

    private static Sensor createSensor(AutomatedCar car, SensorType type) {
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

    private static Sensor createUltrasonicLSF(AutomatedCar car) {
	double[] XCoords = new double[]{
	    0,
	    -ultrasoundSensorDistance,
	    -ultrasoundSensorDistance};

	double[] YCoords = new double[]{
	    car.getHeight() * 1 / 10,
	    (int) Math.round(car.getHeight() * 1 / 10 - ((double) ultrasoundSensorDistance) * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getHeight() * 1 / 10 + ((double) ultrasoundSensorDistance) * Math.tan(ultrasoundSensorViewAngle / 2))};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_LSF
	);
	return sensor;
    }

    private static Sensor createUltrasonicLSB(AutomatedCar car) {
	double[] XCoords = new double[]{
	    0,
	    -ultrasoundSensorDistance,
	    -ultrasoundSensorDistance};

	double[] YCoords = new double[]{
	    car.getHeight() * 9 / 10,
	    (int) Math.round(car.getHeight() * 9 / 10 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getHeight() * 9 / 10 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_LSB
	);
	return sensor;
    }

    private static Sensor createUltrasonicRSF(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth(),
	    car.getWidth() + ultrasoundSensorDistance,
	    car.getWidth() + ultrasoundSensorDistance};

	double[] YCoords = new double[]{
	    car.getHeight() * 1 / 10,
	    (int) Math.round(car.getHeight() * 1 / 10 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getHeight() * 1 / 10 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_RSF
	);
	return sensor;
    }

    private static Sensor createUltrasonicRSB(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth(),
	    car.getWidth() + ultrasoundSensorDistance,
	    car.getWidth() + ultrasoundSensorDistance};

	double[] YCoords = new double[]{
	    car.getHeight() * 9 / 10,
	    (int) Math.round(car.getHeight() * 9 / 10 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getHeight() * 9 / 10 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_RSB
	);
	return sensor;
    }

    private static Sensor createUltrasonicFL(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth() * 1 / 4,
	    (int) Math.round(car.getWidth() * 1 / 4 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getWidth() * 1 / 4 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	double[] YCoords = new double[]{
	    0,
	    -ultrasoundSensorDistance,
	    -ultrasoundSensorDistance};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_FL
	);
	return sensor;
    }

    private static Sensor createUltrasonicFR(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth() * 3 / 4,
	    (int) Math.round(car.getWidth() * 3 / 4 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getWidth() * 3 / 4 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	double[] YCoords = new double[]{
	    0,
	    -ultrasoundSensorDistance,
	    -ultrasoundSensorDistance};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_FR
	);
	return sensor;
    }

    private static Sensor createUltrasonicRL(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth() * 1 / 4,
	    (int) Math.round(car.getWidth() * 1 / 4 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getWidth() * 1 / 4 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	double[] YCoords = new double[]{
	    car.getHeight(),
	    car.getHeight() + ultrasoundSensorDistance,
	    car.getHeight() + ultrasoundSensorDistance};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_RL
	);
	return sensor;
    }

    private static Sensor createUltrasonicRR(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth() * 3 / 4,
	    (int) Math.round(car.getWidth() * 3 / 4 - ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2)),
	    (int) Math.round(car.getWidth() * 3 / 4 + ultrasoundSensorDistance * Math.tan(ultrasoundSensorViewAngle / 2))};

	double[] YCoords = new double[]{
	    car.getHeight(),
	    car.getHeight() + ultrasoundSensorDistance,
	    car.getHeight() + ultrasoundSensorDistance};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.ULTRASONIC_RR
	);
	return sensor;
    }

    private static Sensor createRadar(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth() * 1 / 2,
	    (int) Math.round(car.getWidth() * 1 / 2 - radarSensorDistance * Math.tan(radarSensorViewAngle / 2)),
	    (int) Math.round(car.getWidth() * 1 / 2 + radarSensorDistance * Math.tan(radarSensorViewAngle / 2))};

	double[] YCoords = new double[]{
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

    private static Sensor createCamera(AutomatedCar car) {
	double[] XCoords = new double[]{
	    car.getWidth() * 1 / 2,
	    (int) Math.round(car.getWidth() * 1 / 2 - cameraSensorDistance * Math.tan(cameraSensorViewAngle / 2)),
	    (int) Math.round(car.getWidth() * 1 / 2 + cameraSensorDistance * Math.tan(cameraSensorViewAngle / 2))};

	double[] YCoords = new double[]{
	    car.getHeight() * 1 / 3,
	    car.getHeight() * 1 / 3 - cameraSensorDistance,
	    car.getHeight() * 1 / 3 - cameraSensorDistance};

	Sensor sensor = new Sensor(
		XCoords,
		YCoords,
		car,
		SensorType.CAMERA
	);
	return sensor;
    }
}
