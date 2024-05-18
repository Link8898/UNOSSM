package noaharnavrobert.unossm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String serverIP = scan.nextLine();

        Socket socket;
        PrintWriter out;
        BufferedReader in;

        try {
            socket = new Socket(serverIP, 6969);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true){
            try {
                String req = scan.nextLine();
                out.println(req);

                String res = in.readLine();
                System.out.println("Response: " + res);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
