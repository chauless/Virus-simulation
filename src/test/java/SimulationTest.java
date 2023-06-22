import cvut.fel.cz.pjv.model.Person;
import cvut.fel.cz.pjv.model.Position;
import cvut.fel.cz.pjv.model.Simulation;
import cvut.fel.cz.pjv.model.State;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class represents a test for the Simulation class.
 */
public class SimulationTest {

    private Simulation simulation;
    private Pane pane;

    /**
     * This method sets up the simulation before each test.
     */
    @Before
    public void setUp() {
        pane = new Pane();
        simulation = new Simulation(pane, 5);
    }

    /**
     * This method tests the constructor of the Simulation class.
     */
    @Test
    public void testConstructor() {
        List<Person> people = simulation.getPeople();

        assertEquals(6, people.size()); // check that there are 6 people in the simulation (5 healthy, 1 sick)
        assertEquals(State.SICK, people.get(5).getState()); // check that the last person is sick
    }

    /**
     * This method tests how the simulation recovers people.
     */
    @Test
    public void testRecover() {
        List<Person> people = simulation.getPeople();

        people.get(0).setState(State.SICK);
        people.get(1).setState(State.RECOVERED);

        people.get(1).recoverTime = 0; // set recoverTime to 0 for testing
        simulation.recover();

        assertEquals(State.RECOVERED, people.get(0).getState()); // check that sick person recovered
        assertEquals(State.RECOVERED, people.get(1).getState()); // check that recovered person is still recovered
    }

    /**
     * This method tests getting the number of healthy people.
     */
    @Test
    public void testGetHealthyPeople() {
        List<Person> people = new ArrayList<>();
        people.add(new Person(pane, State.NOT_SICK)); // healthy
        people.add(new Person(pane, State.NOT_SICK)); // healthy
        people.add(new Person(pane, State.SICK));
        people.add(new Person(pane, State.RECOVERED));
        people.add(new Person(pane, State.DEAD));

        simulation = new Simulation(pane, people.size());
        simulation.getPeople().clear();
        simulation.getPeople().addAll(people);

        assertEquals(2, simulation.getHealthyPeople()); // check that 2 people are healthy
    }

    /**
     * This method tests moving people.
     */
    @Test
    public void testMove() {
        List<Person> people = simulation.getPeople();

        for (Person person : people) {
            person.setPosition(new Position(0, 0));
        }

        simulation.move();

        for (Person person : people) {
            assertFalse(person.getPosition().equals(new Position(0, 0))); // check that people moved
        }
    }

    /**
     * This method tests checking collisions between people.
     */
    @Test
    public void testCheckCollisions() {
        List<Person> people = simulation.getPeople();

        people.get(0).setState(State.NOT_SICK);
        people.get(1).setState(State.SICK);
        people.get(0).setPosition(new Position(0, 0));
        people.get(1).setPosition(new Position(Person.RADIUS, 0));

        simulation.checkCollisions();

        assertEquals(State.SICK, people.get(0).getState()); // check that healthy person got sick
    }
}
