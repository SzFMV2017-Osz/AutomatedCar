package hu.oe.nik.szfmv.automatedcar;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

/**
 * This is an example class for the SystemComponent.
 */
public class Driver extends SystemComponent {

	// Variables for test
	private final int REFRESH_RATE = 25;
	private LocalTime startTime;
	private int loopCounter;
	private LocalTime actualTime = null;
	private boolean testDriveMode = false;
	private double revolution = 7200;
	private double previousSpeed = 0;
	private double actualSpeed = 0;

	// LoopCounter as key
	private Map<Integer, Integer> gasPedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, Integer> brakePedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, AutoTransmissionEnum> autoTransmissionProgram = new LinkedHashMap<Integer, AutoTransmissionEnum>();

	// Switching to test drive mode
	public void runTestDrive() {
		// Generating some driving inputs

		// *** Engine braking test forward and reverse
		// autoTransmissionProgram.put(1, AutoTransmissionEnum.D);
		// gasPedalProgram.put(2, 100);
		// gasPedalProgram.put(35 * this.REFRESH_RATE, 0);
		// autoTransmissionProgram.put(110 * this.REFRESH_RATE,
		// AutoTransmissionEnum.R);
		// gasPedalProgram.put(111 * this.REFRESH_RATE, 100);
		// gasPedalProgram.put(120 * this.REFRESH_RATE, 0);

		// *** Engine braking + brake pedal test 
		// autoTransmissionProgram.put(1, AutoTransmissionEnum.D);
		// gasPedalProgram.put(2, 100);
		// gasPedalProgram.put(35 * this.REFRESH_RATE, 0);
		// autoTransmissionProgram.put(111 * this.REFRESH_RATE,
		// AutoTransmissionEnum.N);
		// brakePedalProgram.put(112 * this.REFRESH_RATE, 50);

		// *** Acceleration and braking test program
		brakePedalProgram.put(1, 100);
		autoTransmissionProgram.put(2, AutoTransmissionEnum.D);
		brakePedalProgram.put(2 * this.REFRESH_RATE, 0);
		gasPedalProgram.put(6 * this.REFRESH_RATE, 50);
		gasPedalProgram.put(9 * this.REFRESH_RATE, 100);
		brakePedalProgram.put(50 * this.REFRESH_RATE, 90);
		gasPedalProgram.put(53 * this.REFRESH_RATE, 0);
		autoTransmissionProgram.put(62 * this.REFRESH_RATE, AutoTransmissionEnum.N);
		brakePedalProgram.put(63 * this.REFRESH_RATE, 100);
		autoTransmissionProgram.put(64 * this.REFRESH_RATE, AutoTransmissionEnum.R);
		brakePedalProgram.put(65 * this.REFRESH_RATE, 0);
		gasPedalProgram.put(68 * this.REFRESH_RATE, 100);
		brakePedalProgram.put(80 * this.REFRESH_RATE, 50);
		autoTransmissionProgram.put(82 * this.REFRESH_RATE, AutoTransmissionEnum.N);
		gasPedalProgram.put(83 * this.REFRESH_RATE, 0);
		brakePedalProgram.put(88 * this.REFRESH_RATE, 0);

		this.startTime = LocalTime.now();
		this.loopCounter = 0;
		this.testDriveMode = true;
	}

	@Override
	public void loop() {
		if (this.testDriveMode) {

			this.loopCounter++;
			this.actualTime = LocalTime.now();
			if ((this.loopCounter % 3) == 0
					&& Math.floor(100 * this.previousSpeed) != Math.floor(100 * this.actualSpeed)) {
				this.printHMI();
				this.previousSpeed = this.actualSpeed;
			}

			// send demo signals
			AutoTransmissionEnum autoTransmission = autoTransmissionProgram.get(this.loopCounter);

			// AutoTransmission is changed
			if (autoTransmission != null) {
				System.out.format(
						"Elapsed time: %1$tM:%1$tS:%1$tL, loop: %2$d, autotransmission is switched to: %3$s\n",
						this.startTime.until(actualTime, ChronoUnit.MILLIS), this.loopCounter, autoTransmission);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AUTOTRANSMISSION, autoTransmission));
			}

			Integer gasPedal = gasPedalProgram.get(this.loopCounter);

			// Gas pedal is changed
			if (gasPedal != null) {
				System.out.format("Elapsed time: %1$tM:%1$tS:%1$tL, loop: %2$d, gas pedal value: %3$d\n",
						this.startTime.until(actualTime, ChronoUnit.MILLIS), this.loopCounter, gasPedal);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.GASPEDAL, gasPedal));
			}

			Integer brakePedal = brakePedalProgram.get(this.loopCounter);

			// Brake pedal is changed
			if (brakePedal != null) {
				System.out.format("Elapsed time: %1$tM:%1$tS:%1$tL, loop: %2$d, brake pedal value: %3$d\n",
						this.startTime.until(actualTime, ChronoUnit.MILLIS), this.loopCounter, brakePedal);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.BREAKPEDAL, brakePedal));
			}
		}
	}

	@Override
	public void receiveSignal(Signal s) {
		// Prints elapsed time and revolution in test drive mode
		if (testDriveMode) {
			switch (s.getId()) {
			case SPEED:
				this.actualSpeed = (double) s.getData();
				break;
			case REVOLUTION:
				this.revolution = (double) s.getData();
				break;
			default:
				break;
			}
		}
	}

	private void printHMI() {
		System.out.format("Elapsed time: %1$tM:%1$tS:%1$tL, speed: %2$.3f, rpm: %3$.3f\n",
				this.startTime.until(LocalTime.now(), ChronoUnit.MILLIS), this.actualSpeed, this.revolution);
	}
}
