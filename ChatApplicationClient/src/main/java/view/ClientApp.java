package client.view;

import client.controller.ClientController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientApp extends Application {

    private ClientController controller;
    private TextArea chatArea;
    private TextField messageField;

    @Override
    public void start(Stage stage) {

        controller = new ClientController();

        try {
            // Ask username
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Login");
            dialog.setHeaderText("Enter username (leave empty for read-only)");
            String username = dialog.showAndWait().orElse("");

            controller.start(username);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Chat display
        chatArea = new TextArea();
        chatArea.setEditable(false);

        // Message input
        messageField = new TextField();
        messageField.setPromptText("Type message...");

        Button sendButton = new Button("Send");

        sendButton.setOnAction(e -> sendMessage());
        messageField.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, messageField, sendButton);
        inputBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("Chat Client");
        stage.setScene(scene);
        stage.show();

        // Disable input if read-only
        if (controller.getState().isReadOnly()) {
            messageField.setDisable(true);
            sendButton.setDisable(true);
        }

        // Connect listener to UI
        controller.setMessageHandler(message ->
                chatArea.appendText(message + "\n")
        );
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            controller.send(message);
            messageField.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}