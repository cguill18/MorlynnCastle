package MorlynnCastle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
        this.textArea.setText(this.textArea.getText() + text);
    }
}
