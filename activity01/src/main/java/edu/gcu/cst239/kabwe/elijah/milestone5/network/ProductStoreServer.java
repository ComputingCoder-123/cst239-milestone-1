package edu.gcu.cst239.kabwe.elijah.milestone5.network;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.InventoryManager;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.InventoryService;
import edu.gcu.cst239.kabwe.elijah.milestone5.service.ProductCommandService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Single-client synchronous TCP server that exposes Product Store inventory
 * commands and returns JSON responses.
 */
public class ProductStoreServer {
    private static final int DEFAULT_PORT = 5050;
    private final int port;
    private final ProductCommandService commandService;

    /**
     * Constructs a ProductStoreServer listening on the specified port.
     *
     * @param port           the TCP port to bind
     * @param commandService the command service handling client requests
     */
    public ProductStoreServer(int port, ProductCommandService commandService) {
        this.port = port;
        this.commandService = commandService;
    }

    /**
     * Starts the server loop, accepting and processing one client at a time.
     *
     * @throws IOException if the server socket fails to bind or accept
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("ProductStoreServer listening on port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                    handleClient(clientSocket);
                    System.out.println("Client disconnected.");
                } catch (IOException e) {
                    System.err.println("Client connection error: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                if (".".equals(line.trim())) {
                    break;
                }
                String response = commandService.processMessage(line);
                out.println(response);
            }
        }
    }

    /**
     * Entry point for the ProductStoreServer executable JAR.
     *
     * @param args optional command-line port argument
     * @throws IOException if the server cannot start
     */
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        InventoryService inventoryService = new InventoryManager();
        ProductCommandService commandService = new ProductCommandService(inventoryService);
        new ProductStoreServer(port, commandService).start();
    }
}
