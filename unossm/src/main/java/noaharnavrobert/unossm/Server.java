package noaharnavrobert.unossm;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Server extends Thread {

    // boolean for when the game starts
    private boolean running;
    // arraylist that contains all of the players
    private ArrayList<String> players;
    private ArrayList<String> playerips;
    private Logic logic; // Game logic
    public void run() {

        // server variables
        // port 1234
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(1234);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        players = new ArrayList();
        playerips = new ArrayList<>();

        // boolean for when the lobby is running
        boolean waiting = true;
        byte[] buf = new byte[256];

        while (waiting) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                String received = new String(data);
                System.out.println(received);
                if(received.split(" ")[0].equals("join")){
                    newPLayer(received);
                } else if (received.split(" ")[0].equals("start")) {

                    try {
                        String msg = "start "+playerips.get(0);
                        byte[] buffer = msg.getBytes();

                        for(String ip : playerips) {
                            DatagramPacket startPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), 5678);
                            socket.send(startPacket);
                        }

                    } catch (SocketException | UnknownHostException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    socket.close();
                    waiting = false;
                    running = true;
                    game();
                } else if (received.split(" ")[0].equals("leave")) {

                    players.remove(received.split(" ")[1]);
                    playerips.remove(received.split(" ")[2]);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendPlayers(){
        try {
            DatagramSocket socket = new DatagramSocket();
            String msg = "players "+players.toString();
            System.out.println(msg);
            System.out.println(players);

            byte[] buf = msg.getBytes();

            for(String ip : playerips) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), 5678);
                socket.send(packet);
            }

        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newPLayer(String received){
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        // updates arraylists
        players.add(received.split(" ")[1]);
        playerips.add(received.split(" ")[2]);
        String msg = "joined "+players.get(players.size()-1) +" "+ playerips.get(playerips.size()-1);
        byte[] buffer = msg.getBytes();

        // sends packet to clients to confirm joining
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(playerips.get(playerips.size() - 1)), 5678);
            socket.send(packet);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sendPlayers();

    }

    public void game(){
        logic = new Logic(players.size());
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("TCP Listening on port 1234");
            while(running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");


                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String msg = in.readLine();
                String[] split_msg = msg.split(" ");
                if(split_msg[0].equals("gethand")){
                    int index = Integer.parseInt(split_msg[1]);
                    out.println(logic.GetHand(index).toString());
                } else if(split_msg[0].equals("getcurrent")){
                    out.println(logic.CurrentCard());
                } 

                //out.println("True, Noah really does suck!!!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}