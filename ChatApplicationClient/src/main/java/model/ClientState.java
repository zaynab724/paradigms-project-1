package client.model;

public class ClientState {

    private String username;
    private boolean connected;
    private boolean readOnly;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

        // Automatically set read-only if username empty
        if (username == null || username.trim().isEmpty()) {
            this.readOnly = true;
        } else {
            this.readOnly = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}