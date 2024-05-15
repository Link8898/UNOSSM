package noaharnavrobert.unossm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LSListener extends Thread {

    private String serveraddress;
    private String name;
    private ArrayList<String> players;

    public void run(){
        boolean waiting = true;

        try {
            DatagramSocket socket = new DatagramSocket(5678);
            byte[] buf = new byte[256];
            while(waiting) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                String received = new String( data);
                System.out.println("received " + received);

                if (received.split(" ")[0].equals("joined")) {
                    serveraddress = String.valueOf(packet.getAddress());
                    name = received.split(" ")[1];

                } else if (received.split(" ")[0].equals("players")) {
                    String playersstring = received.split(" ")[1];

                    String[] strSplit = playersstring.split(",");
                    players = new ArrayList<>(Arrays.asList(strSplit));
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("lobbyserver.fxml"));

                    Parent p = loader.load();
                    LSController controller = loader.getController();
                    controller.playersUpdate(players);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
