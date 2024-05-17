package noaharnavrobert.unossm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class GameListener extends Thread{

    String serveraddress;
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public GameListener(String server){
        serveraddress = server;
    }

    public void run() {
        try {
            socket = new Socket(serveraddress, 1234);

             out = new PrintWriter(socket.getOutputStream(), true);

             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //out.println("gethand");

            //String response = in.readLine();
            //System.out.println("Server says: "+response);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> GetHand(int id) {
        ArrayList<String> hand = new ArrayList<String>();

        out.println("gethand "+id);

        try {
            String res = in.readLine();
            hand = new ArrayList<String>(Arrays.asList(res.split(", ")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hand;
    }

    public void GetCurrent() {
        
    }
}
