package hu.oe.nik.szfmv.automatedcar.steering;

public class Steering {
    double steerDirection = 0;
    double steerAngle = 0;
    double maxSteerAngle = 0.6;
    double steerSpeed = 2.5;
    double steerAdjustSpeed = 1;
    double speedSteerCorrection = 300;
    double frameRateCoefficient = 0.0417;

    /**
     * Get updated steering data based on steering keystrokes and current velocity.
     * 
     * @param steerLeft,
     *            left keystroke is pressed
     * @param steerRight,
     *            right keystroke is pressed
     * @param velocity,
     *            current velocity
     * @return angle in which the tires are pointed
     */
    double UpdateSteering(boolean steerLeft, boolean steerRight, int velocity) {
        // Apply filters to our steer direction
        double steerInput = 0;
        if (steerLeft)
            steerInput = 1;
        if (steerRight)
            steerInput = -1;
        steerDirection = SmoothSteering(steerInput, velocity);

        // Calculate the current angle the tires are pointing
        steerAngle = steerDirection * maxSteerAngle;
        return steerAngle;
    }

    double SmoothSteering(double steerInput, int velocity) {
        double steer = 0;
        if (Math.abs(steerInput) > 0.001) {
            steer = Math.max(-1, Math.min(1, steerDirection + steerInput * frameRateCoefficient * steerSpeed));
        } else {
            if (steerDirection > 0) {
                steer = Math.max(steerDirection - frameRateCoefficient * steerAdjustSpeed, 0);
            } else if (steerDirection < 0) {
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