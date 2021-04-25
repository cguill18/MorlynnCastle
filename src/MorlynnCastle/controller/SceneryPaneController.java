package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.item.Key;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.model.space.Place;
import MorlynnCastle.view.InteractionView;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneryPaneController {
    @FXML
    private GridPane sceneryPane;

    private Game game;

    private MorlynnCastleController morlynnCastleController;

    private HashMap<String,String> imagePlaces = new HashMap<>();

    public SceneryPaneController() {
        this.imagePlaces.put("hall","hall.png");
        this.imagePlaces.put("weapon_room","weapon.png");
        this.imagePlaces.put("gallery", "gallery.png");
        this.imagePlaces.put("guard_room", "guard.png");
        this.imagePlaces.put("cellar", "cellar.png");
        this.imagePlaces.put("throne_room","throne.png");
        this.imagePlaces.put("exit","exit.png");
    }

    @FXML
    public void initialize() {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100% ; -fx-background-repeat: no-repeat;";
        this.sceneryPane.setStyle(styleGeneral+"-fx-background-image:url(\"/res/pieces/hall.png\")");
    }

    public void setGame(Game game){ this.game = game; }

    public Game getGame() { return this.game; }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void setBackground(Place place){
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;";
        this.sceneryPane.setStyle(styleGeneral+"-fx-background-image:url(\"/res/pieces/" + this.imagePlaces.get(place.getName()) +"\")");
    }

    public void initScenery(Place place) {
        this.setBackground(place);
        this.generateRoomItems();
    }

    @FXML
    public void handleClick(MouseEvent event) throws IOException {
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView){
            this.morlynnCastleController.launchCommand((InteractionView) eventTarget);
        }
    }


    @FXML
    public void MyEndDragAndDrop(DragEvent event) {
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView){
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()){
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
            this.morlynnCastleController.launchDrop((InteractionView)eventTarget);
        }
    }

    @FXML
    public void MyDragAndDrop(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
        dragEvent.consume();
    }


    //methodes pour les objets dans la piece
    public void generateRoomItems() {
        this.sceneryPane.getChildren().clear();
        Map<String, Interaction> interactions = game.getHero().getPlace().getInteractions();
        for (Map.Entry<String, Interaction> objects : interactions.entrySet()) {
            if (!(objects.getValue() instanceof Hero)) {
                InteractionView inte = new InteractionView(objects.getValue());
                inte.setStyle(inte.getStyle() + "-fx-background-image:url(\"/res/"+objects.getValue().getImage()+"\")");
                this.sceneryPane.add(inte, objects.getValue().getPosx(), objects.getValue().getPosy());
            }
        }
    }

    public void removeInteractionView(InteractionView interactionView){
        this.sceneryPane.getChildren().remove(interactionView);
    }



}
