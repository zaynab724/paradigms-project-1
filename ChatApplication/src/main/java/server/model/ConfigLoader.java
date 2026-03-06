package server.model;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties props = new Properties();

    public ConfigLoader() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("server.properties")) {
            if (in == null) {
                throw new RuntimeException("server.properties not found in resources!");
            }
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load server.properties: " + e.getMessage(), e);
        }
    }

    public int getPort() {
        String portStr = props.getProperty("port");
        if (portStr == null || portStr.trim().isEmpty()) {
            throw new RuntimeException("Missing 'port' in server.properties");
        }
        return Integer.parseInt(portStr.trim());
    }
}