package cvut.fel.cz.pjv.controller;

import cvut.fel.cz.pjv.model.Simulation;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SimController {

    @FXML
    private BorderPane borderPaneWorld;

    @FXML
    Button buttonGenerate;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonStop;

    @FXML
    private Label labelTitle;

    @FXML
    Pane paneVirusSimulation;
    Simulation sim;
    private final static int POPULATION = 150;

    @FXML
    public void generate(ActionEvent event) {
        Timer timer = new Timer();
        timer.start();

        paneVirusSimulation.getChildren().clear();
        sim = new Simulation(paneVirusSimulation, POPULATION);
    }

    @FXML
    void start(ActionEvent event) {

    }

    @FXML
    void stop(ActionEvent event) {

    }

    private class Timer extends AnimationTimer {
        private long prevTime = 0;
        private double FPS = 60e6; // 60 frames per second

        @Override
        public void handle(long now) {
            long dt = now - prevTime;
            if (dt > FPS) {
                prevTime = now;
                step();
            }
        }
    }

    public void step() {
        sim.move();
        sim.draw();
        System.out.println("step");
    }

}
