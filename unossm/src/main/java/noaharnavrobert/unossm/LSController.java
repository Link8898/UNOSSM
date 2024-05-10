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
    private String name;

    private byte[] buf;


    public LSController(String name){
        this.name = name;
    }

    public void initialize(){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        players = new ArrayList<String>();
        players.add(name);
        try {
            Server server = new Server();
        } catch (SocketException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }


    public void sendPacket(){
        String msg = "hi";
        buf = msg.getBytes();
        DatagramPacket packet
          = new DatagramPacket(buf, buf.length, address, 1234);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
