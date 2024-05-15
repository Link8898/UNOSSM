package noaharnavrobert.unossm;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {

    // boolean for when the game starts
    private boolean running;
    // arraylist that contains all of the players
    private ArrayList<String> players;
    private ArrayList<String> playerips;
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
                System.out.println("2");
                String received = new String(packet.getData());
                received = received.replace("\0", "");
                System.out.println(received);
                if(received.split(" ")[0].equals("join")){

                    newPLayer(received);

                } else if (received.split(" ")[0].equals("start")) {
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
        System.out.println("5");
        try {
            DatagramSocket socket = new DatagramSocket();
            String msg = "players "+players.toString();

            byte[] buf = msg.getBytes();

            for(String ip : playerips) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), 5678);
                socket.send(packet);
                System.out.println("6");
            }

        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newPLayer(String received){
        System.out.println("3");
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
            System.out.println("4");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sendPlayers();

    }

    public void game(){
        Logic logic = new Logic(players.size());
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(1234);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        int counter = 0;
        for(String ip : playerips) {

            String msg = "game "+players.get(counter)+" "+playerips.get(counter);
            byte[] buf = msg.getBytes();

            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), 1234);
                socket.send(packet);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }

    }
}