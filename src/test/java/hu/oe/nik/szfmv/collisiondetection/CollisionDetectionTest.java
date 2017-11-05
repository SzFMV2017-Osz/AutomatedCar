package hu.oe.nik.szfmv.collisiondetection;

import hu.oe.nik.szfmv.environment.model.CollidableObject;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollisionDetectionTest {

    private TreeMock treeMock;
    private AutomatedCarMock automatedCarMock;

    @Before
    public void createObjects() throws Exception {

        this.treeMock = new TreeMock(
                10, 10, 0F, 10, 10, "tree.png", 50, ModelShape.RECTANGULAR
        );

        this.automatedCarMock = new AutomatedCarMock(
                50, 50, 0F, 102, 208, "car_2_white.png",
                ModelShape.RECTANGULAR
        );
    }

    @Test
    public void testCollision() throws Exception {
        assertEquals(this.treeMock.isCollided(),false);
        this.treeMock.onCollision();
        assertEquals(this.treeMock.isCollided(),true);
    }

    class TreeMock extends CollidableObject {

        public TreeMock(int x, int y, float rotation, int width, int height, String imageFileName, int weight, ModelShape shape) {
            super(x, y, rotation, width, height, imageFileName, weight, shape);
        }

        @Override
        protected void doOnCollision() {

        }
    }

    class AutomatedCarMock extends WorldObject {

        public AutomatedCarMock(double x, double y, double rotation, int width, int height, String imageName, ModelShape shape) {
            super(x, y, rotation, width, height, imageName, shape);
        }
    }
}