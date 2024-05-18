package noaharnavrobert.unossm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController {
    // Identity
    String ip;
    // Player's Hand of Cards
    private ArrayList<String> hand;
    @FXML
    private AnchorPane container;
    @FXML Button playedcard;
    private ArrayList<Button> cards = new ArrayList<Button>();
    // Styling
    private final int margin = 60;
    private GameListener listener;
    private Media sound;

    private void RenderHand() {
        container.getChildren().removeIf(Button.class::isInstance); // Clear the previous hand
        cards = new ArrayList<Button>();
        System.out.println("Requesting hand from server");
        ArrayList<String> cardData = listener.GetHand(ip); // FETCHING DATA
        System.out.println("Received hand from server");
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
                    //logic.PlayCard(id, cards.indexOf(card));
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
        // Render the most recently played card
        String currentCard = listener.GetCurrent();
        if (currentCard.equals("NONE")) {
            String style = "-fx-background-color: " + "gray" + "; -fx-text-fill: black; -fx-font-size: 200%;";
            playedcard.setText("");
            playedcard.setStyle(style);
        }
        else {
            String color = "red";
            if (currentCard.charAt(0) == 'g') { color = "green"; }
            else if (currentCard.charAt(0) == 'b') { color = "lightblue"; }
            else if (currentCard.charAt(0) == 'y') { color = "yellow"; }
            String style = "-fx-background-color: " + color + "; -fx-text-fill: black; -fx-font-size: 300%;";
            playedcard.setText(currentCard.substring(1));
            playedcard.setStyle(style);
        }
    }

    @FXML
    protected void onDrawButtonClick() {
        listener.DrawCard(ip);
        RenderHand();
    }

    public void Connect(String clientIP, String serverIP) {
        Runnable task = () -> {
            Platform.runLater(() -> {
                listener = new GameListener(serverIP);
                listener.start();
                ip = clientIP;
                RenderHand();
                sound = new Media(new File("src/main/resources/sounds/game.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            });
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}