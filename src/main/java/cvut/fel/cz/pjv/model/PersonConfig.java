package cvut.fel.cz.pjv.model;

/**
 * Represents the configuration for a person in a simulation.
 * It specifies the position, state, and parameters of the simulation.
 */
public class PersonConfig {
    private double x;
    private double y;
    private State state;
    private double populationSize;
    private double recoverySpeed;
    private double movingSpeed;

    /**
     * Constructs a new PersonConfig object with default values.
     */
    public PersonConfig() {
    }

    /**
     * @return The x coordinate of the person.
     */
    public double getX() {
        return x;
    }

    /**
     * @param x The x coordinate of the person.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return The y coordinate of the person.
     */
    public double getY() {
        return y;
    }

    /**
     * @param y The y coordinate of the person.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return The state of the person.
     */
    public State getState() {
        return state;
    }

    /**
     * @param state The state of the person.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return The size of the population.
     */
    public double getPopulationSize() {
        return populationSize;
    }

    /**
     * @param populationSize The size of the population.
     */
    public void setPopulationSize(double populationSize) {
        this.populationSize = populationSize;
    }

    /**
     * @return The recovery speed of the person.
     */
    public double getRecoverySpeed() {
        return recoverySpeed;
    }

    /**
     * @param recoverySpeed The recovery speed of the person.
     */
    public void setRecoverySpeed(double recoverySpeed) {
        this.recoverySpeed = recoverySpeed;
    }

    /**
     * @return The moving speed of the person.
     */
    public double getMovingSpeed() {
        return movingSpeed;
    }

    /**
     * @param movingSpeed The moving speed of the person.
     */
    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }
}
