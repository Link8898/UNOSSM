package noaharnavrobert.unossm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Controller {
    Logic logic = new Logic(2); // Temporary placement for the initialization of the logic
    private ArrayList<String> hand;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        logic.PlayCard(0, 3);
    }

    
}