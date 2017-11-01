package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.common.Vector2D;

public class Steering {
    double steerDirection = 0;
    double maxSteerAngle = 0.6;
    double steerSpeed = 2.5;
    double steerAdjustSpeed = 1;
    double speedSteerCorrection = 300;
    double frameRateCoefficient = 0.0417;
    float wheelBase = 10;
    float carHeading;

    /**
     Get updated steering data based on steering keystrokes and current velocity.
     @param steerLeft, left keystroke is pressed
     @param steerRight, right keystroke is pressed
     @param velocity, current velocity
     @return angle in which the tires are pointed
     */
    public Vector2D UpdateSteering(boolean steerLeft, boolean steerRight, int velocity, Vector2D carLocation){
        double steerAngle = UpdateSteerAngle(steerLeft, steerRight, velocity);
        Vector2D frontWheel = carLocation.add(new Vector2D( Math.cos(carHeading) , Math.sin(carHeading) ).mult(wheelBase/2));
        Vector2D rearWheel = carLocation.sub(new Vector2D( Math.cos(carHeading) , Math.sin(carHeading) ).mult(wheelBase/2));
        rearWheel = rearWheel.add(new Vector2D(Math.cos(carHeading) , Math.sin(carHeading)).mult(velocity).mult(frameRateCoefficient));
        frontWheel = frontWheel.add(new Vector2D(Math.cos(carHeading + steerAngle) , Math.sin(carHeading + steerAngle)).mult(velocity).mult(frameRateCoefficient));
        carLocation = frontWheel.add(rearWheel).mult(0.5);
        carHeading = (float)Math.atan2( frontWheel.getY() - rearWheel.getY() , frontWheel.getX() - rearWheel.getX() );
        return carLocation;
//        if (steerLeft) {
//            carLocation.setY(carLocation.getY() - 1);
//        }
//        if (steerRight) {
//            carLocation.setY(carLocation.getY() + 1);
//        }
//        return carLocation;
    }

    double UpdateSteerAngle(boolean steerLeft, boolean steerRight, int velocity){
        // Apply filters to our steer direction
        double steerInput = 0;
        if (steerLeft) steerInput = 1;
        if (steerRight) steerInput = -1;
        steerDirection = SmoothSteering (steerInput, velocity);

        // Calculate the current angle the tires are pointing
        double steerAngle = steerDirection * maxSteerAngle;
        return steerAngle;
    }

    double SmoothSteering(double steerInput, int velocity) {
        double steer = 0;
        if(Math.abs(steerInput) > 0.001) {
            steer = Math.max(-1, Math.min(1, steerDirection + steerInput * frameRateCoefficient * steerSpeed));
        }
        else
        {
            if (steerDirection > 0) {
                steer = Math.max(steerDirection - frameRateCoefficient * steerAdjustSpeed, 0);
            }
            else if (steerDirection < 0) {
                steer = Math.min(steerDirection + frameRateCoefficient * steerAdjustSpeed, 0);
            }
        }
        return SpeedAdjustedSteering(steer, velocity);
    }

    double SpeedAdjustedSteering(double steerInput, int velocity) {
        double steer = steerInput * (1 - (velocity / speedSteerCorrection));
        return steer;
    }
}