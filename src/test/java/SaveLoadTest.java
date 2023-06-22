import cvut.fel.cz.pjv.model.Person;
import cvut.fel.cz.pjv.model.Position;
import cvut.fel.cz.pjv.model.Simulation;
import cvut.fel.cz.pjv.model.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveLoadTest {
    private Pane paneVirusSimulation;
    private Slider sliderPopulationSize;
    private Slider sliderRecoverySpeed;
    private Slider sliderMovingSpeed;

    @BeforeEach
    public void setup() {
        new JFXPanel();

        paneVirusSimulation = new Pane();
        sliderPopulationSize = new Slider();
        sliderRecoverySpeed = new Slider();
        sliderMovingSpeed = new Slider();
    }

    @Test
    public void testSaveLoadConfig() {
        String tempFileName = "tempConfig.json";

        Simulation sim = new Simulation(paneVirusSimulation, 3);

        sim.saveConfig(sim.getPeople(), tempFileName, sliderPopulationSize, sliderRecoverySpeed, sliderMovingSpeed);

        File tempFile = new File(tempFileName);
        assertTrue(tempFile.exists());

        sim.loadConfig(paneVirusSimulation, tempFileName, sliderPopulationSize, sliderRecoverySpeed, sliderMovingSpeed);

        List<Person> loadedPeople = sim.getPeople();
        assertEquals(4, loadedPeople.size());

        for (Person person : loadedPeople) {
            Position position = person.getPosition();
            double x = position.getX();
            double y = position.getY();
            State state = person.getState();

            assertEquals(x, person.getPosition().getX());
            assertEquals(y, person.getPosition().getY());
            assertEquals(state, person.getState());
        }

        tempFile.delete();
    }
}
