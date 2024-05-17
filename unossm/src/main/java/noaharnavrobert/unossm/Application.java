package noaharnavrobert.unossm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader introloader = new FXMLLoader(Application.class.getResource("loadingscreen.fxml"));
        Scene scene = new Scene(introloader.load(), 500, 500);
        stage.setTitle("UNOSSM");
        stage.setScene(scene);
        stage.show();

        // Play background music
        //String musicFile = "Lobby.mp3";
        //Media sound = new Media(new File(musicFile).toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}