package noaharnavrobert.unossm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class LSController {

    private ArrayList<String> players;

    // label displaying current players
    @FXML
    private Label playerlist;
    // label displaying the current playercount
    @FXML
    private Label playercount;
    // Button that when pressed starts the game
    @FXML
    private Button startgame;

    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;




    public void initialize(){

    }



    // when startgame is pressed
    @FXML
    protected void startGame(){

    }

    // used to update playercount & players
    @FXML
    protected void playersUpdate(){

    }


}
