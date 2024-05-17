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

public class LCController {

    // label displaying current players
    @FXML
    private VBox playerList;
    // label displaying the current playercount
    @FXML
    private Label playercount;
    // Button that when pressed starts the game
    @FXML
    private Label ip;

    private String ipaddress;
    private String serveraddress;
    private String name;


    public void initialize(){
        getLocalIP();
    }


    public void startupPing(String name, LCController controller, String host){
        this.name = name;
        serveraddress = host;

        LCListener listener = new LCListener(controller);
        listener.start();


            try {
                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName(host);

                String msg = "join " + name + " " + ipaddress;
                System.out.println(msg);

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
            ip.setTextFill(Color.RED);
            System.out.println(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    protected void startGame(){
        System.out.println("I was called to start the game");
        try {
            FXMLLoader game = new FXMLLoader(Application.class.getResource("view.fxml"));
            Scene scene = new Scene(game.load(), 500, 500);
            System.out.println("Now stage");
            Runnable task = () -> {
                Platform.runLater(() -> {
                    Stage stage = (Stage)(ip.getScene().getWindow());
                    stage.setScene(scene);
                    stage.show();
                });
            };
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Game should have started");

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