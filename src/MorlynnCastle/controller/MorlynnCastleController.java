package MorlynnCastle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class MorlynnCastleController {

    @FXML
    private Pane scenery;

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

    @FXML
    public void initialize() {
        try {
            scenery.getChildren().add(FXMLLoader.load(getClass().getResource("../view/hall.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
