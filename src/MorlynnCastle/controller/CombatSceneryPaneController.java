package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Character;
import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.space.Interaction;
import MorlynnCastle.view.CharacterView;
import MorlynnCastle.view.InteractionView;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class CombatSceneryPaneController {

    @FXML
    private GridPane combatSceneryPane;

    private CombatPaneController combatPaneController;

    private CharacterView heroView;

    public CharacterView getHeroView() {
        return heroView;
    }

    public void setCombatPaneController(CombatPaneController combatPaneController) {
        this.combatPaneController = combatPaneController;
    }

    public void displayCharacters(Hero hero, Map<String, Character> enemies){
        final int[] i = {0};
        this.heroView = new CharacterView(hero);
        combatSceneryPane.add(heroView, 1, 2);

        enemies.forEach((name,enemy)->{
            CharacterView characterView = new CharacterView(enemy);
            characterView.setOnMouseClicked(event ->  this.combatPaneController.launchCommand(characterView));
            combatSceneryPane.add(characterView, 3+i[0]/3, 1+i[0]%3);
            i[0]++;
        });
    }

    public void removeInteractionView(CharacterView characterView){
        this.combatSceneryPane.getChildren().remove(characterView);
    }

}
