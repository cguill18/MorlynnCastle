package MorlynnCastle.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import MorlynnCastle.controller.MorlynnCastleController;
import MorlynnCastle.model.commands.InvalidArgumentNumberException;
import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.space.*;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.GridPane;


public class DirectionPaneController {
    
    @FXML
    private GridPane directionPane;
    
    private Game game;
    
    private Map<String, String> roomUrl;
    
    @FXML
    private Button upButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private Button bottomButton;

    private MorlynnCastleController morlynnCastleController;

    private SceneryPaneController sceneryPaneController;
    
    private DialogBoxController dialogBoxController;
    
    /****************************/
    /*********methods************/
    /****************************/
    
    @FXML
    public void initialize() {
        this.roomUrl = new HashMap<>();
    }

    public void setGame(Game game) { 
        this.game = game; 
    }


    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    
    //actions des boutons
    @FXML
    private void upAction(ActionEvent event) {
       this.morlynnCastleController.moveHero("north");
    }
    
    @FXML
    private void leftAction(ActionEvent event) {
        this.morlynnCastleController.moveHero("west");
    }
    
    @FXML
    private void rightAction(ActionEvent event) {
        this.morlynnCastleController.moveHero("east");
    }
    
    @FXML
    private void bottomAction(ActionEvent event) {
        this.morlynnCastleController.moveHero("south");
    }
    
}
