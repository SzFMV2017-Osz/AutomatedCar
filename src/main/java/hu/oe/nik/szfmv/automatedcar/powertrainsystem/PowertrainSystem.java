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
	private final double FORWARD_MAX_SPEED = 284.37;
	private final double REVERSE_MAX_SPEED = 119.1;
	private final double PEDAL_MAX_VALUE = 100;
	private final double[] SHIFTING_RATIOS = { 0, 3.2935, 1.9583, 1.4069, 1.1321, 0.9726, 0.8187, -1.9583 };
	private final double FINAL_DRIVE_RATIO = 3.88;
	private final double[] SHIFTING_UP_LEVELS = { 0, 0, 69.8, 118.4, 164.4, 205.2, 239.2, 300 };
	private final double[] MAX_REVOLUTION_STEPS = { 0, 1587, 561, 290, 188, 139, 99, 561 };
	private final double WEIGHT_OF_CAR = 1337.1;
	private final double ENGINE_TORQUE = 151.2;
	private final double WHEEL_DIAMETER = 0.66675;
	private final double MIN_RPM = 600;
	private final double MAX_RPM = 7200;
	private final double RPM_SPEED_CONV_RATE = 30.87;

	private final double MAX_BRAKE_FORCE = 14000;

	// input signals
	private double gasPedal = 0;
	private double breakPedal = 0;
	private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.P;

	// Output signals
	private int shiftingLevel = 1;
	private byte direction;
	private double deltaSpeed = this.calculateDeltaSpeed();
	private double actualRevolution = 0;
	private double expectedRevolution = 600;

	// Only these are available trough getters
	private int x = 0;
	private int y = 0;
	private double wheelAngle = 0;
	private double actualSpeed = 0;

	public PowertrainSystem(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public void loop() {
		if (Math.abs(this.expectedRevolution - this.actualRevolution) >= (this.MAX_REVOLUTION_STEPS[this.shiftingLevel]
				/ this.REFRESH_RATE)) {
			this.deltaSpeed = this.calculateDeltaSpeed();
			switch (this.autoTransmission) {
			case D:
				// Calculating shifting level
				if (deltaSpeed > 0) {
					while (this.SHIFTING_UP_LEVELS[this.shiftingLevel + 1] <= this.actualSpeed) {
						this.shiftingLevel++;
						System.out.format("Shifting level: %d\n", this.shiftingLevel);
					}
				} else if (deltaSpeed < 0) {
					while (this.SHIFTING_UP_LEVELS[this.shiftingLevel] > this.actualSpeed) {
						this.shiftingLevel--;
						System.out.format("Shifting level: %d\n", this.shiftingLevel);
					}
				}

				// Updating actual speed and revolution
				this.doSpeedAdjustment(this.FORWARD_MAX_SPEED);
				break;
			case R:
				// Sets shifting level
				if (this.shiftingLevel != 7) {
					this.shiftingLevel = 7;
					System.out.format("Shifting level: %d\n", this.shiftingLevel);
				}

				// Updating actual speed and revolution
				this.doSpeedAdjustment(this.REVERSE_MAX_SPEED);
				break;
			case N:
				if (this.shiftingLevel != 0) {
					this.shiftingLevel = 0;
				}

				this.doSpeedAdjustment(Double.MAX_VALUE);
				break;
			default:
				break;
			}
			this.sendSignals();
		} else if (this.gasPedal == 0 && this.actualSpeed != 0) {
			this.actualSpeed = 0;
			this.actualRevolution = this.MIN_RPM;
			this.sendSignals();
		}
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
		this.actualRevolution = this.RPM_SPEED_CONV_RATE * this.SHIFTING_RATIOS[this.shiftingLevel] * this.actualSpeed;

		if (this.actualRevolution < 600) {
			this.actualRevolution = 600;
		}
	}

	private double calculateDeltaSpeed() {
		double netGearRatio = this.SHIFTING_RATIOS[this.shiftingLevel] * this.FINAL_DRIVE_RATIO;
		double torqueOnWheels = netGearRatio * this.ENGINE_TORQUE;
		double rotationalForce = torqueOnWheels / (this.WHEEL_DIAMETER / 2)
				- this.direction * (this.MAX_BRAKE_FORCE * this.breakPedal / this.PEDAL_MAX_VALUE);
		double acceleration = rotationalForce / this.WEIGHT_OF_CAR;
		return this.MPS_TO_KMPH * acceleration / this.REFRESH_RATE;
	}

	@Override
	public void receiveSignal(Signal s) {
		switch (s.getId()) {
		case GASPEDAL:
			this.gasPedal = (int) s.getData();
			this.expectedRevolution = this.MIN_RPM
					+ (this.MAX_RPM - this.MIN_RPM) * this.gasPedal / this.PEDAL_MAX_VALUE;
			break;
		case BREAKPEDAL:
			this.breakPedal = (int) s.getData();
			break;
		case AUTOTRANSMISSION:
			this.autoTransmission = (AutoTransmissionEnum) s.getData();
			if (this.autoTransmission.equals(AutoTransmissionEnum.D)) {
				this.direction = 1;
			} else if (this.autoTransmission.equals(AutoTransmissionEnum.R)) {
				this.direction = -1;
			}
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
