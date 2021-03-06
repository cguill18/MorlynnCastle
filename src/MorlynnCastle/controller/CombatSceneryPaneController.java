package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.characters.NonPlayerCharacter;
import MorlynnCastle.view.CharacterView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.Map;


public class CombatSceneryPaneController {

    /* --------------------------------------- attributs -------------------------------------------*/
    @FXML
    private GridPane combatSceneryPane;

    private CombatPaneController combatPaneController;

    private CharacterView heroView;

    /* --------------------------------------- methodes -------------------------------------------*/

    public CharacterView getHeroView() {
        return heroView;
    }

    public void setCombatPaneController(CombatPaneController combatPaneController) {
        this.combatPaneController = combatPaneController;
    }

    /**
     * Génère les images des personnages et ajoute l'évènement cliquable
     * @param hero le personnage joueur
     * @param enemies la map des personnages hostiles
     */
    public void displayCharacters(Hero hero, Map<String, NonPlayerCharacter> enemies) {
        final int[] i = {0};
        this.heroView = new CharacterView(hero);
        combatSceneryPane.add(heroView, 1, 2);

        enemies.forEach((name, enemy) -> {
            CharacterView characterView = new CharacterView(enemy);
            characterView.setOnMouseClicked(event -> this.combatPaneController.launchCommand(characterView));
            combatSceneryPane.add(characterView, 3 + i[0] / 3, 1 + i[0] % 3);
            i[0]++;
        });
    }

    public void removeInteractionView(CharacterView characterView) {
        this.combatSceneryPane.getChildren().remove(characterView);
    }

}
