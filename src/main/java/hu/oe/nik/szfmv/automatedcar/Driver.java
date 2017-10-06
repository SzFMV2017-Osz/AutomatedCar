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

	private final LocalTime STARTTIME;
	private int loopCounter = 0;
	private LocalTime elapsedTime = null;

	// LoopCounter as key value
	private Map<Integer, Integer> gasPedalProgram = new LinkedHashMap<Integer, Integer>();

	private Map<Integer, Integer> brakePedalProgram = new LinkedHashMap<Integer, Integer>();

	private Map<Integer, AutoTransmissionEnum> autoTransmissionProgram = new LinkedHashMap<Integer, AutoTransmissionEnum>();

	public Driver() {
		super();
		this.STARTTIME = LocalTime.now();

		// Defining some inputs from HMI
		autoTransmissionProgram.put(1, AutoTransmissionEnum.D);
		gasPedalProgram.put(2, 100);
		// gasPedalProgram.put(100, 100);
		// gasPedalProgram.put(90, 0);
		// brakePedalProgram.put(140, 50);
	}

	@Override
	public void loop() {
		// send demo signals
		this.loopCounter++;
		this.elapsedTime = LocalTime.now();

		AutoTransmissionEnum autoTransmission = autoTransmissionProgram.get(this.loopCounter);

		// AutoTransmission is changed
		if (autoTransmission != null) {
			System.out.format("Elapsed: %1$tM:%1$tS:%1$tL, Loop: %2$d, Autotransmission is switched to: %3$s\n",
					this.STARTTIME.until(elapsedTime, ChronoUnit.MILLIS), this.loopCounter, autoTransmission);
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AUTOTRANSMISSION, autoTransmission));
		}

		Integer gasPedal = gasPedalProgram.get(this.loopCounter);
		
		// Gas pedal is changed
		if (gasPedal != null) {
			System.out.format("Elapsed: %1$tM:%1$tS:%1$tL, Loop: %2$d, Gas pedal value: %3$d\n",
					this.STARTTIME.until(elapsedTime, ChronoUnit.MILLIS), this.loopCounter, gasPedal);
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.GASPEDAL, gasPedal));
		}

		Integer brakePedal = brakePedalProgram.get(this.loopCounter);
		
		// Brake pedal is changed
		if (brakePedal != null) {
			System.out.format("Elapsed: %1$tM:%1$tS:%1$tL, Loop: %2$d, Brake pedal value: %3$d\n",
					this.STARTTIME.until(elapsedTime, ChronoUnit.MILLIS), this.loopCounter, brakePedal);
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.BREAKPEDAL, brakePedal));
		}
	}

	@Override
	public void receiveSignal(Signal s) {
		// Prints elapsed time to console
		if (s.getId().equals(SignalEnum.ELAPSEDTIME)) {
			System.out.format("Elapsed: %1$tM:%1$tS:%1$tL ", this.STARTTIME.until(LocalTime.now(), ChronoUnit.MILLIS));
		}
	}

}
