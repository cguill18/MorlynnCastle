package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.item.Armor;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.model.item.Weapon;
import MorlynnCastle.view.InteractionView;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Map;

public class CharacterPaneController {

    /* --------------------------- attributs ---------------------------------- */
    /** elements de la vue */
    @FXML
    private Label name;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private GridPane inventoryPane;

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

    /** controleur principal */
    private MorlynnCastleController morlynnCastleController;

    /* --------------------------------------- methodes -------------------------------------------*/
    @FXML
    public void initialize() {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    /** setter */
    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    /** mise à jour de la barre de vie */
    public void setProgress(DoubleProperty doubleProperty) {
        this.hpBar.progressProperty().bind(doubleProperty);
    }

    /** met à jour la vue de l'inventaire */
    public void displayInventory(Map<String, Item> inventory) {
        this.inventoryPane.getChildren().clear();
        final int[] i = {0};
        inventory.forEach((name, item) -> {
            InteractionView<Item> interactionView = new InteractionView<>(item);
            interactionView.changeTooltip(name+"\n"+item.getDescription());
            interactionView.setOnMouseClicked(event -> this.morlynnCastleController.launchCommandForInventory(interactionView));
            interactionView.setOnDragDetected(event -> this.myStartDragAndDrop(event, interactionView));
            inventoryPane.add(interactionView, i[0] % this.inventoryPane.getColumnConstraints().size(), i[0] / this.inventoryPane.getColumnConstraints().size());
            i[0]++;
            if (i[0] / this.inventoryPane.getColumnConstraints().size() > (this.inventoryPane.getRowConstraints().size() - 1))
                this.addInventoryRow();
        });
    }

    /** vue de l'inventaire, pour avoir un repartion equitable de la place des objets */
    public void addInventoryRow() {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.SOMETIMES);
        rowConstraints.prefHeightProperty().bind(this.scrollPane.heightProperty().divide(4));
        this.inventoryPane.getRowConstraints().add(rowConstraints);
    }

    /** fonction qui detecte un drag and drop */
    public void myStartDragAndDrop(MouseEvent event, InteractionView<Item> interactionView) {
        Dragboard db = inventoryPane.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        content.putImage(interactionView.snapshot(snapshotParameters, null));
        db.setContent(content);
        event.consume();
        this.morlynnCastleController.launchDrag(interactionView);
    }

    /** vide la vue de l'equipement */
    public void clearEquipment() {
        this.weaponPane.setCenter(null);
        this.armorPane.setCenter(null);
        this.armorLabel.setText(null);
        this.weaponLabel.setText(null);
    }

    /** met à jour la vue de l'equipement */
    public void updateEquipment(Hero hero) {
        if (hero.getArmor() != null)
            this.addArmor(hero.getArmor());
        if (hero.getWeapon() != null)
            this.addWeapon(hero.getWeapon());
    }

    /** ajoute une armure dans la vue de l'equipement */
    public void addArmor(Armor armor) {
        InteractionView<Armor> armorView = new InteractionView<>(armor);
        this.armorPane.setCenter(armorView);
        this.armorLabel.setText(armor.getName() + "\n" + "Armor class: " + armor.getArmorClass());
    }

    /** ajoute une arme dans la vue de l'equipement */
    public void addWeapon(Weapon weapon) {
        InteractionView<Weapon> weaponView = new InteractionView<>(weapon);
        this.weaponPane.setCenter(weaponView);
        this.weaponLabel.setText(weapon.getName() + "\n" + "Attack Power: " + weapon.getAttackPower());
    }
}
