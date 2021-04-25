package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.item.Armor;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.model.item.Weapon;
import MorlynnCastle.view.InteractionView;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.util.Map;

public class CharacterPaneController {

    @FXML
    private Label name;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private GridPane inventoryPane;

    @FXML
    private HBox avatar;

    @FXML
    private TabPane tabPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private BorderPane weaponPane;

    @FXML
    private Label weaponLabel;

    @FXML
    private BorderPane armorPane;

    @FXML
    private Label armorLabel;

    private MorlynnCastleController morlynnCastleController;

    @FXML
    public void initialize() {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void setProgress(DoubleProperty doubleProperty){
        this.hpBar.progressProperty().bind(doubleProperty);
    }

    public void displayInventory(Map<String, Item> inventory) {
        this.inventoryPane.getChildren().clear();
        final int[] i = {0};
        inventory.forEach((name, item) -> {
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle() + "-fx-background-image:url(\"/res/" + item.getImage() + "\")");
            inventoryPane.add(interactionView, i[0] % this.inventoryPane.getColumnConstraints().size(), i[0] / this.inventoryPane.getColumnConstraints().size());
            i[0]++;
            if (i[0]/this.inventoryPane.getColumnConstraints().size() > (this.inventoryPane.getRowConstraints().size() -1))
                this.addInventoryRow();
        });
    }

    public void removeInteractionView(InteractionView interactionView) {
        this.inventoryPane.getChildren().remove(interactionView);
    }

    public void addInventoryRow() {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.SOMETIMES);
        rowConstraints.prefHeightProperty().bind(this.scrollPane.heightProperty().divide(4));
        this.inventoryPane.getRowConstraints().add(rowConstraints);
    }


    @FXML
    public void handleClick(MouseEvent event) {
        EventTarget eventTarget = event.getTarget();
        System.out.println(eventTarget);
        if (eventTarget instanceof InteractionView) {
            this.morlynnCastleController.launchCommandForInventory((InteractionView) eventTarget);
        }
    }


    @FXML
    public void MyStartDragAndDrop(MouseEvent event) {
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView) {
            Dragboard db = inventoryPane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("le drag and drop est capricieux");
            db.setContent(content);
            event.consume();
            this.morlynnCastleController.launchDrag((InteractionView) eventTarget);
        }
    }


    public void clearEquipment(){
        this.weaponPane.setCenter(null);
        this.armorPane.setCenter(null);
        this.armorLabel.setText(null);
        this.weaponLabel.setText(null);
    }

    public void updateEquipment(Hero hero) {
        if (hero.getArmor() != null)
            this.addArmor(hero.getArmor());
        if (hero.getWeapon() != null)
            this.addWeapon(hero.getWeapon());
    }


    public void addArmor(Armor armor) {
        InteractionView armorView = new InteractionView(armor);
        armorView.setStyle(armorView.getStyle() + "-fx-background-image:url(\"/res/" + armor.getImage() + "\")");
        this.armorPane.setCenter(armorView);
        this.armorLabel.setText(armor.getName() + "\n" + "Armor class: " + armor.getArmorClass());
    }

    public void addWeapon(Weapon weapon) {
        InteractionView weaponView = new InteractionView(weapon);
        weaponView.setStyle(weaponView.getStyle() + "-fx-background-image:url(\"/res/" + weapon.getImage() + "\")");
        this.weaponPane.setCenter(weaponView);
        this.weaponLabel.setText(weapon.getName() + "\n" + "Attack Power: " + weapon.getAttackPower());
    }
}
