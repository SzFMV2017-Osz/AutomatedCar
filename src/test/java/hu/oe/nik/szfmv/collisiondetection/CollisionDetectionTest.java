package hu.oe.nik.szfmv.collisiondetection;

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
                0, 0, 0F, 10, 10, "tree.png", 50, ModelShape.RECTANGULAR
        );

        this.automatedCarMock = new AutomatedCarMock(
                100, 100, 0F, 102, 208, "car_2_white.png", ModelShape.RECTANGULAR
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
        boolean testIfTheyIntersecting = this.treeMock.isIntersects(this.automatedCarMock);
        assertEquals(testIfTheyIntersecting, false);

        this.automatedCarMock.move(-110,-110);
        testIfTheyIntersecting = this.treeMock.isIntersects(this.automatedCarMock);
        assertEquals(testIfTheyIntersecting, true);
    }

    class TreeMock extends CollidableObject {

        public TreeMock(int x, int y, float rotation, int width, int height, String imageFileName, int weight, ModelShape shape) {
            super(x, y, rotation, width, height, imageFileName, weight, shape);
        }

        boolean isIntersects(WorldObject worldObject){

            Rectangle rectangleFromThis = new Rectangle((int)this.x, (int)this.y, this.getWidth(), this.getHeight());
            Rectangle rectangleFromWorldObject = new Rectangle((int)worldObject.getX(), (int)worldObject.getY(), worldObject.getWidth(), worldObject.getHeight());

            return rectangleFromThis.intersects(rectangleFromWorldObject);
        }

        @Override
        protected void doOnCollision() {
            // assertEquals();
        }
    }

    class AutomatedCarMock extends WorldObject {

        public AutomatedCarMock(int x, int y, float rotation, int width, int height, String imageName, ModelShape shape) {
            super(x, y, rotation, width, height, imageName, shape);
        }

        public void move(int x, int y){
            this.x += x;
            this.y += y;
        }

    }
}