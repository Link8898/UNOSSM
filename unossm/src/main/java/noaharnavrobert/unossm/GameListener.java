package noaharnavrobert.unossm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class GameListener extends Thread {
    String serverIP;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public GameListener(String server) {
        serverIP = server;
        try {
            socket = new Socket(serverIP, 1234);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
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
            dos.writeUTF("getHand " + ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String res = dis.readUTF();
            res = res.substring(1, res.length() - 1);
            hand = new ArrayList<String>(Arrays.asList(res.split(", ")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hand;
    }

    public String GetCurrent(String ip) {
        String card = "";
        try {
            System.out.println("Requesting current card");
            dos.writeUTF("getCurrent " + ip);
            System.out.println("Received current card");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            card = dis.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return card;
    }

    public void DrawCard(String ip) {
        try {
            dos.writeUTF("drawCard "+ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
