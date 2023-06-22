package cvut.fel.cz.pjv.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class represents a simulation application
 * and loads the fxml file.
 */
public class SimApp extends Application {

    public static Stage stage;

    /**
     * This method starts the application and loads the fxml file
     * @param primaryStage stage
     * @throws IOException exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/simPage.fxml"));

        Scene scene = new Scene(root);
        stage.setWidth(1000);
        stage.setHeight(600);
//        stage.setResizable(false);
        primaryStage.setTitle("Virus Simulation App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method launches the application
     * @param args arguments
     */
    public static void main(String[] args){
        launch(args);
    }
}
