package client.controller;

import client.model.*;

import java.util.function.Consumer;

public class ClientController {

    private ChatClient client;
    private ClientState state;

    public void start(String username) throws Exception {

        ClientConfigLoader config = new ClientConfigLoader();

        state = new ClientState();
        state.setUsername(username);

        client = new ChatClient();

        // Connect AND send username inside connect()
        client.connect(
                config.getHost(),
                config.getPort(),
                username
        );

        state.setConnected(true);
    }

    public void send(String message) {
        client.send(message);
    }

    public void stop() {
        client.close();
        state.setConnected(false);
    }

    public void setMessageHandler(Consumer<String> handler) {
        client.setMessageHandler(handler);
    }

    public ClientState getState() {
        return client.getState();
    }

    public ChatClient getClient() {
        return client;
    }
}