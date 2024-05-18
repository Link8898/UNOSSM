package noaharnavrobert.unossm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class GameListener extends Thread {
    String serverIP;
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public GameListener(String server) {
        serverIP = server;
        System.err.println(server);
        try {
            socket = new Socket(serverIP, 1234);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
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

        out.println("getHand " + ip);

        try {
            String res = in.readLine();
            res = res.substring(1, res.length() - 1);
            hand = new ArrayList<String>(Arrays.asList(res.split(", ")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hand;
    }

    public String GetCurrent() {
        String card = "";
        out.println("getCurrent");

        try {
            card = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return card;
    }

    public void DrawCard(String ip) {
        out.println("drawCard "+ip);

    }

}
