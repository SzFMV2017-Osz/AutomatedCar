package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import java.util.Arrays;

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
	private final double MAX_SPEED = 284.37;
	private final double PEDAL_MAX_VALUE = 100;
	private final double[] GEAR_RATIOS = { -1.9583, 3.2935, 1.9583, 1.4069, 1.1321, 0.9726, 0.8187 };
	// private final double[] RPM_SPEED_RATIOS = { 0,
	private final double FINAL_DRIVE_RATIO = 3.88;
	private final double[] GEAR_UP_LEVELS = { -300, 0, 70.8, 119.1, 165.7, 206, 239.8, 300 };
	private final double WEIGHT_OF_CAR = 1337.1;
	private final double ENGINE_TORQUE = 151.2;
	private final double WHEEL_DIAMETER = 0.66675;
	private final double MIN_RPM = 600;
	private final double MAX_RPM = 7200;
	private final double RPM_SPEED_RATE = 30.87;

	// input signals
	private double gasPedal = 0;
	private int breakPedal = 0;
	private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.P;

	// Output signals
	private int gearLevel = 1;
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
		// Calculating shifting grade
		switch (this.autoTransmission) {
		case D:
			while (this.GEAR_UP_LEVELS[this.gearLevel + 1] <= this.actualSpeed) {
				this.gearLevel++;
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.ELAPSEDTESTTIME, 0));
				this.deltaSpeed = this.calculateDeltaSpeed();
				System.out.format(", Gear level: %d\n", this.gearLevel);
			}

			while (this.GEAR_UP_LEVELS[this.gearLevel] > this.actualSpeed) {
				this.gearLevel--;
				this.deltaSpeed = this.calculateDeltaSpeed();
				System.out.format("Gear level: %d\n", this.gearLevel);
			}
			break;

		case R:
			if (this.gearLevel != 0) {
				this.gearLevel = 0;
				this.deltaSpeed = this.calculateDeltaSpeed();
				System.out.format("Gear level: %d\n", this.gearLevel);
			}
			break;

		default:
			break;
		}

		// Updating actual speed and revolution
		if (Arrays.asList(AutoTransmissionEnum.D, AutoTransmissionEnum.R).contains(this.autoTransmission)) {
			// Calculating speed
			if (this.actualSpeed < this.MAX_SPEED && this.expectedRevolution > this.actualRevolution) {
				this.actualSpeed += this.deltaSpeed;
				if (this.actualSpeed > this.MAX_SPEED) {
					VirtualFunctionBus.sendSignal(new Signal(SignalEnum.ELAPSEDTESTTIME, 0));
					this.actualSpeed = this.MAX_SPEED;
				}
			}

			// Calculating revolution
			this.actualRevolution = this.RPM_SPEED_RATE * this.GEAR_RATIOS[this.gearLevel] * this.actualSpeed;
			if (this.actualRevolution < 600) {
				this.actualRevolution = 600;
			}

			// Sending revolution to Bus
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.REVOLUTION, this.actualRevolution));

			// Sending speed to Bus
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.SPEED, this.actualSpeed));

		}
	}

	private double calculateDeltaSpeed() {
		double netGearRatio = this.GEAR_RATIOS[this.gearLevel] * this.FINAL_DRIVE_RATIO;
		double torqueOnWheels = netGearRatio * this.ENGINE_TORQUE;
		double rotationalForce = torqueOnWheels / (this.WHEEL_DIAMETER / 2);
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
