package cvut.fel.cz.pjv.model;

/*
 * This class represents a position on the pane
 * and generates a random position for a person
 */

import javafx.scene.layout.Pane;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Pane pane) {
        this.x = Math.random() * pane.getWidth();
        this.y = Math.random() * pane.getHeight();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // todo:
    // - generate random position on the pane
}
