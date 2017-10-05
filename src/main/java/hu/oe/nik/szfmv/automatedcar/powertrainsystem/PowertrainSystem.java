package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.AutoTransmissionEnum;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

public class PowertrainSystem extends SystemComponent {

	private final double CIRCULARTRACKLENGTH = 1080;
	private final int CENTERX = 340;
	private final int CENTERY = 177;
	private final double TRACKRADIUS = 172;
	private final double REFRESHRATE = 25;
	private final double MAXSPEED = 299.34;
	private final double PEDALMAXVALUE = 100;

	private double positionOnTrack = 0;
	private double currentSpeed = 0;

	// input signals
	private int gasPedal = 0;
	private int breakPedal = 0;
	private int steeringWheel = 0;
	private AutoTransmissionEnum autoTransmission = AutoTransmissionEnum.P;

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
		// Calculating velocity
		if (autoTransmission.equals(AutoTransmissionEnum.D)) {
			this.currentSpeed = Math.round((this.MAXSPEED * (double) this.gasPedal) / this.PEDALMAXVALUE);
		}

		// Calculating position on the circular track
		this.positionOnTrack = (int) Math.round(this.positionOnTrack + (this.currentSpeed / this.REFRESHRATE))
				% this.CIRCULARTRACKLENGTH;

		this.convertToCircularTrack(this.positionOnTrack);
	}

	@Override
	public void receiveSignal(Signal s) {
		switch (s.getId()) {
		case GASPEDAL:
			this.gasPedal = (int) s.getData();
			break;
		case BREAKPEDAL:
			this.breakPedal = (int) s.getData();
			break;
		case STEERINGWHEEL:
			this.steeringWheel = (int) s.getData();
			break;
		case AUTOTRANSMISSION:
			this.autoTransmission = (AutoTransmissionEnum) s.getData();
			break;
		default:
			// ignore other signals
		}
	}

	private void convertToCircularTrack(double positionOnTrack) {
		this.x = CENTERX
				+ (int) Math.round(TRACKRADIUS * Math.sin(positionOnTrack * 2 * Math.PI / CIRCULARTRACKLENGTH));

		this.y = CENTERY
				- (int) Math.round(TRACKRADIUS * Math.cos(positionOnTrack * 2 * Math.PI / CIRCULARTRACKLENGTH));
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
