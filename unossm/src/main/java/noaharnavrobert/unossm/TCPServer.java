package noaharnavrobert.unossm;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class TCPServer {

    private Logic logic;
    private ArrayList<String> players;
    private ArrayList<String> playerips;

    public TCPServer(Logic logic, ArrayList<String> players, ArrayList<String> playerips){
        this.logic = logic;
        this.players = players;
        this.playerips = playerips;

    }

    public void run()
    {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int counter = 0;
        while (counter < players.size())
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos, logic, players, playerips);
                // Invoking the start() method
                t.start();
                System.err.println("Created new thread for this client");
                System.err.println(counter);
                counter++;

            }
            catch (Exception e){
                try {
                    s.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                e.printStackTrace();
            }
        }
    }
}