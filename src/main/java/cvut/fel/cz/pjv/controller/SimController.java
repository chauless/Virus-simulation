package cvut.fel.cz.pjv.controller;

import cvut.fel.cz.pjv.model.Person;
import cvut.fel.cz.pjv.model.Simulation;
import cvut.fel.cz.pjv.model.State;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.*;

/**
 * This class represents a simulation controller.
 */
public class SimController {

    @FXML
    BorderPane borderPaneWorld;
    @FXML
    Button buttonGenerate;
    @FXML
    Button buttonStart;
    @FXML
    Button buttonStop;
    @FXML
    Button buttonSaveConfig;
    @FXML
    Button buttonLoadConfig;
    @FXML
    CheckBox checkBoxShowTimeline;
    @FXML
    Label labelTitle;
    @FXML
    Label labelPopulation;
    @FXML
    Label labelPopulationHealthy;
    @FXML
    Label labelPopulationSick;
    @FXML
    Label labelPopulationRecovered;
    @FXML
    Label labelPopulationDead;
    @FXML
    public Slider sliderPopulationSize;
    @FXML
    public Slider sliderRecoverySpeed;
    @FXML
    public Slider sliderMovingSpeed;
    @FXML
    CheckBox checkBoxLogging;
    @FXML
    CheckBox checkBoxLoggingToFile;
    @FXML
    Pane paneVirusSimulation;
    @FXML
    Pane paneIllnessTimeline;

    public static Logger log = Logger.getLogger(SimController.class.getName());
    private static int POPULATION = 150;
    Simulation sim;
    Timer timer;
    Handler fileHandler;
    HashMap<State, Rectangle> illnessTimeline = new HashMap<>();

