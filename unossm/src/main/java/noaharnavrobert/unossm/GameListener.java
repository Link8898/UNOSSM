package noaharnavrobert.unossm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameListener extends Thread{

    String serveraddress;

    public GameListener(String server){
        serveraddress = server;
    }

    public void run() {
        try {
            Socket socket = new Socket(serveraddress, 1234);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("NoahSucks");

            String response = in.readLine();
            System.out.println("Server says: "+response);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetHand(int id) {
        ArrayList<String> hand = new ArrayList<String>();

        // client requests its hand
        // server return logic.getHand()

        return hand;
    }

}
