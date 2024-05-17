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

    public void run() {

    }

    public ArrayList<String> GetHand(String ip) {
        ArrayList<String> hand = new ArrayList<String>();
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        out.println("gethand "+ip);

        try {
            String res = in.readLine();
            hand = new ArrayList<String>(Arrays.asList(res.split(", ")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hand;
    }

    public String GetCurrent(String ip) {
        String card = "";

        out.println("getcurrent "+ip);

        try {
            card = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return card;
    }

}
