package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Character;
import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.space.Interaction;
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

    public void setCombatPaneController(CombatPaneController combatPaneController) {
        this.combatPaneController = combatPaneController;
    }

    @FXML
    public void handleClick(MouseEvent event){
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView){
            this.combatPaneController.attackCharacter((InteractionView) eventTarget);
        }
    }

    public void displayCharacters(Hero hero, Map<String, Character> enemies){
        final int[] i = {0};
        InteractionView heroView = new InteractionView(hero);
        heroView.setStyle(heroView.getStyle()+"-fx-background-image:url(\"/res/hero.png\")");
        combatSceneryPane.add(heroView, 1, 2);

        enemies.forEach((name,enemy)->{
            InteractionView interactionView = new InteractionView(enemy);
            interactionView.setStyle(interactionView.getStyle()+"-fx-background-image:url(\"/res/"+ enemy.getImage() +"\")");
            combatSceneryPane.add(interactionView, 3+i[0]%3, 1+i[0]/3);
            i[0]++;
        });

    }
}
