package noaharnavrobert.unossm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LCController {

    // label displaying current players
    @FXML
    private Label playerlist;
    // label displaying the current playercount
    @FXML
    private Label playercount;
    // Button that when pressed starts the game
    @FXML
    private Button startgame;
    @FXML
    private Label ip;

    private String name;
    private String localip;
    private String serveraddress;
    private ArrayList<String> players;



    public void initialize(){
        byte[] buf = new byte[256];
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(5678);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        /*
        boolean waiting = true;
        while(waiting) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                String received = new String(packet.getData());
                received = received.replace("\0", "");

                if(received.split(" ")[0].equals("joined")){
                    serveraddress = String.valueOf(packet.getAddress());
                    name = received.split(" ")[1];

                } else if (received.split(" ").equals("players")) {

                    String playersstring = received.split(" ")[1];

                    String[] strSplit = playersstring.split("");

                    players = new ArrayList<>(Arrays.asList(strSplit));
                    playersUpdate(players);

                }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        }

         */

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                String received = new String(packet.getData());
                received = received.replace("\0", "");

                if(received.split(" ")[0].equals("joined")){
                    serveraddress = String.valueOf(packet.getAddress());
                    name = received.split(" ")[1];
                    localip = received.split(" ")[2];
                    ip.setText(serveraddress);
                    ip.setTextFill(Color.GREEN);
                } else if (received.split(" ").equals("players")) {

                    String playersstring = received.split(" ")[1];

                    String[] strSplit = playersstring.split("");

                    players = new ArrayList<>(Arrays.asList(strSplit));
                    playersUpdate(players);
                }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        socket.close();
    }


    // used to update playercount & players
    @FXML
    protected void playersUpdate(ArrayList<String> players){
        if(players.size() < 2) {
            playercount.setText("2 Players Required to start game ("+players.size()+")");
        } else {
            playercount.setTextFill(Color.GREEN);
            playercount.setText("The game can now be started by host ("+players.size()+")");
        }
        playerlist.setText(players.toString());
    }

    @FXML
    protected void leaveServer(){
        try {
            String msg = "leave "+name+" "+localip;

            byte[] buf = msg.getBytes();

            DatagramSocket socket = new DatagramSocket(5678);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(serveraddress), 1234);

            socket.send(packet);

            try {
            FXMLLoader loadingscreen = new FXMLLoader(Application.class.getResource("loadingscreen.fxml"));
            Scene scene = new Scene(loadingscreen.load(), 500, 500);
            Stage stage = (Stage) ip.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



