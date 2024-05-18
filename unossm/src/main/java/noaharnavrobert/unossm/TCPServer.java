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
    private Scanner scanner = new Scanner(System.in);

    public TCPServer(Logic logic, ArrayList<String> players, ArrayList<String> playerips){
        this.logic = logic;
        this.players = players;
        this.playerips = playerips;

    }

    public void run()
    {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int counter = 0;
        while (counter < players.size())
        {
            Socket socket = null;

            try
            {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);
                scanner.nextLine();

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                scanner.nextLine();

                System.out.println("Assigning new thread for this client");
                scanner.nextLine();

                System.out.println(socket.isClosed());
                scanner.nextLine();




                // create a new thread object
                Thread t = new ClientHandler(socket, dis, dos, logic, players, playerips);
                scanner.nextLine();
                // Invoking the start() method
                t.start();
                scanner.nextLine();
                System.err.println("Created new thread for this client");
                System.err.println(counter);
                counter++;


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

