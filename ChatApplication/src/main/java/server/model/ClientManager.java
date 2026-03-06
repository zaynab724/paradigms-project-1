package server.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {

    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void add(ClientHandler c) {
        clients.add(c);
    }

    public static void remove(ClientHandler c) {
        clients.remove(c);
    }

    public static void broadcast(String msg) {
        for (ClientHandler c : clients) {
            c.send(msg);
        }
    }

    //  This is what  ClientHandler is calling
    public static List<String> getActiveUsernames() {
        Set<String> names = new LinkedHashSet<>(); // keeps order, no duplicates

        for (ClientHandler c : clients) {
            String u = c.getUsername();
            if (u != null) {
                u = u.trim();
                if (!u.isEmpty()) {
                    names.add(u);
                }
            }
        }
        return new ArrayList<>(names);
    }
}