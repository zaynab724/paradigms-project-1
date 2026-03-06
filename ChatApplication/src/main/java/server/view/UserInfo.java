package server.view;

import javafx.scene.paint.Color;

public class UserInfo {
    private final String username;
    private final Color color;
    private boolean online;

    public UserInfo(String username, Color color, boolean online) {
        this.username = username;
        this.color = color;
        this.online = online;
    }

    public String getUsername() { return username; }
    public Color getColor() { return color; }

    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
}