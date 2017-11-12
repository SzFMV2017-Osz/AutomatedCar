package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class PowertrainSystem extends SystemComponent {

	// Parameters needed for drawing
	private static final double REFRESH_RATE = 25;
	private static final double MPS_TO_KMPH = 3.6;

	// Engine characteristics
	private final Characteristics carSpecs;

	public static final double PEDAL_MAX_VALUE = 100;

	// input signals
	private double gasPedal = 0;
	private double breakPedal = 0;
	private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.N;

	// Output signals
	private int shiftingLevel = 0;
	private byte direction;
	private double deltaSpeed = 0;
	private double actualRevolution;
	private double expectedRevolution;

	// Only these are available through getters
	private int x = 0;
	private int y = 0;
	private double actualSpeed = 0;
	private double steeringWheel;

	public PowertrainSystem(int x, int y, Characteristics carCharacteristics) {
		super();
		this.x = x;
		this.y = y;
		this.carSpecs = carCharacteristics;
		this.expectedRevolution = carSpecs.minRPM;
		this.actualRevolution = carSpecs.minRPM;
	}

	@Override
	public void loop() {
		this.deltaSpeed = this.calculateDeltaSpeed();
		switch (this.autoTransmission) {
		case D:

			// Calculating shifting level
			if (deltaSpeed > 0) {
				this.shiftingUpIfNeeded();
			} else if (deltaSpeed < 0) {
				this.shiftingDownIfNeeded();
			}
			// Updating actual speed and revolution
			this.doSpeedAdjustment(this.carSpecs.forwardMaxSpeed);
			break;
		case R:
			// Updating actual speed and revolution
			this.doSpeedAdjustment(this.carSpecs.reverseMaxSpeed);
			break;
		case N:
			// Updating actual speed and revolution
			this.doSpeedAdjustment(Double.MAX_VALUE);
			break;
		default:
			break;
		}
		this.sendSignals();
	}

	private void shiftingDownIfNeeded() {
		int shiftingLevelChange = 0;
		while (this.carSpecs.shiftingUpLevels[this.shiftingLevel + shiftingLevelChange] > Math
				.abs(this.actualSpeed)) {
			shiftingLevelChange--;
		}
		if (shiftingLevelChange < 0) {
			this.shiftingLevel += shiftingLevelChange;
			System.out.format("Shifting level: %d\n", this.shiftingLevel);
		}

	}

	private void shiftingUpIfNeeded() {
		int shiftingLevelChange = 0;
		while (this.carSpecs.shiftingUpLevels[this.shiftingLevel + shiftingLevelChange + 1] <= Math
				.abs(this.actualSpeed)) {
			shiftingLevelChange++;
		}
		if (shiftingLevelChange > 0) {
			this.shiftingLevel += shiftingLevelChange;
			System.out.format("Shifting level: %d\n", this.shiftingLevel);
		}
	}

	private void sendSignals() {
		// Sending revolution to Bus
		VirtualFunctionBus.sendSignal(new Signal(SignalEnum.REVOLUTION, this.actualRevolution));

		// Sending speed to Bus
		VirtualFunctionBus.sendSignal(new Signal(SignalEnum.SPEED, this.actualSpeed));
	}

	private void doSpeedAdjustment(double maxSpeed) {

		this.actualSpeed += this.deltaSpeed;

		// Max speed correction
		if (this.direction == 1 && Math.signum(this.actualSpeed) == 1) {
			// Max. speed limit
			if (this.actualSpeed > maxSpeed) {
				// Modifying speed
				this.actualSpeed = maxSpeed;
			}
		} else if (this.direction == -1 && Math.signum(this.actualSpeed) == -1) {
			// Max. speed limit
			if (this.actualSpeed < -maxSpeed) {
				// Modifying speed
				this.actualSpeed = -maxSpeed;
			}
		}

		// Brake effect correction
		if ((this.direction == 1 && this.deltaSpeed < 0 && this.actualSpeed < 0)
				|| (this.direction == -1 && this.deltaSpeed > 0 && this.actualSpeed > 0)) {
			this.actualSpeed = -signumOfSpeed() * 0.0001;
		}

		// Calculating revolution
		this.calculateRevolution();
	}

	private void calculateRevolution() {
		double result;
		if (this.autoTransmission.equals(AutoTransmissionEnum.N)) {
			result = carSpecs.minRPM + (carSpecs.maxRPM - carSpecs.minRPM) * this.gasPedal / this.PEDAL_MAX_VALUE;
		} else {
			result = this.carSpecs.RPMSpeedConvRate * this.carSpecs.shiftingRatios[this.shiftingLevel]
					* Math.abs(this.actualSpeed);
		}
		if (result < carSpecs.minRPM) {
			this.actualRevolution = carSpecs.minRPM;
		} else if (result > carSpecs.maxRPM) {
			this.actualRevolution = carSpecs.maxRPM;
		} else {
			this.actualRevolution = result;
		}

	}

	private double calculateDeltaSpeed() {
		boolean isAcceleration = this.expectedRevolution >= this.actualRevolution;
		double netGearRatio = this.carSpecs.shiftingRatios[this.shiftingLevel] * this.carSpecs.finalDriveRatio;
		double torqueOnWheels = netGearRatio * (isAcceleration ? this.direction * this.carSpecs.engineTorque
				: signumOfSpeed() * this.carSpecs.engineBrakingTorque
						* (this.expectedRevolution - this.actualRevolution)
						/ (this.carSpecs.maxRPM - this.carSpecs.minRPM));
		double rotationalForce = torqueOnWheels / (this.carSpecs.wheelDiameter / 2)
				- signumOfSpeed() * (this.carSpecs.maxBrakeForce * this.breakPedal / this.PEDAL_MAX_VALUE);
		double acceleration = rotationalForce / this.carSpecs.weightOfCar;
		return this.MPS_TO_KMPH * acceleration / this.REFRESH_RATE;
	}

	private double signumOfSpeed() {
		return Math.signum(this.actualSpeed);
	}

	@Override
	public void receiveSignal(Signal s) {
		switch (s.getId()) {
		case GASPEDAL:
			this.gasPedal = (int) s.getData();
			this.expectedRevolution = this.carSpecs.minRPM
					+ (this.carSpecs.maxRPM - this.carSpecs.minRPM) * this.gasPedal / this.PEDAL_MAX_VALUE;
			break;
		case BREAKPEDAL:
			this.breakPedal = (int) s.getData();
			break;
		case AUTOTRANSMISSION:
			this.autoTransmission = (AutoTransmissionEnum) s.getData();
			switch (this.autoTransmission) {
			case D:
				this.direction = 1;
				this.shiftingLevel = 0;
				this.shiftingUpIfNeeded();
				break;
			case R:
				this.direction = -1;
				this.shiftingLevel = 7;
				System.out.format("Shifting level: %d\n", this.shiftingLevel);
				break;
			case N:
				this.shiftingLevel = 0;
				System.out.format("Shifting level: %d\n", this.shiftingLevel);
				break;
			default:
				break;
			}
			break;
		case STEERINGWHEEL:
			this.steeringWheel = (int) (s.getData());
			break;
		default:
			break;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getSteeringWheel() {
		return this.steeringWheel;
	}

	public double getGasPedal() {
		return gasPedal;
	}

	public double getSpeed() {
		return this.actualSpeed;
	}

}
