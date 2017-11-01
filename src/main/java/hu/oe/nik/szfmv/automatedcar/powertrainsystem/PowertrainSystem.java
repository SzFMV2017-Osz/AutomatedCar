package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.util.Random;

public class PowertrainSystem extends SystemComponent {

	// Parameters needed for drawing
	private final double REFRESH_RATE = 25;
	private final double MPS_TO_KMPH = 3.6;

	// Engine characteristics
	private final Characteristics carSpecs;

	public final double PEDAL_MAX_VALUE = 100;

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
	private int wheelAngle = 0;
	private double actualSpeed = 0;

    private int steer = 0;

	public PowertrainSystem(int x, int y, Characteristics carCharacteristics) {
		super();
		this.x = x;
		this.y = y;
		this.carSpecs = carCharacteristics;
		this.expectedRevolution = carSpecs.MIN_RPM;
		this.actualRevolution = carSpecs.MIN_RPM;
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

	private void shiftingDownIfNeeded() {
		int shiftingLevelChange = 0;
		while (this.carSpecs.SHIFTING_UP_LEVELS[this.shiftingLevel + shiftingLevelChange] > Math
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
		while (this.carSpecs.SHIFTING_UP_LEVELS[this.shiftingLevel + shiftingLevelChange + 1] <= Math
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
			result = carSpecs.MIN_RPM + (carSpecs.MAX_RPM - carSpecs.MIN_RPM) * this.gasPedal / this.PEDAL_MAX_VALUE;
		} else {
			result = this.carSpecs.RPM_SPEED_CONV_RATE * this.carSpecs.SHIFTING_RATIOS[this.shiftingLevel]
					* Math.abs(this.actualSpeed);
		}
		if (result < carSpecs.MIN_RPM) {
			this.actualRevolution = carSpecs.MIN_RPM;
		} else if (result > carSpecs.MAX_RPM) {
			this.actualRevolution = carSpecs.MAX_RPM;
		} else {
			this.actualRevolution = result;
		}

	}

	private double calculateDeltaSpeed() {
		boolean isAcceleration = this.expectedRevolution >= this.actualRevolution;
		double netGearRatio = this.carSpecs.SHIFTING_RATIOS[this.shiftingLevel] * this.carSpecs.FINAL_DRIVE_RATIO;
		double torqueOnWheels = netGearRatio * (isAcceleration ? this.direction * this.carSpecs.ENGINE_TORQUE
				: signumOfSpeed() * this.carSpecs.ENGINE_BRAKING_TORQUE
						* (this.expectedRevolution - this.actualRevolution)
						/ (this.carSpecs.MAX_RPM - this.carSpecs.MIN_RPM));
		double rotationalForce = torqueOnWheels / (this.carSpecs.WHEEL_DIAMETER / 2)
				- signumOfSpeed() * (this.carSpecs.MAX_BRAKE_FORCE * this.breakPedal / this.PEDAL_MAX_VALUE);
		double acceleration = rotationalForce / this.carSpecs.WEIGHT_OF_CAR;
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
			this.expectedRevolution = this.carSpecs.MIN_RPM
					+ (this.carSpecs.MAX_RPM - this.carSpecs.MIN_RPM) * this.gasPedal / this.PEDAL_MAX_VALUE;
			break;
		case BREAKPEDAL:
			this.breakPedal = (int) s.getData();
			break;
		case STEERINGWHEEL:
            this.steer = (int) s.getData();
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

	public int getWheelAngle() {
		return wheelAngle;
	}

	public double getGasPedal() {
		return gasPedal;
	}

	public double getSpeed() {
		return this.actualSpeed;
	}

    public int getSteer() {
        return steer;
    }
}
