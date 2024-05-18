package noaharnavrobert.unossm;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String args[]) throws IOException 
    {
        // create a server socket on port number 9090
        ServerSocket serverSocket = new ServerSocket(6969);
        System.out.println("Server searching...");

        // Accept incoming client connection
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected from ip "+clientSocket.getInetAddress());

        // Setup input and output streams for communication with the client
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Read message from client
        String message = in.readLine();
        System.out.println("Client says: " + message);

        // Send response to the client
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.next();
        out.println(msg);

        // Close the client socket
        // Close the server socket
    }
}