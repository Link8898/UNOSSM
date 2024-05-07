package noaharnavrobert.unossm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Controller {
    Logic logic = new Logic(1); // Temporary placement for the initialization of the logic

    // Identity
    private final int id = 0;

    // Player's Hand of Cards
    private ArrayList<String> hand;
    @FXML
    private AnchorPane container;
    private ArrayList<Button> cards = new ArrayList<Button>();

    // Styling
    private final int margin = 60;

    @FXML
    protected void onHelloButtonClick() {
        RenderHand();
    }

    private void RenderHand() { // Now fix positioning of the cards (space out based on cards.length or something)
        container.getChildren().clear(); // Clear previous hand
        cards = new ArrayList<Button>();
        ArrayList<String> cardData = logic.GetHand(id); // FETCHING DATA
        for (int index = 0; index < cardData.size(); index++) {
            // Create and style the card
            Button card = new Button();
            card.setText(cardData.get(index).substring(1));
            String color = "red";
            if (cardData.get(index).charAt(0) == 'g') { color = "green"; }
            else if (cardData.get(index).charAt(0) == 'b') { color = "lightblue"; }
            else if (cardData.get(index).charAt(0) == 'y') { color = "yellow"; }
            String style = "-fx-background-color: " + color + "; -fx-text-fill: black; -fx-font-size: 200%;";
            card.setStyle(style);
            card.setMinWidth(50);
            card.setMinHeight(80);
            int origin = (int)(container.getWidth() / 2 - card.getMinWidth() / 2);
            card.setLayoutX((index - (double) cardData.size() / 2) * margin + origin + (double) margin / 2);
            card.setLayoutY((int)(container.getHeight() / 2) - card.getMinHeight() / 2);

            // Add event listener to the card
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    logic.PlayCard(id, cards.indexOf(card));
                    RenderHand();
                }
            };
            card.setOnAction(event);

            // Append the card to the view
            cards.add(card);
        }
        for (Button card: cards) { // Append new hand
            container.getChildren().add(card);
        }
    }

    public void initialize() {
        // RenderHand();
    }
}