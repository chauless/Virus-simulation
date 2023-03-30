package cvut.fel.cz.pjv.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/*
 * This class represents a person on the pane
 * (draw, move, check for collision, recover)
 */

public class Person {
    // todo:
    // - draw: draw a person on the pane, person is a circle and have
    //                                 a color depending on the state
    // - move: move a person on the pane
    // - check for collision: check if sick person is near healthy person
    // - recover: recover a sick person after a certain time

    private Pane pane;
    private Position position;
    private Circle circle;
    private State state;
    private static final int RADIUS = 5;

    public Person(Pane pane, State state) {
        this.pane = pane;
        this.state = state;
        this.position = new Position(pane);
        this.circle = new Circle(RADIUS, state.getColor());

        pane.getChildren().add(circle);
    }

    public void draw() {
        circle.setStroke(Color.BLACK);
        circle.setTranslateX(position.getX());
        circle.setTranslateY(position.getY());
    }

    public void move() {
        // todo
    }

    public void collisionCheck() {
        // todo
    }

    public void recover() {
        // todo
    }
}
