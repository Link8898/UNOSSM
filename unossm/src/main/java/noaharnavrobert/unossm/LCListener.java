package noaharnavrobert.unossm;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LCListener extends Thread {

    private String serveraddress;
    private String name;
    private ArrayList<String> players;
    private LCController controller;

    public LCListener(LCController controller){

        this.controller = controller;
    }

    public void run(){
        boolean waiting = true;

        try {
            DatagramSocket socket = new DatagramSocket(5678);
            byte[] buf = new byte[256];
            while(waiting) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                System.err.println("received");
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                String received = new String(data);
                System.out.println("received " + received);

                if (received.split(" ")[0].equals("joined")) {
                    serveraddress = String.valueOf(packet.getAddress());
                    name = received.split(" ")[1];

                } else if (received.split(" ")[0].equals("players")) {
                    String playerNames = received.substring("players".length() + 1, received.length() - 1);
                    String[] nameArray = playerNames.split(",");
                    players = new ArrayList<>(Arrays.asList(nameArray));
                    controller.playersUpdate(players);
                    System.err.println("called playersUpdate");

                } else if (received.split(" ")[0].equals("start")) {
                    System.err.println("received start");
                    controller.startGame();
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
