package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.visualisation.Coord;

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
	private Coord externalCenter = new Coord(0, 0);
	private Coord rearAxleCenter;
//	private PolarCoord rearAxleCenterToLeftFrontWheelCenter;
//	private PolarCoord rearAxleCenterToRightFrontWheelCenter;
//	private PolarCoord carRefToRearAxleCenter;
//	private PolarCoord externalCenterToRearAxleCenter = new PolarCoord(0, 0);

    public AutomatedCar(int x, int y, float rotation, int width, int height, String imageFileName, int weight,
			ModelShape shape) {
       super(x, y, rotation, width, height, imageFileName, weight, shape);

        powertrainSystem = new PowertrainSystem(x, y, new PorscheCharacteristics());
    }
//		double distX, distY;
//		distX = width * 0.48;
//		distY = height * 0.65;
//		this.carRefToRearAxleCenter = new PolarCoord(new Coord(width / 2, 0.85 * height));
//		this.rearAxleCenter = carRefToRearAxleCenter.addAbsoluteCoord(x, y);
//		this.rearAxleCenterToRightFrontWheelCenter = new PolarCoord(new Coord(distX, distY));
//		this.rearAxleCenterToLeftFrontWheelCenter = new PolarCoord(new Coord(-distX, distY));
//		this.axialDistance = distY;

    public void drive() {
        VirtualFunctionBus.loop();
        	this.speed = powertrainSystem.getSpeed() / this.VISUAL_CORRECTION;
			//this.steeringWheel = powertrainSystem.getSteeringWheel();
//			calculateCarWheelAngle();
//			if (this.carWheelAngle == 0) {
//				PolarCoord delta = new PolarCoord(rotation + (speed > 0 ? Math.PI : 0), speed);
//				Coord nextPos = delta.addAbsoluteCoord(x, y);
//				x = nextPos.getX();
//				y = nextPos.getY();
//				this.externalCenter = this.carRefToRearAxleCenter
//						.addPolarCoord(this.externalCenterToRearAxleCenter.turnOver()).addAbsoluteCoord(x, y);
//				this.rearAxleCenter = this.carRefToRearAxleCenter.addAbsoluteCoord(x, y);
//			} else {
//				calculateExternalCenter();
//				calculateAngularSpeed();
//				updateCarPoints();
//				calculateRotation(this.angularSpeed);
//        }
//        accelerate();
//        move();
    }

    private void accelerate() {
//        if (this.getCurrentSpeed().abs() > 0) {
//            this.changeDirection(this.getCurrentSpeed().normalize().mult((float)powertrainSystem.getAcceleration()));
//        } else {
//            Vector2D vector =  Vector2D.getForwardVector(this.getRotation());
//            this.changeDirection(vector.mult((float)powertrainSystem.getAcceleration()));
//        }
    }

    @Override
    protected void doOnCollision() {

    }

	private void updateCarPoints() {
		if (this.steeringWheel != 0) {
//			Coord modifCoord;
//			int rightOrLeft = this.carWheelAngle > 0 ? 1 : -1;
//			this.modifyVectorsDirection(this.angularSpeed * rightOrLeft);
//			modifCoord = this.externalCenterToRearAxleCenter.addAbsoluteCoord(this.externalCenter);
//			this.rearAxleCenter.setValue(modifCoord.getX(), modifCoord.getY());
//			modifCoord = this.carRefToRearAxleCenter.turnOver().addAbsoluteCoord(this.rearAxleCenter);
//			x = modifCoord.getX();
//			y = modifCoord.getY();
		}
	}

	private void modifyVectorsDirection(double angle) {
//		this.externalCenterToRearAxleCenter.addAngle(angle);
//		this.rearAxleCenterToRightFrontWheelCenter.addAngle(angle);
//		this.rearAxleCenterToLeftFrontWheelCenter.addAngle(angle);
//		this.carRefToRearAxleCenter.addAngle(angle);
	}

	private void calculateRotation(double angle) {
//		rotation = (rotation + (this.carWheelAngle > 0 ? 1 : -1) * angle) % (2 * Math.PI);
	}

	private void calculateAngularSpeed() {
//		double arcLength = this.externalCenterToRearAxleCenter.getDistance() * 2 * Math.PI;
//		this.angularSpeed = 2 * Math.PI * this.speed / arcLength;
	}

	private void calculateExternalCenter() {
//		if (this.carWheelAngle != 0) {
//			double vectorAngle;
//			PolarCoord vector;
//			if (this.steeringWheel > 0) {
//				vectorAngle = rotation - (Math.PI / 2) + this.carWheelAngle;
//				vector = new PolarCoord(vectorAngle, Math.abs(this.axialDistance / Math.sin(this.carWheelAngle)));
//				this.externalCenter.setValue(
//						vector.addAbsoluteCoord(
//								this.rearAxleCenterToRightFrontWheelCenter.addAbsoluteCoord(this.rearAxleCenter))
//								.getX(),
//						vector.addAbsoluteCoord(
//								this.rearAxleCenterToRightFrontWheelCenter.addAbsoluteCoord(this.rearAxleCenter))
//								.getY());
//			} else {
//				vectorAngle = rotation + (Math.PI / 2) - this.carWheelAngle;
//				vector = new PolarCoord(vectorAngle, Math.abs(this.axialDistance / Math.sin(this.carWheelAngle)));
//				this.externalCenter.setValue(
//						vector.addAbsoluteCoord(rearAxleCenterToLeftFrontWheelCenter.addAbsoluteCoord(rearAxleCenter))
//								.getX(),
//						vector.addAbsoluteCoord(rearAxleCenterToLeftFrontWheelCenter.addAbsoluteCoord(rearAxleCenter))
//								.getY());
//			}
//			this.externalCenterToRearAxleCenter.updateValues(externalCenter, rearAxleCenter);
//			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.POSX, this.externalCenter.getX()));
//			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.POSY, this.externalCenter.getY()));
//		}
	}

	private double degreeToRadian(double value) {
		return Math.PI * value / 180;
	}

	private void calculateCarWheelAngle() {
		this.carWheelAngle = degreeToRadian(MAXIMUM_WHEEL_ROTATION) * this.steeringWheel / SIGNAL_MAX_VALUE;
	}
}
