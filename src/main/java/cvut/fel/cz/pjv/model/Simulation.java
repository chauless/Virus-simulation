package cvut.fel.cz.pjv.model;

import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static cvut.fel.cz.pjv.controller.SimController.log;


/**
 * Represents a simulation of people.
 * Manages the movement, collisions and states of the people in the simulation.
 */
public class Simulation {
    private ArrayList<Person> people;

    /**
     * Constructs a Simulation object with the specified pane and population size.
     *
     * @param pane       The pane to display the simulation.
     * @param population The number of people in the simulation.
     */
    public Simulation(Pane pane, int population) {
        people = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            Person person = new Person(pane, State.NOT_SICK);
            people.add(person);
        }
        people.add(new Person(pane, State.SICK)); // add one sick person

        for (Person person : people) {
            person.createThread(); // create thread for each person
        }
        log.info("People added to simulation pane");
        draw();
    }

    /**
     * Draws the people in the simulation on the pane.
     */
    public void draw() {
        for (Person person : people) {
            person.draw();
        }
    }

    /**
     * Moves the people in the simulation.
     */
    public void move() {
        for (Person person : people) {
            person.move();
        }
    }

    /**
     * Checks for collisions between people in the simulation.
     * Handles the direction changes.
     */
    public void checkCollisions() {
        for (Person person : people) {
            for (Person otherPerson : people) {
                if (person != otherPerson) {
                    person.collisionCheck(otherPerson);
                    if (otherPerson.getState() == State.SICK && // if other person is sick, try to change direction
                            person.getState() == State.NOT_SICK &&
                            person.getPosition().distance(otherPerson.getPosition()) < 2 * Person.RADIUS) {
                        person.getDirection().tryToChangeDirection(otherPerson);
                    }
                }
            }
        }
    }

    /**
     * Updates the recovery status of the people in the simulation.
     */
    public void recover() {
        for (Person person : people) {
            person.recover(person);
        }
    }

    /**
     * Manage if any sick pople die in the simulation.
     */
    public void die() {
        for (Person person : people) {
            person.dieChance();
        }
    }

    /**
     * @return The number of healthy people.
     */
    public int getHealthyPeople() {
        int healthyPeople = 0;
        for (Person person : people) {
            if (person.getState() == State.NOT_SICK) {
                healthyPeople++;
            }
        }
        return healthyPeople;
    }

    /**
     * @return The number of sick people.
     */
    public int getSickPeople() {
        int sickPeople = 0;
        for (Person person : people) {
            if (person.getState() == State.SICK) {
                sickPeople++;
            }
        }
        return sickPeople;
    }

    /**
     * @return The number of recovered people.
     */
    public int getRecoveredPeople() {
        int recoveredPeople = 0;
        for (Person person : people) {
            if (person.getState() == State.RECOVERED) {
                recoveredPeople++;
            }
        }
        return recoveredPeople;
    }

    /**
     * @return The number of dead people.
     */
    public int getDeadPeople() {
        int deadPeople = 0;
        for (Person person : people) {
            if (person.getState() == State.DEAD) {
                deadPeople++;
            }
        }
        return deadPeople;
    }

    /**
     * @return The ArrayList of people in the simulation.
     */
    public ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * Saves the configuration of the people in the simulation to a file.
     *
     * @param people               The list of people to save.
     * @param filename             The name of the file.
     * @param sliderPopulationSize The population size slider.
     * @param sliderRecoverySpeed  The recovery speed slider.
     * @param sliderMovingSpeed    The moving speed slider.
     */
    public void saveConfig(List<Person> people, String filename, Slider sliderPopulationSize,
                           Slider sliderRecoverySpeed, Slider sliderMovingSpeed) {
        List<String> lines = new ArrayList<>();
        for (Person person : people) {
            Position position = person.getPosition();
            String line = position.getX() + "," + position.getY() + "," + person.getState() + ","
                    + sliderPopulationSize.getValue() + "," + sliderRecoverySpeed.getValue() + ","
                    + sliderMovingSpeed.getValue();
            lines.add(line);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
            log.info("Configuration saved to file " + filename + ", people saved: " + people.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the configuration of the people in the simulation from a file.
     *
     * @param paneVirusSimulation  The pane to display the simulation.
     * @param filename             The name of the file.
     * @param sliderPopulationSize The population size slider.
     * @param sliderRecoverySpeed  The recovery speed slider.
     * @param sliderMovingSpeed    The moving speed slider.
     */
    public void loadConfig(Pane paneVirusSimulation, String filename, Slider sliderPopulationSize,
                           Slider sliderRecoverySpeed, Slider sliderMovingSpeed) {
        try (Reader reader = new FileReader(filename)) {
            List<String> lines = Files.readAllLines(Path.of(filename));

            paneVirusSimulation.getChildren().clear(); // clear the pane
            people.clear(); // clear the people list

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 6) {
                    log.warning("Invalid line in config file: " + line);
                    continue;
                }

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                State state = State.valueOf(parts[2]);
                double populationSize = Double.parseDouble(parts[3]);
                double recoverySpeed = Double.parseDouble(parts[4]);
                double movingSpeed = Double.parseDouble(parts[5]);

                Person person = new Person(paneVirusSimulation, state);
                Position position = person.getPosition();
                position.setX(x);
                position.setY(y);

                sliderPopulationSize.setValue(populationSize);
                sliderRecoverySpeed.setValue(recoverySpeed);
                sliderMovingSpeed.setValue(movingSpeed);

                people.add(person);
                person.createThread();
            }

            log.info("Config loaded from " + filename + ", " + lines.size() + " people loaded");
            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }}
