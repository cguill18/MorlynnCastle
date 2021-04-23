package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Combat;
import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.characters.NonPlayerCharacter;
import MorlynnCastle.view.InteractionView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class CombatPaneController {

    @FXML
    private GridPane combatCommandPane;

    @FXML
    private CombatCommandPaneController combatCommandPaneController;

    @FXML
    private TextArea textArea;

    @FXML
    private GridPane combatSceneryPane;

    @FXML
    private CombatSceneryPaneController combatSceneryPaneController;

    private MorlynnCastleController morlynnCastleController;

    public Combat combat;

    public Hero hero;

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void initCombat(Hero hero) {
        this.hero = hero;
        this.combat = hero.getOngoingCombat();
        this.combatSceneryPaneController.setCombatPaneController(this);
        this.combatCommandPaneController.setCombatPaneController(this);
        this.combatSceneryPaneController.displayCharacters(this.hero,this.combat.getEnemies());
    }

    public void attackCharacter(InteractionView interactionView) {
        if (this.combatCommandPaneController.getCommand() == Command.ATTACK) {
            NonPlayerCharacter npc = (NonPlayerCharacter) interactionView.getInteraction();
            int damage = this.hero.attack(npc);
            String text = "You attack "+ npc.getName() +".\n";
            if (damage == 0)
                text = text + "Miss !\nHe takes no damage.";
            else {
                text = text + "You hit him.\nHe take " + damage +" points of damage.\n";
                if (!npc.isAlive())
                    text = text + "You killed him.";
            }
            this.textArea.setText(this.textArea.getText() + text);
            if (!(this.combat.enemiesStillAlive() && this.hero.isAlive())) {
                this.endCombat();
            } else {
                this.combatTurn();
            }
        }
    }

    public void flee() {
        this.textArea.setText(this.textArea.getText() + "You flee the fight.\n");
        this.endCombat();
    }

    public void combatTurn(){
        this.combat.getEnemies().values().stream().filter(enemy -> enemy.isAlive()).forEach(enemy -> {
            int damage = this.combat.enemyTurn(hero, enemy);
            String text = enemy.getName() + "attacks you.\n";
            if (damage == 0)
                text = text + "Miss !\nYou take no damage.";
            else {
                text = text + "He hits you.\nYou take " + damage +" points of damage.\n";
                if (!this.hero.isAlive())
                    text = text + "You died.";
            }
            this.textArea.setText(this.textArea.getText() + text);
        });
    }

    public void endCombat() {
        Alert alert = new Alert(AlertType.NONE, "Fin du combat", ButtonType.OK);
        alert.showAndWait();
        this.hero.setOngoingCombat(null);
        this.combatCommandPane.getScene().setRoot(this.morlynnCastleController.getBorderPaneRoot());
    }

}
