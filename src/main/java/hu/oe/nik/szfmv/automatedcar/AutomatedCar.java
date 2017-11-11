package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.common.Vector2DPlus;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;

public class AutomatedCar extends MovingObject {

	private PowertrainSystem powertrainSystem;

	private final double MAXIMUM_WHEEL_ROTATION = 30;
	private final double SIGNAL_MAX_VALUE = 100;

	private final double VISUAL_CORRECTION = 5;

	private double steeringWheel = 0;
	private double carWheelAngle;
	private double axialDistance;
	private double angularSpeed;
	private double speed;
	private Vector2DPlus externalCenter = new Vector2DPlus(0, 0);
	private Vector2DPlus rearAxleCenter;
	private Vector2DPlus rearAxleCenterToLeftFrontWheelCenter;
	private Vector2DPlus rearAxleCenterToRightFrontWheelCenter;
	private Vector2DPlus carRefToRearAxleCenter;
	private Vector2DPlus externalCenterToRearAxleCenter = new Vector2DPlus(0, 0);

	public AutomatedCar(int x, int y, float rotation, String imageFileName, ModelShape shape) {
		super(x, y, rotation, imageFileName, 1337, shape);

		powertrainSystem = new PowertrainSystem(x, y, new PorscheCharacteristics());

		double distX, distY;
		distX = getWidth() * 0.48;
		distY = getHeight() * 0.65;
		this.carRefToRearAxleCenter = new Vector2DPlus(0, -0.35 * getHeight());

		this.rearAxleCenter = carRefToRearAxleCenter.add(getPosition());
		this.rearAxleCenterToRightFrontWheelCenter = new Vector2DPlus(distX, -distY);
		this.rearAxleCenterToLeftFrontWheelCenter = new Vector2DPlus(-distX, -distY);
		this.axialDistance = distY;
	}

	public void drive() {
		VirtualFunctionBus.loop();
		this.speed = powertrainSystem.getSpeed() / this.VISUAL_CORRECTION;
		this.steeringWheel = powertrainSystem.getSteeringWheel();
		calculateCarWheelAngle();
		if (this.carWheelAngle == 0) {
			Vector2DPlus delta = new Vector2DPlus(true, getRotation() + (speed > 0 ? 0 : Math.PI), speed);
			Vector2DPlus nextPos = delta.add(getX(), getY());
			setPosition(new Vector2D(nextPos.getX(), nextPos.getY()));
			this.rearAxleCenter.addToItself(delta);
		} else {
			calculateExternalCenter();
			calculateAngularSpeed();
			updateCarPoints();
			calculateRotation(this.angularSpeed);
		}
		// VirtualFunctionBus.sendSignal(new Signal(SignalEnum.STEERINGWHEEL,
		// wheelAngle));
	}

	private void updateCarPoints() {
		if (this.steeringWheel != 0) {
			Vector2DPlus modifCoord;
			int rightOrLeft = this.carWheelAngle > 0 ? 1 : -1;
			this.modifyVectorsDirection(this.angularSpeed * rightOrLeft);
			modifCoord = this.externalCenterToRearAxleCenter.add(this.externalCenter);
			this.rearAxleCenter.setValues(modifCoord.getX(), modifCoord.getY());
			modifCoord = this.carRefToRearAxleCenter.turnOver().add(this.rearAxleCenter);
			setPosition(new Vector2D(modifCoord.getX(), modifCoord.getY()));
		}
	}

	private void modifyVectorsDirection(double angle) {
		this.externalCenterToRearAxleCenter.addAngle(angle);
		this.rearAxleCenterToRightFrontWheelCenter.addAngle(angle);
		this.rearAxleCenterToLeftFrontWheelCenter.addAngle(angle);
		this.carRefToRearAxleCenter.addAngle(angle);
	}

	private void calculateRotation(double angle) {
		setRotationAngle(getRotationRadian() + ((this.carWheelAngle > 0 ? 1 : -1) * angle) % (2 * Math.PI));
	}

	private void calculateAngularSpeed() {
		double arcLength = this.externalCenterToRearAxleCenter.getAbs() * 2 * Math.PI;
		this.angularSpeed = 2 * Math.PI * this.speed / arcLength;
	}

	private void calculateExternalCenter() {
		if (this.carWheelAngle != 0) {
			double vectorAngle;
			Vector2DPlus vector;
			if (this.steeringWheel > 0) {
				vectorAngle = getRotation() + this.carWheelAngle;
				vector = new Vector2DPlus(true, vectorAngle,
						Math.abs(this.axialDistance / Math.sin(this.carWheelAngle)));
				this.externalCenter.setValues(
						this.rearAxleCenterToRightFrontWheelCenter.add(this.rearAxleCenter).add(vector).getX(),
						this.rearAxleCenterToRightFrontWheelCenter.add(this.rearAxleCenter).add(vector).getY());
			} else {
				vectorAngle = getRotation() - this.carWheelAngle;
				vector = new Vector2DPlus(true, vectorAngle,
						Math.abs(this.axialDistance / Math.sin(this.carWheelAngle)));
				this.externalCenter.setValues(
						this.rearAxleCenterToLeftFrontWheelCenter.add(this.rearAxleCenter).add(vector).getX(),
						this.rearAxleCenterToLeftFrontWheelCenter.add(this.rearAxleCenter).add(vector).getY());
			}
			this.externalCenterToRearAxleCenter.setValues(this.rearAxleCenter.sub(this.externalCenter).getX(),
					this.rearAxleCenter.sub(this.externalCenter).getY());
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.POSX, this.externalCenter.getX()));
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.POSY, this.externalCenter.getY()));
		}
	}

	private double degreeToRadian(double value) {
		return Math.PI * value / 180;
	}

	private void calculateCarWheelAngle() {
		this.carWheelAngle = degreeToRadian(MAXIMUM_WHEEL_ROTATION) * this.steeringWheel / SIGNAL_MAX_VALUE;
	}

	// private void accelerate() {
	// if (this.getCurrentSpeed().abs() > 0) {
	// this.changeDirection(this.getCurrentSpeed().normalize().mult((float)
	// powertrainSystem.getAcceleration()));
	// } else {
	// Vector2D vector = Vector2D.getForwardVector(this.getRotation());
	// this.changeDirection(vector.mult((float)
	// powertrainSystem.getAcceleration()));
	// }
	// }

	protected void doOnCollision() {

	}
}
