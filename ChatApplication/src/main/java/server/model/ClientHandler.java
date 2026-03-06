package server.model;

import server.controller.ServerMediator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final ServerMediator mediator;

    private DataInputStream dis;
    private DataOutputStream dos;

    private String username = ""; // empty => read-only client
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ClientHandler(Socket socket, ServerMediator mediator) {
        this.socket = socket;
        this.mediator = mediator;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            // 1) first message from client = username (can be empty => read-only)
            username = dis.readUTF();
            if (username == null) username = "";
            username = username.trim();

            ClientManager.add(this);

            if (!username.isEmpty()) {
                log(username + " connected");
                if (mediator != null) mediator.userConnected(username);

                ClientManager.broadcast(username + " joined the chat");
                log(username + " joined the chat");
            } else {
                log("Read-only client connected");
            }

            // 2) read messages
            while (true) {
                String msg = dis.readUTF();
                if (msg == null) continue;
                msg = msg.trim();

                // Disconnect commands
                if (msg.equalsIgnoreCase("bye") || msg.equalsIgnoreCase("end")) {
                    break;
                }

                // allUsers command (reply ONLY to this client)
                if (msg.equalsIgnoreCase("allUsers")) {
                    List<String> users = ClientManager.getActiveUsernames();

                    StringBuilder sb = new StringBuilder();
                    sb.append("[Server] Active users (").append(users.size()).append("): ");
                    for (int i = 0; i < users.size(); i++) {
                        sb.append(users.get(i));
                        if (i < users.size() - 1) sb.append(", ");
                    }

                    send(sb.toString());
                    log((username.isEmpty() ? "anon" : username) + " requested allUsers");
                    continue;
                }

                // Normal chat message: format with time + username
                String sender = username.isEmpty() ? "anon" : username;
                String time = LocalTime.now().format(TIME_FMT);
                String formatted = "[" + time + "] " + sender + ": " + msg;

                ClientManager.broadcast(formatted);
                log(formatted);
            }

        } catch (Exception ignored) {
            // client disconnected unexpectedly
        } finally {
            ClientManager.remove(this);

            if (!username.isEmpty()) {
                ClientManager.broadcast(username + " left the chat");
                log(username + " disconnected");
                if (mediator != null) mediator.userDisconnected(username);
            } else {
                log("Read-only client disconnected");
            }

            try { if (dis != null) dis.close(); } catch (Exception ignored) {}
            try { if (dos != null) dos.close(); } catch (Exception ignored) {}
            try { socket.close(); } catch (Exception ignored) {}
        }
    }

    public void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (Exception ignored) {}
    }

    private void log(String msg) {
        if (mediator != null) mediator.log(msg);
    }
}