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

    @FXML
    private GridPane sceneryPane;

    private MorlynnCastleController morlynnCastleController;

    private HashMap<String, String> imagePlaces = new HashMap<>();

    public SceneryPaneController() {
        this.imagePlaces.put("hall", "hall.png");
        this.imagePlaces.put("weapon_room", "weapon.png");
        this.imagePlaces.put("gallery", "gallery.png");
        this.imagePlaces.put("guard_room", "guard.png");
        this.imagePlaces.put("cellar", "cellar.png");
        this.imagePlaces.put("throne_room", "throne.png");
        this.imagePlaces.put("exit", "exit.png");
    }

    @FXML
    public void initialize() {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100% ; -fx-background-repeat: no-repeat;";
        this.sceneryPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/pieces/hall.png\")");
    }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void setBackground(Place place) {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;";
        this.sceneryPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/pieces/" + this.imagePlaces.get(place.getName()) + "\")");
    }

    public void initScenery(Place place) {
        this.setBackground(place);
        this.displayRoomItems(place.getInteractions());
    }


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

    @FXML
    public void myDragAndDrop(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasImage()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
        dragEvent.consume();
    }


    //methodes pour les objets dans la piece
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

    public void removeInteractionView(InteractionView<Interaction> interactionView) {
        this.sceneryPane.getChildren().remove(interactionView);
    }


}
