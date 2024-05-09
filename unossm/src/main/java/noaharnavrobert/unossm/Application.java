package noaharnavrobert.unossm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader introloader = new FXMLLoader(Application.class.getResource("loadingscreen.fxml"));
        FXMLLoader gameloader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(introloader.load(), 500, 500);
        stage.setTitle("UNOSSM");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}