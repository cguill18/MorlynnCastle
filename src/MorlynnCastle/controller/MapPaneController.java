package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.model.space.Place;
import MorlynnCastle.view.InteractionView;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.Map;

public class MapPaneController {

    @FXML
    private FlowPane center;

    @FXML
    private FlowPane west;

    @FXML
    private FlowPane north;

    @FXML
    private FlowPane east;

    @FXML
    private FlowPane south;

    private Game game;

    private HashMap<String,String> imgPieces = new HashMap<>();

    //fonctions d'initialisation
    @FXML
    public void initialize(){}

    public void setGame(Game game) { this.game = game; }

    public void initMap(){
        this.initImage();
        this.generateMap();
    }

    //fonctions de generation des pieces
    public void generateMap() {
        this.clearMap();
        String styleGeneral = "-fx-background-position: center; -fx-background-size: contain ; -fx-background-repeat: no-repeat;";
        Place HeroPlace = this.game.getHero().getPlace();
        String HeroPlaceString = HeroPlace.getName();
        this.center.setStyle(styleGeneral+"-fx-background-image:url(\"/res/map/"+this.imgPieces.get(HeroPlaceString)+"\")");

        if (HeroPlace.getInteractions().containsKey("north"))
            this.north.setStyle("-fx-background-color: grey");
        if (HeroPlace.getInteractions().containsKey("east"))
            this.east.setStyle("-fx-background-color: grey");
        if (HeroPlace.getInteractions().containsKey("south"))
            this.south.setStyle("-fx-background-color: grey");
        if (HeroPlace.getInteractions().containsKey("west"))
            this.west.setStyle("-fx-background-color: grey");
    }

    private void clearMap() {
        this.north.setStyle("");
        this.south.setStyle("");
        this.east.setStyle("");
        this.west.setStyle("");
        this.center.setStyle("");
    }

    private void initImage() {
        this.imgPieces.put("hall","hall.png");
        this.imgPieces.put("weapon_room","weapon.png");
        this.imgPieces.put("gallery", "gallery.png");
        this.imgPieces.put("guard_room", "guard.png");
        this.imgPieces.put("cellar", "cellar.png");
        this.imgPieces.put("throne_room","throne.png");
        this.imgPieces.put("exit","exit.png");
    }

}
