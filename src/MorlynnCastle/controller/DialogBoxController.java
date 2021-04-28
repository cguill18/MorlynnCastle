package MorlynnCastle.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DialogBoxController {

    @FXML
    private TextArea textArea;
    @FXML
    private TextFlow textFlow;


    public void addText(String text) {
        this.textArea.appendText(text + "\n");
    }

    public void startDialog() {
        this.textFlow.setVisible(true);
        this.textFlow.setManaged(true);
    }

    public void endDialog() {
        this.textFlow.setVisible(false);
        this.textFlow.setManaged(false);
        this.textFlow.getChildren().clear();
    }

    public void addDialog(String text, EventHandler<MouseEvent> answer) {
        Text line = new Text(text + "\n");
        line.setOnMouseEntered(event -> {
            line.getScene().setCursor(Cursor.HAND);
            line.setFill(Color.CADETBLUE);
        });
        line.setOnMouseExited(event -> {
            line.getScene().setCursor(Cursor.DEFAULT);
            line.setFill(Color.BLACK);
        });
        this.textFlow.getChildren().add(line);
        line.setOnMouseClicked(answer);
    }
}
