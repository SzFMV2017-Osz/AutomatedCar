package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import java.util.Arrays;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class PowertrainSystem extends SystemComponent {

	// Circular track attributes
	private final double CIRCULAR_TRACK_LENGTH = 1080;
	private final int CENTER_X = 340;
	private final int CENTER_Y = 177;
	private final double TRACK_RADIUS = 172;
	private double positionOnTrack = 0;

	// Parameters needed for drawing
	private final double REFRESH_RATE = 25;
	private final double SPEED_CORRECTION = 3.6;
	private final double VISUAL_CORRECTION = 15;

	// Engine characteristics
	private final double MAX_SPEED = 284.37;
	private final double PEDAL_MAX_VALUE = 100;
	private final double[] GEAR_RATIOS = { -1.95, 3.31, 1.95, 1.41, 1.13, 0.97, 0.82 };
	private final double FINAL_DRIVE_RATIO = 3.88;
	private final double[] GEAR_LEVEL_LIMITS = { -119.09, 0, 70.81, 119.09, 165.76, 206, 239.8, 300 };
	private final double WEIGHT_OF_CAR = 1337.1;
	private final double ENGINE_TORQUE = 151.2;
	private final double WHEEL_DIAMETER = 0.66675;
	private final double MIN_RPM = 600;
	private final double MAX_RPM = 7200;

	// input signals
	private int gasPedal = 0;
	private int breakPedal = 0;
	private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.P;

	// Output signals
	private int shiftingGrade = 1;
	private double currentSpeed = 0;
	private double currentRevolution = 0;
	private double expectedRevolution = 0;
	
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
		
		// Temporary variables
		double deltaSpeed = 0;
		double netGearRatio;
		double torqueOnWheels;
		double rotationalForce;
		double acceleration;
		
		// Calculating shifting grade
		switch (this.autoTransmission) {
		case D:
			while (this.GEAR_LEVEL_LIMITS[this.shiftingGrade + 1] < this.currentSpeed) {
				this.shiftingGrade++;
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.ELAPSEDTIME, 0));
				System.out.format("Shifting grade: %d\n", this.shiftingGrade);
			}

			while (this.GEAR_LEVEL_LIMITS[this.shiftingGrade] > this.currentSpeed) {
				this.shiftingGrade--;
				System.out.format("Shifting grade: %d\n", this.shiftingGrade);
			}
			break;
			
		case R:
			this.shiftingGrade = 0;
			System.out.format("Shifting grade: %d\n", this.shiftingGrade);
			break;
			
		default:
			break;
		}

		// Calculating acceleration and delta speed
		if (Arrays.asList(AutoTransmissionEnum.D, AutoTransmissionEnum.R).contains(this.autoTransmission)
				&& this.gasPedal != 0) {
			netGearRatio = this.GEAR_RATIOS[this.shiftingGrade] * this.FINAL_DRIVE_RATIO;
			torqueOnWheels = netGearRatio * this.ENGINE_TORQUE;
			rotationalForce = torqueOnWheels / (this.WHEEL_DIAMETER / 2);
			acceleration = rotationalForce / this.WEIGHT_OF_CAR;
			deltaSpeed = this.SPEED_CORRECTION * acceleration / this.REFRESH_RATE;
		}

		// Calculating velocity
		if (this.currentSpeed < this.MAX_SPEED) {
			this.currentSpeed += deltaSpeed;
			if (this.currentSpeed > this.MAX_SPEED) {
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.ELAPSEDTIME, 0));
				this.currentSpeed = this.MAX_SPEED;
			}
		}

		// Calculating position on the circular track
		this.positionOnTrack = (int) Math.round(this.positionOnTrack + (this.currentSpeed / this.VISUAL_CORRECTION))
				% this.CIRCULAR_TRACK_LENGTH;

		this.convertToCircularTrack(this.positionOnTrack);
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

	private void convertToCircularTrack(double positionOnTrack) {
		this.x = CENTER_X
				+ (int) Math.round(TRACK_RADIUS * Math.sin(positionOnTrack * 2 * Math.PI / CIRCULAR_TRACK_LENGTH));

		this.y = CENTER_Y
				- (int) Math.round(TRACK_RADIUS * Math.cos(positionOnTrack * 2 * Math.PI / CIRCULAR_TRACK_LENGTH));
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
