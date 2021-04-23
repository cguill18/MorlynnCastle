/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
public class ContainerPaneController implements Initializable {
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button take;
    
    private MorlynnCastleController morlynnCastleController;
    
    private Game game;
    
    private DialogBoxController dialogBoxController;
    
    private CharacterPaneController characterPaneController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML public void handleTake(MouseEvent event) {
      /*  EventTarget eventTarget = event.getTarget();
        (Interaction) eventTarget;
        for (int i = 0; i < this.gridPane.getColumnConstraints().size(); i++) {
            for (int j = 0; j < this.gridPane.getRowConstraints().size(); j++) {
               Item item = (Item) gridPane.getChildren();
               this.game.getHero().take((Item) item);
            this.dialogBoxController.addText("You add this " + item.getName() + " to your inventory.\n");
            }
        }
        this.gridPane.getChildren().clear();
        this.characterPaneController.displayInventory(this.game.getHero().getInventory());*/
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }
    
    public void setCharacterPaneController(CharacterPaneController characterPaneController) {
        this.characterPaneController = characterPaneController;
    }
    
    public void setDialogBoxController(DialogBoxController dialogBoxController) {
        this.dialogBoxController = dialogBoxController;
    }
    
    public void displayContainer(Map<String, Item> container){
        final int[] i = {0};
        container.forEach((name,item)->{
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/"+item.getImage()+"\")");
            gridPane.add(interactionView, i[0]%this.gridPane.getColumnConstraints().size(), i[0]/this.gridPane.getRowConstraints().size());
            i[0]++;
        });        
    }
    
    
}
