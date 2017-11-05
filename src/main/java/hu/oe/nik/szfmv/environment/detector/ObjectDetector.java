/**
 * 
 */
package hu.oe.nik.szfmv.environment.detector;

import java.awt.Shape;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.model.WorldObject;

/**
 * 
 * Class that scans a collection of <code>WorldObject</code>s with a given
 * <code>BiFunction</code> that is able to check whether to <code>Shape</code>s
 * are intersecting.
 * 
 * @author kalmankostenszky
 *
 */
public class ObjectDetector {

    private static final Logger log = LogManager.getLogger(ObjectDetector.class);

    private Collection<WorldObject> objects;
    private BiFunction<Shape, Shape, Boolean> isIntersecting;

    /**
     * Creates a new detector
     * 
     * @param objects
     *            the list of objects to scan
     * @param isIntersecting
     *            function that checks if to Shapes are intersecting
     */
    public ObjectDetector(final Collection<WorldObject> objects,
            final BiFunction<Shape, Shape, Boolean> isIntersecting) {
        super();
        this.objects = objects;
        this.isIntersecting = isIntersecting;
    }

    /**
     * return a list of sonar detectable objects whitin a given triangle Shape
     * 
     * @param triangle
     * @return
     */
    public List<ISensor> getSonarObjects(final Shape triangle) {

        log.info("getSonarObjects invoked with param: " + triangle);
        return selectIntersecting(triangle, ISensor.class);
    }

    /**
     * return a list of radar detectable objects whitin a given triangle Shape
     * 
     * @param triangle
     * @return
     */
    public List<IRadarSensor> getRadarObjects(final Shape triangle) {

        log.info("getRadarObjects invoked with param: " + triangle);
        return selectIntersecting(triangle, IRadarSensor.class);
    }

    /**
     * return a list of camera detectable objects whitin a given triangle Shape
     * 
     * @param triangle
     * @return
     */
    public List<ICameraSensor> getCameraObjects(final Shape triangle) {

        log.info("getCameraObjects invoked with param: " + triangle);
        return selectIntersecting(triangle, ICameraSensor.class);
    }

    /**
     * returns the objects of the world that are detectable by a specific sensor
     * and found whitin the given triangle Shape
     * 
     * @param triangle
     * @param type
     *            sensor type
     * @return
     */
    private <T> List<T> selectIntersecting(Shape triangle, Class<T> type) {

    		log.info("selectIntersecting is invoked for type: " + type.getName());

        try {
            return objects.parallelStream()
                    .filter(o -> type.isInstance(o) && isIntersecting.apply(o.getShape(), triangle))
                    .map(o -> type.cast(o)).collect(Collectors.toList());
        } catch (ClassCastException e) {
            log.error("Can not cast to: " + type.getName() + "... " + e.getLocalizedMessage());
            return null;
        }
    }
}
