package noaharnavrobert.unossm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    // textfield where you enter your name
    @FXML
    private TextField namebox;
    // name
    private String name;
    private String ipaddress;




    public void initialize() {
        getLocalIP();
    }

    @FXML
    protected void getLocalIP() {
        try{
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 6969);
            String ip = socket.getLocalAddress().getHostAddress();
            ipaddress = ip;
            ipbox.setText(ip);
            ipbox.setTextFill(Color.GREEN);
            System.out.println("socket closed");
        } catch (UnknownHostException e) {
            ipbox.setText("UNKNOWN");
            startgame.setDisable(true);
            ipbox.setTextFill(Color.RED);
            startgame.setCancelButton(true);
            System.out.println(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    protected void onCreateGame(){

        name = namebox.getText();

        // if the name box is not empty
        if(!(name.isEmpty() || name.isBlank())) {


            // start the server - run the "run" method in the server class
            Server server = new Server();
            server.start();

            Stage stage = (Stage) (startgame.getScene().getWindow());

            // send a packet to the server notifying it of the client joining
            try {
                DatagramSocket socket = new DatagramSocket(5678);
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



            try {
                FXMLLoader lobbyserver = new FXMLLoader(Application.class.getResource("lobbyserver.fxml"));
                System.out.println("1");
                Parent root = lobbyserver.load();
                System.out.println("2");
                stage.setScene(new Scene(root));
                System.out.println("3");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            namebox.setText("Enter name here");
        }
    }

    @FXML
    protected void onJoinGame(){

        name = namebox.getText();

        if(!(name.isEmpty() || name.isBlank())){
            Stage stage = (Stage)(startgame.getScene().getWindow());

            try {
                DatagramSocket socket = new DatagramSocket();
                String host = ipbox.getText();
                InetAddress address = InetAddress.getByName(host);

                String msg = "join " + name+" "+ipaddress;

                byte[] buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1234);
                socket.send(packet);

            } catch (SocketException | UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        try {
            FXMLLoader lobbyserver = new FXMLLoader(Application.class.getResource("lobbyclient.fxml"));
            Parent root = lobbyserver.load();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }



        }
}