package noaharnavrobert.unossm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
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
    private byte[] buf = new byte[256];

    public Server() throws SocketException {
        socket = new DatagramSocket(1234);
        players = new ArrayList();
    }

    public void lobby() {

        waiting = true;
        while(waiting){
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0);

            if(received.equals("end")) {
                waiting = false;
                run();
            }


        }


    }

    public void run() {
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
}