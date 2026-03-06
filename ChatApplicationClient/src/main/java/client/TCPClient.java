package client;

import client.controller.ClientController;

import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) {

        try {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter username (leave empty for read-only): ");
            String username = scanner.nextLine();

            ClientController controller = new ClientController();
            controller.start(username);

            if (controller.getState().isReadOnly()) {
                System.out.println("Read-only mode enabled.");
            }

            while (true) {

                String input = scanner.nextLine();

                if ("bye".equalsIgnoreCase(input)) {
                    controller.send("bye");
                    controller.stop();
                    break;
                }

                controller.send(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}