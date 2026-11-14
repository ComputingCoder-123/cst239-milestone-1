package edu.gcu.cst239.kabwe.elijah.milestone5.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Command-line TCP client for interacting with ProductStoreServer.
 */
public class ProductStoreClient {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5050;
    private final String host;
    private final int port;

    /**
     * Constructs a ProductStoreClient targeting the specified host and port.
     *
     * @param host the server hostname or IP address
     * @param port the server TCP port
     */
    public ProductStoreClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connects to the server, reads commands from standard input, and prints
     * JSON responses until the user enters a period (.).
     *
     * @throws IOException if a socket or I/O error occurs
     */
    public void run() throws IOException {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to ProductStoreServer at " + host + ":" + port);
            System.out.println("Enter commands (ALL, GET <id>, FIND <text>, DELETE <id>, SORT NAME, SORT PRICE) or '.' to quit:");

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                out.println(input);
                if (".".equals(input.trim())) {
                    break;
                }
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server closed connection.");
                    break;
                }
                System.out.println(response);
            }
        }
    }

    /**
     * Entry point for the ProductStoreClient executable JAR.
     *
     * @param args optional host and port arguments
     * @throws IOException if the client fails to connect
     */
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        int port = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_PORT;
        new ProductStoreClient(host, port).run();
    }
}
