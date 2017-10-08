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
	private double actualSpeed = 0;

	// LoopCounter as key
	private Map<Integer, Integer> gasPedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, Integer> brakePedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, AutoTransmissionEnum> autoTransmissionProgram = new LinkedHashMap<Integer, AutoTransmissionEnum>();

	// Switching to test drive mode
	public void runTestDrive() {
		// Generating some driving inputs
		autoTransmissionProgram.put(1, AutoTransmissionEnum.D);
		gasPedalProgram.put(2, 50);
		gasPedalProgram.put(5 * this.REFRESH_RATE, 100);
		brakePedalProgram.put(50 * this.REFRESH_RATE, 90);
		gasPedalProgram.put(53 * this.REFRESH_RATE, 0);
		autoTransmissionProgram.put(69 * this.REFRESH_RATE, AutoTransmissionEnum.N);
		brakePedalProgram.put(70 * this.REFRESH_RATE, 0);
		autoTransmissionProgram.put(71 * this.REFRESH_RATE, AutoTransmissionEnum.R);
		gasPedalProgram.put(72 * this.REFRESH_RATE, 100);
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
				double receivedData = (double) s.getData();
				if (Math.abs(this.revolution - receivedData) >= 100) {
					this.revolution = receivedData;
					this.printHMI();
				}
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
