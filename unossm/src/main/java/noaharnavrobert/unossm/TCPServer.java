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

    public void run() {
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(1234);
            System.err.println("listening on port 1234");
            int counter = 0;
            while (counter < players.size()) {
                Socket socket = null;

                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                // create a new thread object
                Thread t = new ClientHandler(socket, dis, dos, logic, players, playerips);
                // Invoking the start() method
                t.start();

                System.err.println("Created new thread for this client");
                counter++;
            }
        } catch(IOException e){
            throw new RuntimeException(e);
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
                    received = dis.readUTF();
                    System.err.println("Received: " + received);
                    String[] receivedArray = received.split(" ");
                    String userIP = receivedArray[1];
                    int id = playerips.indexOf(userIP);

                    // write on output stream based on the
                    // answer from the client
                    switch (receivedArray[0]) {

                        case "getHand":

                            toreturn = logic.GetHand(id).toString();
                            dos.writeUTF(toreturn);
                            break;

                        case "getCurrent":
                            toreturn = logic.CurrentCard();
                            dos.writeUTF(toreturn);
                            break;

                        case "playCard":
                            int cardIndex = Integer.parseInt(receivedArray[2]);
                            String card = logic.GetHand(id).get(cardIndex);
                            logic.PlayCard(id, cardIndex);

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
}



