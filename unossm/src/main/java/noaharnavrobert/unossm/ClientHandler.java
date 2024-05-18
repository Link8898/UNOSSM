package noaharnavrobert.unossm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler extends Thread {

    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Logic logic;
    private ArrayList<String> players;
    private ArrayList<String> playerips;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Logic logic, ArrayList<String> players, ArrayList<String> playerips) {
        this.s = s;
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
                String[] receivedArray = received.split(" ");
                String userIP = receivedArray[1];
                int id = playerips.indexOf(userIP);
                if (receivedArray[0].equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // write on output stream based on the
                // answer from the client
                switch (receivedArray[0]) {

                    case "getHand":

                        toreturn = logic.GetHand(id).toString();
                        dos.writeUTF(toreturn);
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

            try {
                // closing resources
                this.dis.close();
                this.dos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}