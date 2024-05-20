package noaharnavrobert.unossm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LSController {

    // label displaying current players
    @FXML
    private VBox playerList;
    // label displaying the current playercount
    @FXML
    private Label playercount;
    // Button that when pressed starts the game
    @FXML
    private Button startgame;
    @FXML
    private Label ip;

    private String ipaddress;
    private String name;


    public void initialize(){
        getLocalIP();
    }


    public void startupPing(String name, LSController controller){
        this.name = name;

        LSListener listener = new LSListener(controller);
        listener.start();


            try {

                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName("localhost");

                String msg = "join " + name + " " + ipaddress;

                byte[] buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1234);
                socket.send(packet);
                socket.close();

            } catch (SocketException | UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    @FXML
    protected void getLocalIP() {
        try{
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 6969);
            String ipaddress = socket.getLocalAddress().getHostAddress();
            ip.setText(ipaddress);
            ip.setTextFill(Color.GREEN);
            this.ipaddress = ipaddress;
        } catch (UnknownHostException e) {
            ip.setText("UNKNOWN");
            startgame.setDisable(true);
            ip.setTextFill(Color.RED);
            startgame.setCancelButton(true);
            System.out.println(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    // when startgame is pressed
    @FXML
    protected void startGame() {
        try { // Tell clients the game has started and establish a TCP connection
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            String msg = "start "+ ipaddress;

            byte[] buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1234);
            socket.send(packet);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            FXMLLoader game = new FXMLLoader(Application.class.getResource("view.fxml"));
            Scene scene = new Scene(game.load(), 1000, 750);
            Stage stage = (Stage)(ip.getScene().getWindow());
            stage.setScene(scene);
            stage.show();

            GameController gameController = game.getController();
            gameController.Connect(ipaddress, ipaddress);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    // used to update playercount & players
    @FXML
    protected void playersUpdate(ArrayList<String> players){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = "2 Players Required to start game (";
                if (players.size() >= 2) {
                    playercount.setTextFill(Color.GREEN);
                    message = "The game can now be started by host (";
                }
                message += players.size() + ")";

                playerList.getChildren().clear();
                for( String player : players ){
                    Label playerLabel = new Label (player.substring(1, player.length()));
                    playerList.getChildren().add(playerLabel);
                }

                playercount.setText(message);
            }
        });
    }
}
