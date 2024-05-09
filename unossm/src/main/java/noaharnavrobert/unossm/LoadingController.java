package noaharnavrobert.unossm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadingController {


    // box where users local ip is displayed
    @FXML
    private Label ipbox;
    // button to create game on local machine
    @FXML
    private Button startgame;
    // button to join a specific ip's game
    @FXML
    private Button join;
    // textfield where you enter someone's local ip address
    @FXML
    private TextField inputbox;




    public void initialize() {
        getLocalIP();
    }

    @FXML
    protected void getLocalIP() {
        try{
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 6969);
            String ip = socket.getLocalAddress().getHostAddress();
            ipbox.setText(ip);
            ipbox.setTextFill(Color.GREEN);
        } catch (UnknownHostException e) {
            ipbox.setText("UNKNOWN");
            ipbox.setTextFill(Color.RED);
            startgame.setCancelButton(true);
            System.out.println(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    protected void onCreateGame(){

    }

    @FXML
    protected void onJoinGame(){

    }

}