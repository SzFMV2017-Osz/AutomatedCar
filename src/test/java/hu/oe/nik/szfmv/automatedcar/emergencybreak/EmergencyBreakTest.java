package hu.oe.nik.szfmv.automatedcar.emergencybreak;
import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
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
    private AutomatedCar mockCar;
    private RadarSensor mockSensor;
    private Tree mockTree;
    List<WorldObject> worldObjects;
    @Before
    public void createObject(){
        this.mockCar=new AutomatedCar(2500, 1500, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME),
                (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);
        this.mockTree=new Tree(2500,1500,0,"tree.png", 50);
        worldObjects=new ArrayList<>();
        worldObjects.addAll(Arrays.asList(mockTree));
        this.mockSensor=new RadarSensor(mockCar,worldObjects);
        this.emergencyBreak=new EmergencyBreakSystem(mockCar,mockSensor);
    }
    @Test
    public void testSignal(){
        mockSensor.updateRadarPosition();
        emergencyBreak.loop();
        //Assert.assertTrue();
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.AEB_OFF, mockSensor));
    }

}
