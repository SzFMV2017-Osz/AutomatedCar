package hu.oe.nik.szfmv.automatedcar.emergencybreak;
import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.detector.RadarSensor;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Car;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class EmergencyBreakTest {
    private EmergencyBreakSystem emergencyBreak;
    private EmergencyBreakSystem movingemergencyBreak;
    private AutomatedCar mockCar;
    private MockCarMoving movingmockCar;
    private RadarSensor mockSensor;
    private RadarSensor movingmockSensor;
    private Tree mockTree,movingTree;
    List<WorldObject> worldObjects,movingWorldObjects;
    @Before
    public void createObject(){
        this.mockCar=new AutomatedCar(2500, 1500, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME),
                (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);
        this.mockTree=new Tree(2600,1500,0,"tree.png", 50);
        this.movingTree=new Tree(25,10,0,"tree.png", 50);
        worldObjects=new ArrayList<>();
        movingWorldObjects=new ArrayList<>();
        worldObjects.addAll(Arrays.asList(mockTree));
        movingWorldObjects.addAll(Arrays.asList(movingTree));
        this.mockSensor=new RadarSensor(mockCar,worldObjects);
        this.emergencyBreak=new EmergencyBreakSystem(mockCar,mockSensor);
        this.movingmockCar=new MockCarMoving(0, 0, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME),
                (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);
        this.movingmockSensor=new RadarSensor(movingmockCar,movingWorldObjects);
        this.movingemergencyBreak=new EmergencyBreakSystem(movingmockCar,movingmockSensor);
    }

    @Test
    public void testStandingSignal(){
        mockSensor.updateRadarPosition();
        emergencyBreak.loop();
        Assert.assertTrue( emergencyBreak.DetectPotentialCollision()==false);
}

    @Test
    public void testMovingSignal(){
        movingmockSensor.updateRadarPosition();
        movingemergencyBreak.loop();
        Assert.assertTrue(movingemergencyBreak.DetectPotentialCollision());
    }
}
 class MockCarMoving extends AutomatedCar{
    @SuppressWarnings("WeakerAccess")
    public MockCarMoving(int x, int y, float rotation, String imageName,int weight, ModelShape shape) {
        super(x, y, rotation, imageName, weight, shape);
        getCurrentSpeed();
    }

     @Override
     public Vector2D getImpulse() {
         return super.getImpulse();
     }

     @Override
     public Vector2D getCurrentSpeed() {
         return new Vector2D(25,0);
     }
 }
