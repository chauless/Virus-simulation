package cvut.fel.cz.pjv.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SimApp extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/simPage.fxml"));

        Scene scene = new Scene(root);
        stage.setWidth(800);
        stage.setHeight(600);
//        stage.setResizable(false);
        primaryStage.setTitle("Virus Simulation App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
