import cvut.fel.cz.pjv.model.Position;
import cvut.fel.cz.pjv.model.Simulation;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * This class represents a test for the Position class.
 */
public class PositionTest {

    private Position position;
    private Pane pane;
    private Simulation simulation;

    /**
     * This method sets up the simulation before each test.
     */
    @Before
    public void setUp() {
        position = new Position(100, 100);
        pane = new Pane();
        simulation = new Simulation(pane, 5);
    }

    /**
     * This method tests the getX() method.
     */
    @Test
    public void testGetX() {
        assertEquals(100, position.getX(), 0);
    }

    /**
     * This method tests the getY() method.
     */
    @Test
    public void testGetY() {
        assertEquals(100, position.getY(), 0);
    }

    /**
     * This method tests the setX() method.
     */
    @Test
    public void testSetX() {
        position.setX(200);
        assertEquals(200, position.getX(), 0);
    }

    /**
     * This method tests the setY() method.
     */
    @Test
    public void testSetY() {
        position.setY(200);
        assertEquals(200, position.getY(), 0);
    }

    /**
     * This method tests if person stays in the same position.
     */
    @Test
    public void testStay() {
        double originalX = position.getX();
        double originalY = position.getY();
        position.stay();
        assertEquals(originalX, position.getX(), 0);
        assertEquals(originalY, position.getY(), 0);
    }

    /**
     * This method tests if the distance between two positions is calculated correctly.
     */
    @Test
    public void testDistance() {
        Position otherPosition = new Position(200, 200);
        double expectedDistance = Math.sqrt(2 * Math.pow(100, 2));
        assertEquals(expectedDistance, position.distance(otherPosition), 0);
    }

    /**
     * This method tests if two people collide.
     */
    @Test
    public void testCollision() {
        Position otherPosition = new Position(105, 105);
        assertTrue(position.collision(otherPosition));

        otherPosition = new Position(200, 200);
        assertFalse(position.collision(otherPosition));
    }
}
