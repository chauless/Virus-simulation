package cvut.fel.cz.pjv.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class Simulation {

    private ArrayList<Person> people;

    public Simulation(Pane pane, int population) {
        people = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            people.add(new Person(pane, State.NOT_SICK));
        }
        people.add(new Person(pane, State.SICK));
        draw();
    }

    public void draw() {
        for (Person person : people) {
            person.draw();
        }
    }

    public void move() {
        for (Person person : people) {
            person.move();
        }
    }
}
