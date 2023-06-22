package cvut.fel.cz.pjv.model;

import javafx.scene.layout.Pane;

/**
 * This class represents a position on the pane.
 * Generates a random position for a person.
 */
public class Position {
    private double x;
    private double y;

    /**
     * This method creates a position with given coordinates.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method generates a random position for a person.
     *
     * @param pane pane.
     */
    public Position(Pane pane) {
        this.x = Person.RADIUS + Math.random() * (pane.getWidth() - 3 * Person.RADIUS);
        this.y = Person.RADIUS + Math.random() * (pane.getHeight() - 3 * Person.RADIUS);
    }

    /**
     * @return x coordinate of a person.
     */
    public double getX() {
        return x;
    }

    /**
     * @param x coordinate of a person.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * This method returns a y coordinate of a person.
     */
    public double getY() {
        return y;
    }

    /**
     * @param y coordinate of a person.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * This method moves a person.
     *
     * @param movingPosition position to move a person.
     * @param pane           simulation pane.
     * @param person         person.
     */
    public void move(MovingPosition movingPosition, Pane pane, Person person) {
        x += movingPosition.getDx(person);
        y += movingPosition.getDy(person);

        if (x > pane.getWidth() - person.RADIUS * 2 || x < 0) {
            movingPosition.voidSaverX();
        }
        if (y > pane.getHeight() - person.RADIUS * 2 || y < 0) {
            movingPosition.voidSaverY();
        }
    }

    /**
     * This method makes a person stay.
     */
    public void stay() {
        x = x;
        y = y;
    }

    /**
     * This method calculates a distance between two persons.
     *
     * @param otherPerson other person.
     * @return distance between two persons.
     */
    public double distance(Position otherPerson) {
        // pythagorean theorem
        return Math.sqrt(Math.pow(this.x - otherPerson.x, 2) + Math.pow(this.y - otherPerson.y, 2));
    }

    /**
     * This method checks if two persons collide.
     *
     * @param otherPerson other person.
     * @return true if two persons collide, false otherwise.
     */
    public boolean collision(Position otherPerson) {
        return distance(otherPerson) < Person.RADIUS * 1.5;
    }
}
