package noaharnavrobert.unossm;

import java.io.*;
import java.lang.reflect.Array;
import java.text.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Server class
public class TCPServer {

    private Logic logic;
    private ArrayList<String> players;
    private ArrayList<String> playerips;
    private ArrayList<DataOutputStream> sockets;

    public TCPServer(Logic logic, ArrayList<String> players, ArrayList<String> playerips){
        this.logic = logic;
        this.players = players;
        this.playerips = playerips;
        sockets = new ArrayList<>();

    }

    public void run() {
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(1234);
            int counter = 0;
            while (counter < players.size()) {
                Socket socket = null;

                // socket object to receive incoming client requests
                socket = serverSocket.accept();


                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                // create a new thread object
                sockets.add(dos);
                Thread t = new ClientHandler(socket, dis, dos, logic, players, playerips);
                // Invoking the start() method
                t.start();

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
        private Lock lock;
        private ArrayList<String> playerips;


        // Constructor
        public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Logic logic, ArrayList<String> players, ArrayList<String> playerips) {
            this.socket = s;
            this.dis = dis;
            this.dos = dos;
            this.logic = logic;
            this.players = players;
            this.playerips = playerips;
            lock = new ReentrantLock();
        }

        @Override
        public void run() {

            String received;
            String toreturn;
            boolean running = true;
            while (running) {
                try {

                    // receive the answer from client
                    received = dis.readUTF();
                    String[] receivedArray = received.split(" ");
                    String userIP = receivedArray[1];
                    int id = playerips.indexOf(userIP);

                    // write on output stream based on the
                    // answer from the client
                    System.err.println(receivedArray[0]);
                    toreturn = logic.GetHand(id) + " " + logic.CurrentCard();
                    switch (receivedArray[0]) {

                        case "getHand":
                            dos.writeUTF(toreturn);
                            break;

                        case "playCard":
                            int cardIndex = Integer.parseInt(receivedArray[2]);
                            logic.PlayCard(id, cardIndex);

                            lock.lock();
                            for(DataOutputStream socket : sockets) {
                                socket.writeUTF(toreturn);
                            }
                            lock.unlock();


                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    running = false;
                }

            }
        }
    }
}



