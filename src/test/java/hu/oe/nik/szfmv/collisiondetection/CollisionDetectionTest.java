package hu.oe.nik.szfmv.collisiondetection;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.object.Tree;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Area;

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
                100, 100, 0F, "car_2_white.png", ModelShape.RECTENGULAR
        );
    }

    @Test
    public void testCollision() throws Exception {
        assertEquals(this.treeMock.isCollided(), false);
        this.treeMock.onCollision();
        assertEquals(this.treeMock.isCollided(), true);
    }

    @Test
    public void testMoveAndCollide() throws Exception {

        Vector2D currentPossition = this.automatedCarMock.getPosition();
        this.automatedCarMock.setPosition(currentPossition.add(new Vector2D(100, 100)));

        boolean areTheyIntersecting = this.treeMock.isIntersects(this.automatedCarMock);
        assertEquals(areTheyIntersecting, false);

        this.automatedCarMock.setPosition(this.automatedCarMock.getPosition().add(new Vector2D(-100, -100)));
        assertEquals(this.treeMock.isIntersects(this.automatedCarMock), true);
    }

    class TreeMock extends Tree {

        @SuppressWarnings("WeakerAccess")
        public TreeMock(int x, int y, float rotation, String imageFileName, int weight) {
            super(x, y, rotation, imageFileName, weight);
        }

        /**
         * Determines whether or not this <code>CollidableObject</code> and the specified
         * <code>WorldObject</code> intersect. Two objects intersect if
         * their intersection is nonempty.
         *
         * @param worldObject the specified <code>WorldObject</code>
         * @return <code>true</code> if the specified <code>WorldObject</code>
         * and this <code>CollidableObject</code> intersect;
         * <code>false</code> otherwise.
         */
        boolean isIntersects(WorldObject worldObject) {
            Area areaFromThisObject = new Area(this.getShape());
            Area areaFromWorldObject = new Area(worldObject.getShape());
            areaFromThisObject.intersect(areaFromWorldObject);
            return !areaFromThisObject.isEmpty();
        }

        @Override
        protected void doOnCollision() {
            // assertEquals(this.isCollided(), false);
        }
    }

    class AutomatedCarMock extends AutomatedCar {

        @SuppressWarnings("WeakerAccess")
        public AutomatedCarMock(int x, int y, float rotation, String imageName, ModelShape shape) {
            super(x, y, rotation, imageName, shape);
        }
    }
}