package cvut.fel.cz.pjv.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static cvut.fel.cz.pjv.controller.SimController.log;

/**
 * Represents a person in a simulation.
 * The person can move within a pane, change it's state and interact with other persons.
 */
public class Person implements Runnable {

    private Pane pane;
    private Position position;
    private Circle circle;
    private State state;
    private MovingPosition direction;
    private int speed;
    private int sickTime = 0;
    private Thread thread;

    public static final int RADIUS = 5;
    public static int recoverTime = 5 * 50; // 5 seconds in ticks

    private volatile boolean running = true; // is the simulation running
    private final Object threadLock = new Object(); // used to stop the thread

    /**
     * Constructs a new Person object.
     *
     * @param pane  The JavaFX pane.
     * @param state The state of the person.
     */
    public Person(Pane pane, State state) {
        this.pane = pane;
        this.state = state;
        this.position = new Position(pane);
        this.speed = 2;
        this.direction = new MovingPosition();
        this.circle = new Circle(RADIUS, state.getColor());

        pane.getChildren().add(circle); // add the person to the pane
    }

    /**
     * Starts the person's thread.
     * If the thread is not already running, a new thread is created and started.
     */
    public void startThread() {
        if (thread == null || !thread.isAlive()) {
            this.thread.start();
            createThread();
            this.running = true;
        }
    }

    /**
     * Stops the person's thread.
     * If the thread is running, it is stopped and the threadLock is notified.
     */
    public void stopThread() {
        synchronized (threadLock) {
            this.running = false;
            threadLock.notify();
        }
    }

    /**
     * Creates a new thread for the person.
     * */
    public void createThread() {
        this.thread = new Thread(this);
    }

    /**
     * The main behavior of the person.
     * The person moving and sometimes stays at one place.
     * The moving continues until the person's state changes to DEAD or the thread is stopped.
     */
    @Override
    public void run() {
        while (state != State.DEAD && running) {
            try {
                int probabilityOfMove = (int) (Math.random() * 250);
                if (probabilityOfMove > 1) {
                    move();
                    Thread.sleep(50 / speed); // 50 ms in ticks
                } else {
                    position.stay();
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!running) {
            try {
                synchronized (threadLock) {
                    threadLock.wait(); // wait until threadLock is notified
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Draws the person on the pane.
     * This method should be called whenever the person's position or state changes.
     */
    public void draw() {
        circle.setStroke(Color.BLACK);
        circle.setTranslateX(position.getX());
        circle.setTranslateY(position.getY());
    }

    /**
     * Moves the person to a new position.
     */
    public void move() {
        position.move(direction, pane, this);
    }

    /**
     * Checks for collision with another person and updates their states.
     *
     * @param otherPerson The other person to check collision with.
     */
    public void collisionCheck(Person otherPerson) {
        if (position.collision(otherPerson.position)) {
            if (state == State.SICK && otherPerson.state == State.NOT_SICK) {
                otherPerson.setState(State.SICK);
                otherPerson.setSpeed(1);
            }
        }
    }

    /**
     * Handles the recovery of a sick person.
     * If the person has been sick for a certain duration, they change their state to RECOVERED.
     * The speed of the given person is increased by 1.
     *
     * @param person The person to check recovery status.
     */
    public void recover(Person person) {
        if (state == State.SICK) {
            sickTime++;
            if (sickTime > recoverTime) {
                setState(State.RECOVERED);
                person.setSpeed(person.getSpeed() + 1);
            }
        }
    }

    /**
     * Handles the chance of a person dying.
     * If the person is sick, they have a 0.03% chance of dying.
     * The person's state is changed to DEAD and their direction is set to 0.
     */
    public void dieChance() {
        if (state == State.SICK) {
            if (Math.random() < 0.0003) {
                setState(State.DEAD);
                direction = new MovingPosition(0, 0);
                circle.setOpacity(0.5);
                log.info("Person on position " + position.getX() + ", " + position.getY() + " died");
            }
        }
    }

    /**
     * @return The state of the person.
     */
    public State getState() {
        return state;
    }

    /**
     * @return The person's speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return The position of the person.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return The direction of the person.
     */
    public MovingPosition getDirection() {
        return direction;
    }

    /**
     * @param speed The new speed of the person.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @param state The new state of the person.
     */
    public void setState(State state) {
        this.state = state;
        circle.setFill(state.getColor());
    }

    /**
     * @param position The new position of the person.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}
