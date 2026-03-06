package server;

import server.controller.ServerMediator;
import server.model.ClientHandler;
import server.model.ConfigLoader;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void startServer(ServerMediator mediator) {
        try {
            ConfigLoader config = new ConfigLoader();
            int port = config.getPort();

            ServerSocket serverSocket = new ServerSocket(port);
            if (mediator != null) mediator.log("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, mediator);
                handler.start();
            }

        } catch (Exception e) {
            if (mediator != null) mediator.log("Server crashed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}