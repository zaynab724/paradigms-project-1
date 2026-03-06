package server.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import server.view.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class ServerMediator {

    private Consumer<String> logSink = s -> {};
    private final ObservableList<UserInfo> users = FXCollections.observableArrayList();

    private final Map<String, UserInfo> byName = new HashMap<>();
    private final Random random = new Random();

    public void setLogSink(Consumer<String> logSink) {
        this.logSink = (logSink == null) ? s -> {} : logSink;
    }

    public ObservableList<UserInfo> getUsers() {
        return users;
    }

    public void log(String msg) {
        Platform.runLater(() -> logSink.accept(msg));
    }

    public void userConnected(String username) {
        if (username == null || username.trim().isEmpty()) return;

        String u = username.trim();

        Platform.runLater(() -> {
            UserInfo info = byName.get(u);
            if (info == null) {
                Color color = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
                info = new UserInfo(u, color, true);
                byName.put(u, info);
                users.add(info);
            } else {
                info.setOnline(true);
                users.set(users.indexOf(info), info); // refresh cell
            }
        });
    }

    public void userDisconnected(String username) {
        if (username == null || username.trim().isEmpty()) return;

        String u = username.trim();

        Platform.runLater(() -> {
            UserInfo info = byName.get(u);
            if (info != null) {
                info.setOnline(false);
                users.set(users.indexOf(info), info); // refresh cell
            }
        });
    }
}