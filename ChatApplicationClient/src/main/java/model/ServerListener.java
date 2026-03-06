package client.model;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.util.function.Consumer;

public class ServerListener extends Thread {

    private final DataInputStream dis;
    private final Consumer<String> messageHandler;

    public ServerListener(DataInputStream dis, Consumer<String> messageHandler) {
        this.dis = dis;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = dis.readUTF();

                Platform.runLater(() ->
                        messageHandler.accept(message)
                );
            }
        } catch (Exception e) {
            System.out.println("Disconnected from server.");
        }
    }
}