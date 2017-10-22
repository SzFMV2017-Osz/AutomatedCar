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
	private LocalTime startTime;
	private int loopCounter;

	public int getLoopCounter() {
		return loopCounter;
	}

	private LocalTime actualTime = null;
	private boolean testDriveMode = false;
	private double revolution = 0;
	private double previousSpeed = 0;
	private double actualSpeed = 0;

	// Only for test
	// LoopCounter as key
	private Map<Integer, Integer> gasPedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, Integer> brakePedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, AutoTransmissionEnum> autoTransmissionProgram = new LinkedHashMap<Integer, AutoTransmissionEnum>();

	// Switching to test drive mode
	public void runTestDrive(Map<Integer, Integer> brakePedalProgram, Map<Integer, Integer> gasPedalProgram,
			Map<Integer, AutoTransmissionEnum> autoTransmissionProgram) {

		this.gasPedalProgram = gasPedalProgram;
		this.brakePedalProgram = brakePedalProgram;
		this.autoTransmissionProgram = autoTransmissionProgram;

		this.startTime = LocalTime.now();
		this.loopCounter = 0;
		this.testDriveMode = true;
	}

	@Override
	public void loop() {
		if (this.testDriveMode) {

			this.loopCounter++;
			this.actualTime = LocalTime.now();
			if ((this.loopCounter % 7) == 0
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
		} else {
			// send demo signal
	        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.SPEED, 10));
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
		} else {
			switch (s.getId()) {
			case AUTOTRANSMISSION:
				System.out.format("Gearbox state is %s\n", s.getData());
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
