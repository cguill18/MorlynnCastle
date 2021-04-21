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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.Map;

public class SceneryPaneController {
    @FXML
    private GridPane sceneryPane;

    private Game game;

    private MorlynnCastleController morlynnCastleController;

    @FXML
    public void initialize() {
        this.sceneryPane.setStyle("-fx-background-image:url(\"/res/background.png\")");
    }

    public void setGame(Game game){ this.game = game; }

    public Game getGame() { return this.game; }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void setBackground(String img){
        this.sceneryPane.setStyle("-fx-background-image:url(\"/res/pieces/" + img +"\")");
    }

    public void initScenery() {
        this.generateRoomItems();
    }

    @FXML
    public void handleClick(MouseEvent event) throws IOException {
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView){
            this.morlynnCastleController.launchCommand((InteractionView) eventTarget);
        }
    }


    //methodes pour les objets dans la piece
    public void generateRoomItems() {
        this.sceneryPane.getChildren().clear();
        Map<String, Interaction> interactions = game.getHero().getPlace().getInteractions();
        int col = 11;
        int raw = 11;
        /*for (int i = 0 ; i < col ; i++){
            for (int j = 0 ; j < raw ; j++){
                InteractionView n = new InteractionView("-fx-background-color : red",new Key("","",i,j));
                this.sceneryPane.add(n,i,j);
            }
        }*/
        for (Map.Entry<String, Interaction> objects : interactions.entrySet()) {
            if (!(objects.getValue() instanceof Hero)) {
                InteractionView inte = new InteractionView(objects.getValue());
                inte.setStyle(inte.getStyle() + "-fx-background-image:url(\"/res/armor.png\")");
                this.sceneryPane.add(inte, objects.getValue().getPosx(), objects.getValue().getPosy());
                System.out.println("" + objects.getValue().getPosx() + ";" + objects.getValue().getPosy());
            }
        }
    }

    public void removeInteractionView(InteractionView interactionView){
        this.sceneryPane.getChildren().remove(interactionView);
    }

/*    private InteractionView createKey(int id){
        return new ImageWithId(new Image("/res/key.png"),id);
    }

    private ImageWithId createUnlockChest(int id){
        return new ImageWithId(new Image("/res/unlocked_chest.png"),id);
    }

    private ImageWithId createArmor(int id){
        return new ImageWithId(new Image("/res/armor.png"),id);
    }

    private ImageWithId createNonHostileCharacter(int id){
        return new ImageWithId(new Image("/res/non_hostile_character.png"),id);
    }

    private ImageWithId createWeapon(int id){
        return new ImageWithId(new Image("/res/weapon.png"),id);
    }*/
}
