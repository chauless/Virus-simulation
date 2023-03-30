package cvut.fel.cz.pjv.controller;

import cvut.fel.cz.pjv.model.Simulation;
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
    void generate(ActionEvent event) {
        paneVirusSimulation.getChildren().clear();
        sim = new Simulation(paneVirusSimulation, POPULATION);
    }

    @FXML
    void start(ActionEvent event) {

    }

    @FXML
    void stop(ActionEvent event) {

    }

}