    /**
     * This method generates a simulation.
     */
    @FXML
    public void generate() {
        disableButtons(false, false, true, false, false, false, false);
        paneVirusSimulation.getChildren().clear();
        sim = new Simulation(paneVirusSimulation, POPULATION);

        getStatistics();
        setMovingSpeed();
        setPopulationSize();
        setRecoverySpeed();

        log.log(Level.INFO, "Simulation generated, time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

//        paneIllnessTimeline.getChildren().clear();
//        for (State state : State.values()) {
//            Rectangle rectangle = new Rectangle(50, 0, state.getColor());
//            illnessTimeline.put(state, rectangle);
//        }
    }

    /**
     * This method starts the simulation.
     */
    @FXML
    void start() {
        if (sim == null) {
            disableButtons(false, true, true, false, true, false, false);
            return;
        }

        timer = new Timer();
        timer.start();
        disableButtons(true, true, false, true, false, false, true);

        for (Person person : sim.getPeople()) {
            person.startThread();
        }

        System.out.println(Thread.activeCount());
        log.log(Level.INFO, "Simulation started with population: " + (POPULATION + 1));
    }

    /**
     * This method stops the simulation.
     */
    @FXML
    void stop() {
        timer.stop();
        disableButtons(false, false, true, false, false, false, false);

        for (Person person : sim.getPeople()) {
            person.stopThread();
        }

        log.log(Level.INFO, "Simulation stopped");
    }

    /**
     * This method saves the simulation configuration.
     *
     * @param event event
     */
    @FXML
    void saveConfig(ActionEvent event) {
        String filename = "Configs/config" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss")) + ".json";
        sim.saveConfig(sim.getPeople(), filename, sliderPopulationSize, sliderMovingSpeed, sliderRecoverySpeed);
    }

    /**
     * This method loads the simulation configuration.
     *
     * @param event event
     */
    @FXML
    void loadConfig(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open config file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );

        // initial directory for dev purposes, if not exists, it won't be used
        File initialDirectory = new File("C:\\Users\\lockp\\Documents\\Coding\\PJV\\morenmat\\Configs");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String filename = selectedFile.getAbsolutePath();
            sim.loadConfig(paneVirusSimulation, filename, sliderPopulationSize, sliderMovingSpeed, sliderRecoverySpeed);
            setRecoverySpeed();
            setMovingSpeed();
            setPopulationSize();
            getStatistics();
        }
    }

    /**
     * This method make a step in the simulation.
     */
    public void step() {
        sim.checkCollisions();
        sim.recover();
        sim.die();

        getStatistics();

        sim.draw();

        // check if simulation is finished
        if (sim.getSickPeople() == 0) {
            timer.stop();
            disableButtons(false, true, true, false, false, false, false);
            createFinalPopup();
            log.log(Level.INFO, "Simulation successfully finished");
        }
    }

    /**
     * Disables/enables various buttons and sliders in the UI.
     *
     * @param generate            Whether to disable/enable the generate button.
     * @param start               Whether to disable/enable the start button.
     * @param stop                Whether to disable/enable the stop button.
     * @param sliderPopulationSize Whether to disable/enable the population size slider.
     * @param sliderMovingSpeed    Whether to disable/enable the moving speed slider.
     * @param buttonSaveConfig     Whether to disable/enable the save config button.
     * @param buttonLoadConfig     Whether to disable/enable the load config button.
     */
    public void disableButtons(boolean generate, boolean start, boolean stop, boolean sliderPopulationSize,
                               boolean sliderMovingSpeed, boolean buttonSaveConfig, boolean buttonLoadConfig) {
        buttonGenerate.setDisable(generate);
        buttonStart.setDisable(start);
        buttonStop.setDisable(stop);
        this.sliderPopulationSize.setDisable(sliderPopulationSize);
        this.sliderMovingSpeed.setDisable(sliderMovingSpeed);
        this.buttonSaveConfig.setDisable(buttonSaveConfig);
        this.buttonLoadConfig.setDisable(buttonLoadConfig);
    }

    /**
     * Updates statistics about the current simulation and updates the UI labels.
     */
    private void getStatistics() {
        labelPopulation.setText("Population: " + (POPULATION + 1));
        labelPopulationHealthy.setText("Healthy: " + sim.getHealthyPeople());
        labelPopulationSick.setText("Sick: " + sim.getSickPeople());
        labelPopulationRecovered.setText("Recovered: " + sim.getRecoveredPeople());
        labelPopulationDead.setText("Dead: " + sim.getDeadPeople());
    }

    /**
     * Creates a popup window to display the final statistics of the simulation.
     */
    private void createFinalPopup() {
        Stage stage = new Stage();
        stage.setTitle("Statistics");
        VBox root = new VBox();
        Scene scene = new Scene(root, 300, 200);
        Label label = new Label("Simulation successfully finished!");
        label.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");

        Label statistics = new Label("Healthy people: " + sim.getHealthyPeople() + "\n" +
                "Recovered people: " + sim.getRecoveredPeople() + "\n" +
                "Dead people: " + sim.getDeadPeople() + "\n");

        Button button = new Button("Close statistics");
        button.setOnAction(e -> stage.close());

        VBox.setMargin(label, new Insets(0, 0, 20, 0));
        VBox.setMargin(button, new Insets(20, 0, 0, 0));

        label.setTranslateX(50);
        label.setTranslateY(50);
        statistics.setTranslateX(50);
        statistics.setTranslateY(50);
        button.setTranslateX(50);
        button.setTranslateY(50);

        root.getChildren().add(label);
        root.getChildren().add(statistics);
        root.getChildren().add(button);

        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    private void showTimeline() {
//        int healthyPeople = sim.getHealthyPeople();
//        int sickPeople = sim.getSickPeople();
//        int recoveredPeople = sim.getRecoveredPeople();
//        int deadPeople = sim.getDeadPeople();
//
//        addCircles(healthyPeople, State.NOT_SICK);
//        addCircles(sickPeople, State.SICK);
//        addCircles(recoveredPeople, State.RECOVERED);
//        addCircles(deadPeople, State.DEAD);
//    }
//
//    private void addCircles(int count, State state) {
//        for (int i = 0; i < count; i++) {
//            Circle circle = new Circle(1, state.getColor());
//            circle.setTranslateX(timer.getTicks() / 5.0);
//            circle.setTranslateY(130 - count);
//            paneIllnessTimeline.getChildren().add(circle);
//        }
//    }

    /**
     * Initializes the UI components and sets up event listeners.
     */
    public void initialize() {
        disableButtons(false, true, true, false, true, true, true);
        sliderPopulationSize.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                setPopulationSize();
            }
        });

        sliderRecoverySpeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                setRecoverySpeed();
            }
        });

        sliderMovingSpeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                setMovingSpeed();
            }
        });

        checkBoxLogging.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean old_val, Boolean new_val) {
                if (checkBoxLogging.isSelected()) {
                    log.setLevel(Level.ALL);
                    log.log(Level.INFO, "Logging enabled");
                } else {
                    log.log(Level.INFO, "Logging disabled");
                    log.setLevel(Level.OFF);
                }
            }
        });

        checkBoxLoggingToFile.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean old_val, Boolean new_val) {
                if (checkBoxLoggingToFile.isSelected()) {
                    try {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");
                        LocalDateTime now = LocalDateTime.now();
                        fileHandler = new FileHandler("Logs/log" + dateTimeFormatter.format(now) + ".txt", 100 * 1024, 3, true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    fileHandler.setFormatter(new SimpleFormatter());
                    log.addHandler(fileHandler);
                    log.log(Level.INFO, "Logging to file enabled");
                } else {
                    log.log(Level.INFO, "Logging to file disabled");
                    log.removeHandler(fileHandler);
                }
            }
        });
    }

    /**
     * Sets the population size based on the value of the population size slider.
     */
    public void setPopulationSize() {
        POPULATION = (int) sliderPopulationSize.getValue();
        log.log(Level.INFO, "Population size changed to " + POPULATION);
    }

    /**
     * Sets the recovery speed based on the value of the recovery speed slider.
     */
    public void setRecoverySpeed() {
        int prevSpeed = Person.recoverTime;
        Person.recoverTime = 50 * (int) sliderRecoverySpeed.getValue();
        if (prevSpeed - Person.recoverTime > 0.5 || prevSpeed - Person.recoverTime < -0.5) {
            log.log(Level.INFO, "Recovery speed changed to " + Person.recoverTime);
        }
    }

    /**
     * Sets the moving speed of the healthy people based on the value of the moving speed slider.
     */
    public void setMovingSpeed() {
        int newSpeed = (int) sliderMovingSpeed.getValue();
        Person[] people = sim.getPeople().toArray(new Person[0]);
        int prevSpeed = 0;
        for (Person person : people) {
            if (person.getState() != State.SICK) {
                prevSpeed = person.getSpeed();
                person.setSpeed(newSpeed);
            }
        }
        if (newSpeed != prevSpeed)
            log.log(Level.INFO, "Moving speed changed to " + newSpeed);
    }

    /**
     * Returns the simulation pane.
     *
     * @return The simulation pane.
     */
    public Pane getPane() {
        return paneVirusSimulation;
    }

    /**
     * This class represents a timer for controlling the animation and updating the simulation.
     */
    private class Timer extends AnimationTimer {
        private long prevTime = 0;
        private int ticks;
        private double FPS = 120; // 50 frames per second
        private double UPS = 1e9 / FPS; // update per second

        @Override
        public void handle(long now) {
            long dt = now - prevTime;
            if (dt > UPS) {
                prevTime = now;
                step();
                tick();
            }
        }

        public void tick() {
            ticks++;
        }

        public void resetTicks() {
            ticks = 0;
        }

        public double getTicks() {
            return ticks;
        }
    }


}
