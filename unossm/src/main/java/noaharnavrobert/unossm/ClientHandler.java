package noaharnavrobert.unossm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {

    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
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

                        toreturn = "hand";
                        dos.writeUTF(toreturn);
                        System.out.println("sent hand");
                        break;

                    case "getCurrent":
                        toreturn = "g4";
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