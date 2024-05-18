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
                System.err.println("Current iteration through creating client: "+counter);
                counter++;


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    class ClientHandler extends Thread {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Logic logic;
    private ArrayList<String> players;
    private ArrayList<String> playerips;
    public Scanner scanner = new Scanner(System.in);


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Logic logic, ArrayList<String> players, ArrayList<String> playerips) {
        this.socket = s;
        System.err.println(socket.isClosed());
        scanner.nextLine();
        this.dis = dis;
        this.dos = dos;
        this.logic = logic;
        this.players = players;
        this.playerips = playerips;
    }

    @Override
    public void run() {

        String received;
        String toreturn;
        while (true) {
            try {

                // receive the answer from client
                System.out.println(socket.isClosed());
                received = dis.readUTF();
                socket.isClosed();
                System.out.println(socket.isClosed());
                System.err.println("Received: " + received);
                String[] receivedArray = received.split(" ");
                String userIP = receivedArray[1];
                int id = playerips.indexOf(userIP);

                // write on output stream based on the
                // answer from the client
                switch (receivedArray[0]) {

                    case "getHand":

                        toreturn = logic.GetHand(id).toString();
                        System.out.println(socket.isClosed());
                        dos.writeUTF(toreturn);
                        scanner.nextLine();
                        System.out.println("sent hand");
                        break;

                    case "getCurrent":
                        toreturn = logic.CurrentCard();
                        dos.writeUTF(toreturn);
                        System.out.println("sent current card");
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



