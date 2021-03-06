package MorlynnCastle.controller;

import MorlynnCastle.model.space.Door;
import MorlynnCastle.model.space.Place;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;

import java.util.HashMap;

public class MapPaneController {

    /** La carte */
    @FXML
    private GridPane mapPane;

    /** pièces dans la carte */
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

    /** sauvegarder les pieces et leur image */
    private HashMap<String, String> imgPieces = new HashMap<>();

    /** popup de la carte entière */
    private Popup popup;

    /** associe chaque pièce à son image */
    public MapPaneController() {
        this.imgPieces.put("hall", "hall.png");
        this.imgPieces.put("weapon_room", "weapon.png");
        this.imgPieces.put("gallery", "gallery.png");
        this.imgPieces.put("guard_room", "guard.png");
        this.imgPieces.put("cellar", "cellar.png");
        this.imgPieces.put("throne_room", "throne.png");
        this.imgPieces.put("exit", "exit.png");
    }

    @FXML
    public void initialize() {
        this.initPopup();
    }

    /**initialisation du pop up pour afficher la carte */
    private void initPopup() {
        this.popup = new Popup();
        ImageView img = new ImageView();
        img.setImage(new Image("res/map/fullMap.png"));
        popup.getContent().add(img);
        popup.setAutoHide(true);
    }


    /**
     * fonction de generation des pieces dans la carte
     * @param HeroPlace la pièce dans laquelle est le personnage joueur
     */
    public void generateMap(Place HeroPlace) {
        this.clearMap();
        String styleGeneral = "-fx-background-position: center; -fx-background-size: 100% 100% ; -fx-background-repeat: no-repeat;";
        String HeroPlaceString = HeroPlace.getName();
        this.center.setStyle(styleGeneral + "-fx-background-image:url(\"/res/map/" + this.imgPieces.get(HeroPlaceString) + "\")");

        if (HeroPlace.getInteractions().containsKey("north")) {
            Door d = (Door) HeroPlace.getInteractions().get("north");
            String place = d.getExit().getName();
            this.north.setStyle(styleGeneral + "-fx-background-image:url(\"/res/map/" + this.imgPieces.get(place) + "\")");
        }
        if (HeroPlace.getInteractions().containsKey("east")) {
            Door d = (Door) HeroPlace.getInteractions().get("east");
            String place = d.getExit().getName();
            this.east.setStyle(styleGeneral + "-fx-background-image:url(\"/res/map/" + this.imgPieces.get(place) + "\")");
        }
        if (HeroPlace.getInteractions().containsKey("south")) {
            Door d = (Door) HeroPlace.getInteractions().get("south");
            String place = d.getExit().getName();
            this.south.setStyle(styleGeneral + "-fx-background-image:url(\"/res/map/" + this.imgPieces.get(place) + "\")");
        }
        if (HeroPlace.getInteractions().containsKey("west")) {
            Door d = (Door) HeroPlace.getInteractions().get("west");
            String place = d.getExit().getName();
            this.west.setStyle(styleGeneral + "-fx-background-image:url(\"/res/map/" + this.imgPieces.get(place) + "\")");
        }
    }

    private void clearMap() {
        this.north.setStyle("");
        this.south.setStyle("");
        this.east.setStyle("");
        this.west.setStyle("");
        this.center.setStyle("");
    }


    /** affiche la carte en entier après avoir utilisé la loupe */
    @FXML
    public void showMap() {
        if (!this.popup.isShowing())
            this.popup.show(this.mapPane, this.mapPane.getLayoutX(), this.mapPane.getLayoutY());
    }
}
