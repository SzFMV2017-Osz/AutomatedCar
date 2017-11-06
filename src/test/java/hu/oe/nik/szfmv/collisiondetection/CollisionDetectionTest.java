package hu.oe.nik.szfmv.collisiondetection;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class CollisionDetectionTest {

    private TreeMock treeMock;
    private AutomatedCarMock automatedCarMock;

    @Before
    public void createObjects() throws Exception {

        this.treeMock = new TreeMock(
                0, 0, 0F, "tree.png", 50, ModelShape.RECTENGULAR
        );

        this.automatedCarMock = new AutomatedCarMock(
                100, 100, 0F,"car_2_white.png", ModelShape.RECTENGULAR
        );
    }

    @Test
    public void testCollision() throws Exception {
        assertEquals(this.treeMock.isCollided(),false);
        this.treeMock.onCollision();
        assertEquals(this.treeMock.isCollided(),true);
    }

    @Test
    public void testMoveAndCollide() throws Exception{
        this.automatedCarMock.move(10,10);
        assertEquals(this.automatedCarMock.getX(),110,0.1);
        assertEquals(this.automatedCarMock.getY(),110,0.1);

        boolean areTheyIntersecting = this.treeMock.isIntersects(this.automatedCarMock);
        assertEquals(areTheyIntersecting, false);

        this.automatedCarMock.move(-110,-110);
        assertEquals(this.automatedCarMock.getX(),0,0.1);
        assertEquals(this.automatedCarMock.getY(),0,0.1);

        areTheyIntersecting = this.treeMock.isIntersects(this.automatedCarMock);
        assertEquals(areTheyIntersecting, true);
    }

    class TreeMock extends CollidableObject {

        public TreeMock(int x, int y, float rotation, String imageFileName, int weight, ModelShape shape) {
            super(x, y, rotation, imageFileName, weight, shape);
        }

        boolean isIntersects(WorldObject worldObject){

            Rectangle rectangleFromThis = new Rectangle((int)this.getX(), (int)this.getY(), this.getWidth(), this.getHeight());
            Rectangle rectangleFromWorldObject = new Rectangle((int)worldObject.getX(), (int)worldObject.getY(), worldObject.getWidth(), worldObject.getHeight());

            return rectangleFromThis.intersects(rectangleFromWorldObject);
        }

        @Override
        protected void doOnCollision() {
            // assertEquals(this.isCollided(), false);
        }
    }

    class AutomatedCarMock extends WorldObject {

        public AutomatedCarMock(int x, int y, float rotation, String imageName, ModelShape shape) {
            super(x, y, rotation, imageName, shape);
        }

        public void move(int x, int y){
            this.setPosition(new Vector2D(x,y));
        }

    }
}