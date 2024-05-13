package noaharnavrobert.unossm;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server extends Thread {

    // server variables
    // port 1234
    private DatagramSocket socket;
    // boolean for when the game starts
    private boolean running;
    // boolean for when the lobby is running
    private boolean waiting;
    // arraylist that contains all of the players
    private ArrayList<String> players;
    private ArrayList<String> playerips;
    public void run() {


        System.out.println("Server lobby started");

        try {
            socket = new DatagramSocket(1234);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        players = new ArrayList();
        playerips = new ArrayList<>();

        waiting = true;
        byte[] buf = new byte[256];

        while (waiting) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                String received = new String(packet.getData());
                received = received.replace("\0", "");
                players.add(received.split(" ")[1]);
                playerips.add(received.split(" ")[2]);

                // send packet to server with updated players arraylist

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        /*

        running = true;

        while (running) {
            DatagramPacket packet
              = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received
              = new String(packet.getData(), 0, packet.getLength());

            if (received.equals("end")) {
                running = false;
                continue;
            }
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
        socket.close();
    }

         */
    }


    public void sendPlayers(){

        try {
                DatagramSocket socket = new DatagramSocket();

                InetAddress address = InetAddress.getByName(host);

                String msg = players.toString();

                byte[] buf = msg.getBytes();

                for(String ip : playerips) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByAddress(ip.getBytes()), 1234);
                    socket.send(packet);
                }

            } catch (SocketException | UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

}