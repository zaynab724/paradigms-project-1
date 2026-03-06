package server.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.TCPServer;
import server.controller.ServerMediator;

public class ServerApp extends Application {

    private TextArea logArea;
    private ListView<UserInfo> usersList;

    @Override
    public void start(Stage stage) {

        logArea = new TextArea();
        logArea.setEditable(false);

        usersList = new ListView<>();
        usersList.setCellFactory(v -> new UserCell());

        BorderPane root = new BorderPane();
        root.setCenter(logArea);

        VBox right = new VBox(new Separator(), usersList);
        right.setPrefWidth(220);
        root.setRight(right);

        Scene scene = new Scene(root, 820, 520);
        stage.setTitle("Server Dashboard");
        stage.setScene(scene);
        stage.show();

        // mediator connects backend -> UI
        ServerMediator mediator = new ServerMediator();
        mediator.setLogSink(msg -> logArea.appendText(msg + "\n"));

        usersList.setItems(mediator.getUsers());

        // start server in background thread
        Thread serverThread = new Thread(() -> TCPServer.startServer(mediator));
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}