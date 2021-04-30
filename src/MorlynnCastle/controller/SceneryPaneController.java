package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.model.space.Place;
import MorlynnCastle.view.InteractionView;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class SceneryPaneController {

    /** la vue */
    @FXML
    private GridPane sceneryPane;

    /** le controleur principal */
    private MorlynnCastleController morlynnCastleController;

    /** liste des images des pieces */
    private HashMap<String, String> imagePlaces = new HashMap<>();

    /* --------------------------------------- methodes ---------------------------------------*/

    /** création des images liée au nom des pièces */
    public SceneryPaneController() {
        this.imagePlaces.put("hall", "hall.png");
        this.imagePlaces.put("weapon_room", "weapon.png");
        this.imagePlaces.put("gallery", "gallery.png");
        this.imagePlaces.put("guard_room", "guard.png");
        this.imagePlaces.put("cellar", "cellar.png");
        this.imagePlaces.put("throne_room", "throne.png");
        this.imagePlaces.put("exit", "exit.png");
    }

    /** initialisation sur la première pièce du jeu */
    @FXML
    public void initialize() {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100% ; -fx-background-repeat: no-repeat;";
        this.sceneryPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/pieces/hall.png\")");
    }

    /** recuperation du controleur principal */
    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    /** change le fond de la pièce */
    public void setBackground(Place place) {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;";
        this.sceneryPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/pieces/" + this.imagePlaces.get(place.getName()) + "\")");
    }

    /** initialise le fond et les objets dans la pièce */
    public void initScenery(Place place) {
        this.setBackground(place);
        this.displayRoomItems(place.getInteractions());
    }

    /** methode pour générer sur la vue les objets de la piece e */
    public void displayRoomItems(Map<String, Interaction> interactions) {
        this.sceneryPane.getChildren().clear();
        for (Map.Entry<String, Interaction> objects : interactions.entrySet()) {
            if (!(objects.getValue() instanceof Hero)) {
                InteractionView<Interaction> inte = new InteractionView<>(objects.getValue());
                inte.setOnMouseClicked(event -> this.morlynnCastleController.launchCommand(inte));
                inte.setOnDragOver( dragEvent -> this.myDragAndDrop(dragEvent));
                inte.setOnDragDropped(dragEvent -> this.myEndDragAndDrop(dragEvent, inte));
                this.sceneryPane.add(inte, objects.getValue().getPosx(), objects.getValue().getPosy());
            }
        }
    }

    /** vide la scene des précédents objets */
    public void removeInteractionView(InteractionView<Interaction> interactionView) {
        this.sceneryPane.getChildren().remove(interactionView);
    }

    /** fonction qui détecte la fin du drag and drop du "use" */
    @FXML
    public void myEndDragAndDrop(DragEvent event, InteractionView<Interaction> interactionView) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage()) {
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
        this.morlynnCastleController.launchDrop(interactionView);

    }

    /** fonction qui fait suivre le drag and drop du "use" */
    @FXML
    public void myDragAndDrop(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasImage()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
        dragEvent.consume();
    }

}
