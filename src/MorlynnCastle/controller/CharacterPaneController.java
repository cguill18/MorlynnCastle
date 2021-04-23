package MorlynnCastle.controller;

import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.Map;

public class CharacterPaneController {

    @FXML
    private Label name;

    @FXML
    private GridPane inventoryPane;

    @FXML
    private HBox avatar;

    @FXML
    private TabPane tabPane;

    @FXML
    private ScrollPane scrollPane;

    private MorlynnCastleController morlynnCastleController;

    @FXML
    public void initialize(){
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void displayInventory(Map<String, Item> inventory){
        this.inventoryPane.getChildren().clear();
        final int[] i = {0};
        inventory.forEach((name,item)->{
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/"+ item.getImage()+"\")");
            inventoryPane.add(interactionView, i[0]%this.inventoryPane.getRowConstraints().size(), i[0]/this.inventoryPane.getRowConstraints().size());
            i[0]++;
        });
        this.addInventoryRow();
    }

    public void removeInteractionView(InteractionView interactionView){
        this.inventoryPane.getChildren().remove(interactionView);
    }

    public void addInventoryRow(){
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.SOMETIMES);
        rowConstraints.prefHeightProperty().bind(this.scrollPane.heightProperty().divide(4));
        this.inventoryPane.getRowConstraints().add(rowConstraints);
    }


    @FXML
    public void handleClick(MouseEvent event){
        EventTarget eventTarget = event.getTarget();
        System.out.println(eventTarget);
        if (eventTarget instanceof InteractionView){
            this.morlynnCastleController.launchCommandForInventory((InteractionView)eventTarget);
            System.out.println("bon le click marche");
        }
    }


    @FXML
    public void MyStartDragAndDrop(MouseEvent event) {
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView){
            System.out.println("la souris est pressée");
            Dragboard db = inventoryPane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("le drag and drop est capricieux");
            db.setContent(content);
            event.consume();
            this.morlynnCastleController.launchDrag((InteractionView)eventTarget);
        }
    }
}
