package MorlynnCastle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MorlynnCastleController implements Initializable {

    @FXML
    private GridPane commandPane;

    @FXML
    private Button takeButtonCommand;

    @FXML
    private Button attackButtonCommand;

    @FXML
    private Button lookButtonCommand;

    @FXML
    private Button useButtonCommand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
