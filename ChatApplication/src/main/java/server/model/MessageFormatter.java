package server.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MessageFormatter {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm");

    public static String format(String user, String msg) {

        String time =
                LocalTime.now().format(formatter);

        return "[" + time + "] "
                + user + ": "
                + msg;
    }
}