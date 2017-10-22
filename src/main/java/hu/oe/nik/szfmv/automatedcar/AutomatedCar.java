package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class AutomatedCar extends WorldObject {

	private PowertrainSystem powertrainSystem;
	private double wheelAngle = 0;

	// Variables for test
	private final double VISUAL_CORRECTION = 15;
	private final double CIRCULAR_TRACK_LENGTH = 1080;
	private boolean testMode = false;
	private double positionOnTrack = 0;

	public AutomatedCar(int x, int y, float rotation, int width, int height, String imageFileName, ModelShape shape) {
		super(x, y, rotation, width, height, imageFileName, shape);

		// Compose our car from brand new system components
		// The car has to know its PowertrainSystem, to get its coordinates
		powertrainSystem = new PowertrainSystem(x, y, new PorscheCharacteristics());
		// The rest of the components use the VirtualFunctionBus to communicate,
		// they do not communicate with the car itself
	}

	public void initTestmode() {
		testMode = true;
	}

	public void drive() {
		// call components
		VirtualFunctionBus.loop();
		// Update the position and orientation of the car
		if (!testMode) {
			x = powertrainSystem.getX();
			y += powertrainSystem.getSpeed() / this.VISUAL_CORRECTION;
			wheelAngle = (float) powertrainSystem.getWheelAngle();
		} else {
			this.positionOnTrack = this.positionOnTrack
					+ (powertrainSystem.getSpeed() / this.VISUAL_CORRECTION) % this.CIRCULAR_TRACK_LENGTH;

			x = CircularTestTrack.getX(positionOnTrack);
			y = CircularTestTrack.getY(positionOnTrack);
		}
	}
}
