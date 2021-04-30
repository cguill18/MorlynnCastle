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

    /*Boite de dialogue*/
    @FXML
    private TextArea textArea;
    @FXML
    private TextFlow textFlow;

    /*ajout du texte dans le TextArea visible*/
    public void addText(String text) {
        this.textArea.appendText(text + "\n");
    }

    /*Dialogue avec les personnages non joueurs*/
    /*Met en place le TextFlow qui permet de choisir les dialogues*/
    public void startDialog() {
        this.textFlow.setVisible(true);
        this.textFlow.setManaged(true);
    }

    /*Fin des phases de dialogue*/
    /*Fait disparaitre le TextFlow*/
    public void endDialog() {
        this.textFlow.setVisible(false);
        this.textFlow.setManaged(false);
        this.textFlow.getChildren().clear();
    }

    /*Permet de surligner les différents choix de dialogue*/
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
