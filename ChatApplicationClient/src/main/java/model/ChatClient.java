package client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private ServerListener listener;

    private ClientState state = new ClientState();

    // Connect to server
    public void connect(String host, int port, String username) throws Exception {

        socket = new Socket(host, port);

        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        // Send username immediately
        dos.writeUTF(username);
        dos.flush();

        if (username == null || username.trim().isEmpty()) {
            state.setReadOnly(true);
        } else {
            state.setReadOnly(false);
        }
    }

    // Send message to server
    public void send(String message) {

        try {
            if (!state.isReadOnly()) {
                dos.writeUTF(message);
                dos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set UI message handler
    public void setMessageHandler(Consumer<String> handler) {

        listener = new ServerListener(dis, handler);
        listener.start();
    }

    // Close connection
    public void close() {
        try {
            if (listener != null) listener.interrupt();
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientState getState() {
        return state;
    }
}