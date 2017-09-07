package hu.oe.nik.szfmv17t.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;

public class PowertrainSystem extends SystemComponent {

	// signal id table for PowertrainSystem
	private static final int DEMO = 0;

	// input signals
	private int gasPedal = 0;

	// Output signals
	// Only these are available trough getters
	private int x = 0;
	private int y = 0;
	private double wheelAngle = 0;

	public PowertrainSystem(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public void loop() {
		//TODO write this
	}

	@Override
	public void receiveSignal(Signal s) {
		switch(s.getId()) {

			// Handle demo signal
			case DEMO:
				x += (int)s.getData();
				break;

			default:
				// ignore other signals
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getWheelAngle() {
		return wheelAngle;
	}

	public int getGasPedal() {
		return gasPedal;
	}
}
