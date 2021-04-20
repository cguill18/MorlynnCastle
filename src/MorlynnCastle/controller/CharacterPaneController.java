package MorlynnCastle.controller;

import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
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

    public void displayInventory(Map<String, Item> inventory){
        final int[] i = {0};
        inventory.forEach((name,item)->{
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/armor.png\")");
            inventoryPane.add(interactionView, i[0]%this.inventoryPane.getColumnCount(), i[0]/this.inventoryPane.getColumnCount());
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
}
