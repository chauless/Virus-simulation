package cvut.fel.cz.pjv.model;

/**
 * Represents the moving position of a person in a simulation.
 * It defines the direction of movement.
 */
public class MovingPosition {
    private double dx;
    private double dy;
    private double direction;
    private final double directionChangeChance = 0.7;

    /**
     * Constructs an object with a random direction.
     */
    public MovingPosition() {
        this.direction = Math.random() * 4;
        dx = Math.sin(direction);
        dy = Math.cos(direction);
    }

    /**
     * Constructs a new MovingPosition object with the specified x and y.
     *
     * @param dx The placement along the X.
     * @param dy The placement along the Y.
     */
    public MovingPosition(double dx, double dy) {
        this.dx = 0;
        this.dy = 0;
    }

    /**
     * Reverses the placement along the X.
     * Used to change the direction of movement along the X.
     */
    public void voidSaverX() {
        dx *= -1;
    }

    /**
     * Reverses the placement along the Y.
     * Used to change the direction of movement along the Y.
     */
    public void voidSaverY() {
        dy *= -1;
    }

    /**
     * Changes the direction of movement.
     * The direction is changed if the other person is sick.
     *
     * @param otherPerson The other person.
     */
    public void tryToChangeDirection(Person otherPerson) {
        if (Math.random() < directionChangeChance) {
            if (otherPerson.getState() == State.SICK) {
                direction += Math.random() * 0.5;
            }
            dx = Math.sin(direction);
            dy = Math.cos(direction);
        }
    }

    /**
     * @param person The person.
     * @return The placement along the X.
     */
    public double getDx(Person person) {
        return dx * person.getSpeed();
    }

    /**
     * @param person The person.
     * @return The placement along the Y.
     */
    public double getDy(Person person) {
        return dy * person.getSpeed();
    }
}
