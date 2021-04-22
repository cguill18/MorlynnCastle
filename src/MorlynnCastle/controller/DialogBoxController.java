package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DialogBoxController {

    @FXML
    private TextArea textArea;
    @FXML
    private TextFlow textFlow;

    @FXML
    public void initialize(){

    }
    
    public void addText(String text) {
        this.textArea.appendText( text + "\n");
    }

    public void startDialog(){
        this.textFlow.setVisible(true);
        this.textFlow.setManaged(true);
    }

    public void endDialog(){
        this.textFlow.setVisible(false);
        this.textFlow.setManaged(false);
        this.textFlow.getChildren().clear();
    }

    public void addDialog(String text, EventHandler answer){
        Text line = new Text(text+"\n");
        this.textFlow.getChildren().add(line);
        line.setOnMouseClicked(answer);
    }
}
