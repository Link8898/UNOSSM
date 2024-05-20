package noaharnavrobert.unossm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameListener extends Thread {
    Socket socket;
    DataInputStream dis;
    GameController controller;

    public GameListener(Socket socket, GameController controller) {
        try {
            this.socket = socket;
            dis = new DataInputStream(socket.getInputStream());
            this.controller = controller;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        boolean reading = true;
        while (reading) {
            try {
                String res = dis.readUTF();
                System.out.println(res);
                String nameData = res.substring(0, res.indexOf("[") - 1);
                String handData = res.substring(res.indexOf("[") + 1, res.length() - 4);
                String current = res.substring(res.length() - 2);
                ArrayList<String> hand = new ArrayList<String>(Arrays.asList(handData.split(", ")));
                controller.RenderLater(nameData, hand, current);

            } catch (IOException e) {
                reading = false;
                throw new RuntimeException(e);
            }
        }
    }
}
