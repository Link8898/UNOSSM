package noaharnavrobert.unossm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameListener extends Thread{

    String serveraddress;

    public GameListener(String server){
        serveraddress = server;
    }

    public void run() {
        try {
            Socket socket = new Socket(serveraddress, 1234);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String received = reader.readLine();

            System.out.println(received);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void GetHand(int id) {

    }

}
