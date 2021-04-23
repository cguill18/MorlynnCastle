/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.Container;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.model.space.Interaction;
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
    
    private Container container;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML public void handleTake(MouseEvent event) {
     EventTarget eventTarget = event.getTarget();
        InteractionView interactionView = (InteractionView) eventTarget;
        Interaction interaction = interactionView.getInteraction();
        if (interaction instanceof Item){
            this.game.getHero().takeFromContainer(this.container, (Item) interaction);
            this.dialogBoxController.addText("You add this " + ((Item)interaction).getName() + " to your inventory.\n");
            this.removeInteractionView(interactionView); 
            
            this.characterPaneController.displayInventory(this.game.getHero().getInventory());
        }
    }
    
    public void setContainerLooking(Container container){
        this.container = container;
    }
    
    public void removeInteractionView(InteractionView interactionView){
        this.gridPane.getChildren().remove(interactionView);
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
        gridPane.getChildren().clear();
        final int[] i = {0};
        final int[] j = {0};
        container.forEach((name,item)->{
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/"+item.getImage()+"\")");
            gridPane.add(interactionView, i[0]%this.gridPane.getColumnConstraints().size(), j[0]/(this.gridPane.getRowConstraints().size()-1));
            i[0]++;
            j[0]++;
        }); 
    }
    
    
}
