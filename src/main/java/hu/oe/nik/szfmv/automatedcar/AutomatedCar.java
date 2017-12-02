package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.common.Vector2DPlus;
import hu.oe.nik.szfmv.environment.model.MovingObject;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.model.WorldObjectCollection;
import hu.oe.nik.szfmv.environment.util.ModelShape;

import java.awt.geom.Area;
import java.util.ArrayList;

import static java.sql.DriverManager.println;

public class AutomatedCar extends MovingObject {

	private static final double MAXIMUM_WHEEL_ANGLE = 30;
	private static final double SIGNAL_MAX_VALUE = 100;
	private static final double AXIAL_DISTANCE_RATE = 0.7;
	private static final double MIDLINE_TO_WHEEL_ROTATION_AXIS_COEF = 0.48;
	private static final double REF_POINT_TO_REAR_AXIS_COEF = 0.31;
	private static final double VISUAL_CORRECTION = 5;

	private PowertrainSystem powertrainSystem;

	private double carWheelAngle;
	private double axialDistance;
	private double angularSpeed;
	private double speed;
	private Vector2DPlus externalCenter;
	private Vector2DPlus rearAxleCenter;
	private Vector2DPlus rearAxleCenterToLeftFrontWheelCenter;
	private Vector2DPlus rearAxleCenterToRightFrontWheelCenter;
	private Vector2DPlus carRefToRearAxleCenter;
	private Vector2DPlus externalCenterToRearAxleCenter;

	public AutomatedCar(int x, int y, float rotation, String imageFileName, int weight, ModelShape shape) {
		super(x, y, rotation, imageFileName, weight, shape);

		powertrainSystem = new PowertrainSystem(x, y, new PorscheCharacteristics());

		initialize();
	}

//    private void accelerate() {
//        if (this.getCurrentSpeed().abs() > 0) {
//            this.changeDirection(this.getCurrentSpeed().normalize().mult((float)powertrainSystem.getAcceleration()));
//        } else {
//            Vector2D vector =  Vector2D.getForwardVectorRadian(this.getRotation());
//            this.changeDirection(vector.mult((float)powertrainSystem.getAcceleration()));
//        }
//    }

	private void initialize() {
		double distX, distY;
		distX = getWidth() * MIDLINE_TO_WHEEL_ROTATION_AXIS_COEF;
		distY = getHeight() * AXIAL_DISTANCE_RATE;
		externalCenter = new Vector2DPlus(0, 0);
		externalCenterToRearAxleCenter = new Vector2DPlus(0, 0);
		this.carRefToRearAxleCenter = new Vector2DPlus(0, REF_POINT_TO_REAR_AXIS_COEF * getHeight());
		this.rearAxleCenter = carRefToRearAxleCenter.add(getPosition());
		this.rearAxleCenterToRightFrontWheelCenter = new Vector2DPlus(distX, -distY);
		this.rearAxleCenterToLeftFrontWheelCenter = new Vector2DPlus(-distX, -distY);
		this.axialDistance = distY;
	}

	public void drive(World world) {

		VirtualFunctionBus.loop();
		this.speed = powertrainSystem.getSpeed() / VISUAL_CORRECTION;
		this.carWheelAngle = calculateCarWheelAngle(powertrainSystem.getSteeringWheel());
		if (this.speed != 0) {
			if (this.carWheelAngle == 0) {
				Vector2DPlus delta = new Vector2DPlus(true, getRotation() + (this.speed > 0 ? 0 : -Math.PI),
						Math.abs(this.speed));
				Vector2DPlus nextPos = delta.add(getX(), getY());
				setPosition(new Vector2D(nextPos.getX(), nextPos.getY()));
				this.rearAxleCenter.addToItself(delta);
			} else {
				calculateExternalCenter();
				calculateAngularSpeed();
				updateCarPoints();
				calculateRotation(this.angularSpeed);
			}
		}
		double distance = getDistance2Points(world.getWorldObjectsFiltered().getMoving().get(0).getX() - world.getWorldObjectsFiltered().getMoving().get(1).getX(),
				world.getWorldObjectsFiltered().getMoving().get(0).getY() - world.getWorldObjectsFiltered().getMoving().get(1).getY());


		if (distance <= 300)
		{
			if (this.speed < 0)
			{
				double distInMeters = getDistanceInMeters(distance);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.PARKINGSENSOR, distInMeters));
			}
			else
			{
				double distInMeters = getDistanceInMeters(distance);
				VirtualFunctionBus.sendSignal(new Signal(SignalEnum.PARKINGSENSOR, distInMeters));
			}
		}
		else
		{
			VirtualFunctionBus.sendSignal(new Signal(SignalEnum.PARKINGSENSOR, 0));
		}

	}

	private double getDistance2Points(double x, double y)
	{
		return Math.hypot(x, y);
	}

	private double getDistanceInMeters(double distance)
	{
		double retValue = distance >= 250 ? 0.8 : 0.4;
		return  retValue;
	}

	private void updateCarPoints() {
		if (this.carWheelAngle != 0) {
			Vector2DPlus modifCoord;
			this.modifyVectorsDirection(this.angularSpeed);
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
		setRot(getRotation() + (angle % (2 * Math.PI)));
	}

	private void calculateAngularSpeed() {
		double arcLength = this.externalCenterToRearAxleCenter.getAbs() * 2 * Math.PI;
		this.angularSpeed = (this.carWheelAngle > 0 ? 1 : -1) * 2 * Math.PI * this.speed / arcLength;
	}

	private void calculateExternalCenter() {
		if (this.carWheelAngle != 0) {
			double vectorAngle;
			Vector2DPlus frontWheelToExternalCenter;
			if (this.carWheelAngle > 0) {
				vectorAngle = getRotation() + (Math.PI / 2) + this.carWheelAngle;
				frontWheelToExternalCenter = new Vector2DPlus(true, vectorAngle,
						Math.abs(this.axialDistance / Math.sin(this.carWheelAngle)));
				Vector2DPlus externalPoint = this.rearAxleCenterToRightFrontWheelCenter.add(this.rearAxleCenter)
						.add(frontWheelToExternalCenter);
				this.externalCenter.setValues(externalPoint.getX(), externalPoint.getY());
			} else {
				vectorAngle = getRotation() - (Math.PI / 2) + this.carWheelAngle;
				frontWheelToExternalCenter = new Vector2DPlus(true, vectorAngle,
						Math.abs(this.axialDistance / Math.sin(this.carWheelAngle)));
				Vector2DPlus externalPoint = this.rearAxleCenterToLeftFrontWheelCenter.add(this.rearAxleCenter)
						.add(frontWheelToExternalCenter);
				this.externalCenter.setValues(externalPoint.getX(), externalPoint.getY());
			}
			this.externalCenterToRearAxleCenter.setValues(this.rearAxleCenter.sub(this.externalCenter).getX(),
					this.rearAxleCenter.sub(this.externalCenter).getY());
		}
	}

	private double degreeToRadian(double value) {
		return Math.PI * value / 180;
	}

	private double calculateCarWheelAngle(double steeringWheel) {
		return degreeToRadian(MAXIMUM_WHEEL_ANGLE) * steeringWheel / SIGNAL_MAX_VALUE;
	}

	protected void doOnCollision() {
	}
}
