package hu.oe.nik.szfmv.collisiondetection;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test collisions between <code>MovingObject</code>s and <code>CollidableObject</code>s
 *
 * @author danijanos
 */
public class CollisionDetectionTest {

    private TreeMock treeMock;
    private AutomatedCarMock automatedCarMock;

    @Before
    public void createObjects() throws Exception {

        this.treeMock = new TreeMock(0, 0, 0F, "tree.png", 50);

        this.automatedCarMock = new AutomatedCarMock(
                100, 100, 0F, "car_2_white.png", ModelShape.RECTANGULAR
        );
    }

    @Test
    public void testTreeCollision() throws Exception {
        assertEquals(this.treeMock.isCollided(), false);
        this.treeMock.onCollision();
        assertEquals(this.treeMock.isCollided(), true);
    }

    @Test
    public void testCarCollision() throws Exception {
        assertEquals(this.automatedCarMock.isCollided(), false);
        this.automatedCarMock.onCollision();
        assertEquals(this.automatedCarMock.isCollided(), true);
    }

    @Test
    public void testMoveAndCollide() throws Exception {

        Vector2D currentPossition = this.automatedCarMock.getPosition();
        this.automatedCarMock.setPosition(currentPossition.add(new Vector2D(100, 100)));

        this.treeMock.isIntersects(this.automatedCarMock);
        boolean areTheyCollided = this.treeMock.isCollided();
        assertEquals(areTheyCollided, false);

        // move the car into the tree
        this.automatedCarMock.setPosition(this.automatedCarMock.getPosition().add(new Vector2D(-200, -200)));
        this.treeMock.isIntersects(this.automatedCarMock);
        assertEquals(this.treeMock.isCollided(), true);
    }

    class TreeMock extends Tree {

        @SuppressWarnings("WeakerAccess")
        public TreeMock(int x, int y, float rotation, String imageFileName, int weight) {
            super(x, y, rotation, imageFileName, weight);
        }

        @Override
        protected void doOnCollision() {
            // assertEquals(this.isCollided(), false);
        }
    }

    class AutomatedCarMock extends AutomatedCar {

        @SuppressWarnings("WeakerAccess")
        public AutomatedCarMock(int x, int y, float rotation, String imageName, ModelShape shape) {
            super(x, y, rotation, imageName, 1500, shape);
        }
    }
}
