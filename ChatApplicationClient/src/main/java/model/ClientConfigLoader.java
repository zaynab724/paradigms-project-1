package client.model;

import java.io.InputStream;
import java.util.Properties;

public class ClientConfigLoader {

    private String host = "localhost";
    private int port = 4444;

    public ClientConfigLoader() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("client.properties")) {
            if (in == null) {
                System.out.println("client.properties not found, using defaults.");
                return;
            }
            Properties props = new Properties();
            props.load(in);

            host = props.getProperty("host", host);
            port = Integer.parseInt(props.getProperty("port", String.valueOf(port)));

        } catch (Exception e) {
            System.out.println("Failed to load client.properties, using defaults.");
            e.printStackTrace();
        }
    }

    public String getHost() { return host; }
    public int getPort() { return port; }
}