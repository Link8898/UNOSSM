package noaharnavrobert.unossm;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class LCListener extends Thread {

    String serveraddress;
    String name;
    ArrayList<String> players;

    public void run(){
        boolean waiting = true;

        DatagramSocket socket;
        try {
            socket = new DatagramSocket(5678);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }


        byte[] buf = new byte[256];
        while(waiting) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {

                socket.receive(packet);
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                String received = new String(data);
                System.out.println(received);

                if(received.split(" ")[0].equals("joined")){
                    serveraddress = String.valueOf(packet.getAddress());
                    name = received.split(" ")[1];

                } else if (received.split(" ").equals("players")) {

                    String playersstring = received.split(" ")[1];
                    String[] strSplit = playersstring.split("");
                    players = new ArrayList<>(Arrays.asList(strSplit));
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("lobbyclient.fxml"));
                    LCController controller = loader.getController();
                    controller.playersUpdate(players);

                }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        }
    }


}
