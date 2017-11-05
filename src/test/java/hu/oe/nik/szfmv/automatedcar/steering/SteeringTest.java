package hu.oe.nik.szfmv.automatedcar.steering;

import org.junit.Assert;

public class SteeringTest {

    private Steering steering;

    @org.junit.Before
    public void setUp() throws Exception {
        steering = new Steering();
    }

    @org.junit.Test
    public void testSmoothSteering() {
        Assert.assertEquals(steering.SmoothSteering(0, 0), 0, 0);
        Assert.assertEquals(steering.SpeedAdjustedSteering(0, 50), 0, 0);
        Assert.assertEquals(steering.SpeedAdjustedSteering(0.1, 0), 0.1, 0);
        Assert.assertEquals(steering.SpeedAdjustedSteering(-0.1, 0), -0.1, 0);
        Assert.assertEquals(steering.SpeedAdjustedSteering(0.3, 30), 0.27, 0);
        Assert.assertEquals(steering.SpeedAdjustedSteering(-0.3, 60), -0.24, 0);
    }

    @org.junit.Test
    public void testUpdateSteering() {
        Assert.assertEquals(steering.UpdateSteerAngle(true, false, 0), 0.06255000000000001, 0);
        steering.steerDirection = 0;
        Assert.assertEquals(steering.UpdateSteerAngle(false, true, 0), -0.06255000000000001, 0);
        steering.steerDirection = 0.5;
        Assert.assertEquals(steering.UpdateSteerAngle(false, false, 0), 0.27498, 0);
        Assert.assertEquals(steering.UpdateSteerAngle(false, false, 60), 0.199968, 0);
        Assert.assertEquals(steering.UpdateSteerAngle(false, true, 0), 0.137418, 0);
        Assert.assertEquals(steering.UpdateSteerAngle(true, false, 0), 0.199968, 0);
    }
}