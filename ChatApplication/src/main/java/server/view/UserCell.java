package server.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UserCell extends ListCell<UserInfo> {

    @Override
    protected void updateItem(UserInfo item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        Circle dot = new Circle(6);
        dot.setFill(item.isOnline() ? Color.LIMEGREEN : Color.GRAY);

        Label name = new Label(item.getUsername());
        name.setTextFill(item.getColor());

        HBox box = new HBox(10, dot, name);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(6));

        setGraphic(box);
    }
}