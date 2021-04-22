package MorlynnCastle.controller;

import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class InventoryPaneController {

    @FXML
    private GridPane gridPane;

    private CharacterPaneController characterPaneController;


    @FXML
    public void initialize(){
    }


     public void displayInventory(Map<String, Item> inventory){
         final int[] i = {0};
        inventory.forEach((name,item)->{
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/armor.png\")");
            gridPane.add(interactionView, i[0]%this.gridPane.getRowConstraints().size(), i[0]/this.gridPane.getRowConstraints().size());
            i[0]++;
        });
     }

    public void removeInteractionView(InteractionView interactionView){
        this.gridPane.getChildren().remove(interactionView);
    }

}
