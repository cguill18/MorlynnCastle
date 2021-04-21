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
    

    private SceneryPaneController sceneryPaneController;
    
    private DialogBoxController dialogBoxController;
    
    /****************************/
    /*********methods************/
    /****************************/
    
    @FXML
    public void initialize() {
        this.roomUrl = new HashMap<>();
    }
    
    public void initMapImg() {
        this.roomUrl = this.addHashMapRoom();
    }
    
    public void setGame(Game game) { 
        this.game = game; 
    }
    
    //ajout, si il n'existe pas déjà, de la pièce actuelle en clé et de l'image associée
    public Map<String, String> addHashMapRoom() {
        String name = this.game.getHero().getPlace().getName();
        this.roomUrl.put(name, name+".png");
        return this.roomUrl;
    }
    
    public void setSceneryPaneController(SceneryPaneController sceneryPaneController) {
        this.sceneryPaneController = sceneryPaneController;
    }
    
    public void setDialogBoxController(DialogBoxController dialogBoxController) {
        this.dialogBoxController = dialogBoxController;
    }
    
    //change le background du sceneryPane
    public void moveRoomImg(String img) {
        this.sceneryPaneController.setBackground(img);
    }
    
    //déplace hero en fonction de la direction qu'il a sélectionné avec les boutons 
    public void moveHeroPlace(String direction) {
        Door door = (Door) this.game.getHero().getPlace().getInteractions().get(direction);
        
        if (door != null) {
            this.game.getHero().go(door);
            
            this.roomUrl = this.addHashMapRoom();
            String name = this.game.getHero().getPlace().getName();
            String img = this.roomUrl.get(name);
            this.moveRoomImg(img);
            
            String description = this.game.getHero().getPlace().getDescription();
            this.dialogBoxController.addText(description);
        }
        else {
           this.dialogBoxController.addText(this.noDoorMessage());
        }
    }
    
    //Message signalant qu'il n'y a pas de porte dans cette direction
    public String noDoorMessage () {
        String noDoor = "Are you sure that a door exists there ?";
        return noDoor;
    }
    
    //actions des boutons
    @FXML
    private void upAction(ActionEvent event) {
       this.moveHeroPlace("north");
    }
    
    @FXML
    private void leftAction(ActionEvent event) {
        this.moveHeroPlace("west");
    }
    
    @FXML
    private void rightAction(ActionEvent event) {
        this.moveHeroPlace("east");
    }
    
    @FXML
    private void bottomAction(ActionEvent event) {
        this.moveHeroPlace("south");
    }
    
}
