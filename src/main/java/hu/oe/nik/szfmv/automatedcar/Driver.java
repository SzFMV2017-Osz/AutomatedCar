package hu.oe.nik.szfmv.automatedcar;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
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
	private LocalTime actualTime = null;
	private boolean testDriveMode = false;
	private double revolution = 7200;
	private int actualSpeed = 0;

	// LoopCounter as key
	private Map<Integer, Integer> gasPedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, Integer> brakePedalProgram = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, AutoTransmissionEnum> autoTransmissionProgram = new LinkedHashMap<Integer, AutoTransmissionEnum>();

	// Switching to test drive mode
	public void runTestDrive() {
		// Defining some inputs
		gasPedalProgram.put(1, 100);
		autoTransmissionProgram.put(2, AutoTransmissionEnum.D);

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
						"Elapsed time: %1$tM:%1$tS:%1$tL, Loop: %2$d, Autotransmission is switched to: %3$s\n",
						this.startTime.until(actualTime, ChronoUnit.MILLIS), this.loopCounter, autoTransmission);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AUTOTRANSMISSION, autoTransmission));
			}

			Integer gasPedal = gasPedalProgram.get(this.loopCounter);

			// Gas pedal is changed
			if (gasPedal != null) {
				System.out.format("Elapsed time: %1$tM:%1$tS:%1$tL, Loop: %2$d, Gas pedal value: %3$d\n",
						this.startTime.until(actualTime, ChronoUnit.MILLIS), this.loopCounter, gasPedal);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.GASPEDAL, gasPedal));
			}

			Integer brakePedal = brakePedalProgram.get(this.loopCounter);

			// Brake pedal is changed
			if (brakePedal != null) {
				System.out.format("Elapsed time: %1$tM:%1$tS:%1$tL, Loop: %2$d, Brake pedal value: %3$d\n",
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
			case ELAPSEDTESTTIME:
				System.out.format(" Elapsed time: %1$tM:%1$tS:%1$tL",
						this.startTime.until(LocalTime.now(), ChronoUnit.MILLIS));
				break;
			case SPEED:
				this.actualSpeed = (int) Math.round((double) s.getData());
				break;
			case REVOLUTION:
				double receivedData = (double) s.getData();
				if (this.revolution > receivedData) {
					this.revolution = receivedData;
					System.out.print(String.join("", Collections.nCopies((int) (this.revolution / 100), "\u2588")));
				} else {
					if ((receivedData - this.revolution) >= 100) {
						int nTimes = (int) (receivedData - this.revolution) / 100;
						System.out.print(String.join("", Collections.nCopies(nTimes, "\u2588")));
						this.revolution += 100 * nTimes;
					}
				}
				break;
			default:
				break;
			}
		}
	}

}
