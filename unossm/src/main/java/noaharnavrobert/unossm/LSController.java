package noaharnavrobert.unossm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class LSController {

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

    private String ipaddress;

    private byte[] buf;




    public void initialize(){ getLocalIP();}

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
    protected void startGame(){

        try {
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

    }

    // used to update playercount & players
    @FXML
    protected void playersUpdate(){

        //playercount.setText("2 Players Required to start game ("+players.)

    }


}
