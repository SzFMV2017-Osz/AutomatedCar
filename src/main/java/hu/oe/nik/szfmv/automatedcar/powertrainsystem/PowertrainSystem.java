package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class PowertrainSystem extends SystemComponent {

	// Parameters needed for drawing
	private final double REFRESH_RATE = 25;
	private final double MPS_TO_KMPH = 3.6;

	// Engine characteristics
	private final Characteristics carSpecs;

	private final double MAX_BRAKE_FORCE = 14000;

	// input signals
	private double gasPedal = 0;
	private double breakPedal = 0;
	private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.P;

	// Output signals
	private int shiftingLevel = 0;
	private byte direction;
	private double deltaSpeed = 0;
	private double actualRevolution = 600;
	private double expectedRevolution = 600;

	// Only these are available through getters
	private int x = 0;
	private int y = 0;
	private double wheelAngle = 0;
	private double actualSpeed = 0;

	public PowertrainSystem(int x, int y, Characteristics carCharacteristics) {
		super();
		this.x = x;
		this.y = y;
		this.carSpecs = carCharacteristics;
	}

	@Override
	public void loop() {
		this.deltaSpeed = this.calculateDeltaSpeed();
		switch (this.autoTransmission) {
		case D:
			// Calculating shifting level
			if (deltaSpeed > 0) {
				while (this.carSpecs.SHIFTING_UP_LEVELS[this.shiftingLevel + 1] <= this.actualSpeed) {
					this.shiftingLevel++;
					System.out.format("Shifting level: %d\n", this.shiftingLevel);
				}
			} else if (deltaSpeed < 0) {
				while (this.carSpecs.SHIFTING_UP_LEVELS[this.shiftingLevel] > this.actualSpeed) {
					this.shiftingLevel--;
					System.out.format("Shifting level: %d\n", this.shiftingLevel);
				}
			}
			// Updating actual speed and revolution
			this.doSpeedAdjustment(this.carSpecs.FORWARD_MAX_SPEED);
			break;
		case R:
			// Updating actual speed and revolution
			this.doSpeedAdjustment(this.carSpecs.REVERSE_MAX_SPEED);
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

	private void sendSignals() {
		// Sending revolution to Bus
		VirtualFunctionBus.sendSignal(new Signal(SignalEnum.REVOLUTION, this.actualRevolution));

		// Sending speed to Bus
		VirtualFunctionBus.sendSignal(new Signal(SignalEnum.SPEED, this.actualSpeed));

	}

	private void doSpeedAdjustment(double maxSpeed) {
		// Modifying speed
		this.actualSpeed += this.deltaSpeed;

		// Brake effect correction
		if ((this.direction == 1 && this.actualSpeed < 0) || (this.direction == -1 && this.actualSpeed > 0)) {
			this.actualSpeed = 0;
		}

		// Max. speed limit
		if (Math.abs(this.actualSpeed) > maxSpeed) {
			this.actualSpeed = this.direction * maxSpeed;
		}

		// Calculating revolution
		this.actualRevolution = this.calculateRevolution();

		if (this.actualRevolution < 600) {
			this.actualRevolution = 600;
		}
	}

	private double calculateRevolution() {
		return this.carSpecs.RPM_SPEED_CONV_RATE * this.carSpecs.SHIFTING_RATIOS[this.shiftingLevel] * this.actualSpeed;
	}

	private double calculateDeltaSpeed() {
		boolean isAcceleration = this.expectedRevolution > this.calculateRevolution();
		double netGearRatio = this.carSpecs.SHIFTING_RATIOS[this.shiftingLevel] * this.carSpecs.FINAL_DRIVE_RATIO;
		double torqueOnWheels = netGearRatio * (isAcceleration ? this.carSpecs.ENGINE_TORQUE
				: this.carSpecs.ENGINE_BRAKING_TORQUE * (this.actualRevolution - this.expectedRevolution)
						/ (this.carSpecs.MAX_RPM - this.carSpecs.MIN_RPM));
		double rotationalForce = torqueOnWheels / (this.carSpecs.WHEEL_DIAMETER / 2)
				- this.direction * (this.MAX_BRAKE_FORCE * this.breakPedal / this.carSpecs.PEDAL_MAX_VALUE);
		double acceleration = rotationalForce / this.carSpecs.WEIGHT_OF_CAR;
		return this.MPS_TO_KMPH * acceleration / this.REFRESH_RATE;
	}

	@Override
	public void receiveSignal(Signal s) {
		switch (s.getId()) {
		case GASPEDAL:
			this.gasPedal = (int) s.getData();
			this.expectedRevolution = this.carSpecs.MIN_RPM
					+ (this.carSpecs.MAX_RPM - this.carSpecs.MIN_RPM) * this.gasPedal / this.carSpecs.PEDAL_MAX_VALUE;
			break;
		case BREAKPEDAL:
			this.breakPedal = (int) s.getData();
			break;
		case AUTOTRANSMISSION:
			this.autoTransmission = (AutoTransmissionEnum) s.getData();
			switch (this.autoTransmission) {
			case D:
				this.direction = 1;
				if (this.shiftingLevel == 0 && this.actualSpeed == 0) {
					this.shiftingLevel++;
				}
				break;
			case R:
				this.direction = -1;
				this.shiftingLevel = 7;
				break;
			case N:
				this.shiftingLevel = 0;
				break;
			default:
				break;
			}
			System.out.format("Shifting level: %d\n", this.shiftingLevel);
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

	public double getGasPedal() {
		return gasPedal;
	}

	public double getSpeed() {
		return this.actualSpeed;
	}
}