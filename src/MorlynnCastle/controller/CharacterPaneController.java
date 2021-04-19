package MorlynnCastle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CharacterPaneController {

    @FXML
    private Label name;

    @FXML
    private Label inventory;

    @FXML
    private HBox avatar;

    @FXML
    private Label equipment;

    private MorlynnCastleController morlynnCastleController;

    @FXML
    public void initialize(){

    }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    @FXML
    public void openInventory(){
        this.morlynnCastleController.openInventory();
    }
}
