package MorlynnCastle.controller;

import MorlynnCastle.model.characters.Character;
import MorlynnCastle.model.characters.Combat;
import MorlynnCastle.model.characters.Hero;
import MorlynnCastle.model.characters.NonPlayerCharacter;
import MorlynnCastle.view.CharacterView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class CombatPaneController {

    @FXML
    private GridPane gridPaneRoot;

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

    @FXML
    public void initialize() {
        gridPaneRoot.styleProperty().bind(Bindings.concat("-fx-font-size:", gridPaneRoot.widthProperty().divide(60).asString(), ";", gridPaneRoot.getStyle()));

    }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    public void initCombat(Hero hero) {
        this.hero = hero;
        this.combat = hero.getOngoingCombat();
        this.combatSceneryPaneController.setCombatPaneController(this);
        this.combatCommandPaneController.setCombatPaneController(this);
        this.combatSceneryPaneController.displayCharacters(this.hero, this.combat.getEnemies());
    }

    public void launchCommand(CharacterView characterView) {
        if (this.combatCommandPaneController.getCommand() != null) {
            switch (this.combatCommandPaneController.getCommand()) {
                case ATTACK:
                    this.attackCharacter(characterView);
                    break;
                case FLEE:
                    this.flee();
                    break;
            }
            this.combatCommandPaneController.resetCommand();
        }
    }

    public void attackCharacter(CharacterView characterView) {
        NonPlayerCharacter npc = (NonPlayerCharacter) characterView.getInteractionView().getInteraction();
        int damage = this.hero.attack(npc);
        String text = "You attack " + npc.getName() + ".\n";
        if (damage == 0)
            text = text + "Miss !\nHe takes no damage.";
        else {
            text = text + "You hit him.\nHe take " + damage + " points of damage.\n";
            if (!npc.isAlive()) {
                text = text + "You killed him.";
                this.combatSceneryPaneController.removeInteractionView(characterView);
            }
        }
        this.textArea.appendText(text);
        characterView.updateHp();
        this.combatTurn();

        if (!(this.combat.enemiesStillAlive() && this.hero.isAlive()))
            this.endCombat();
    }

    public void flee() {
        this.textArea.appendText("You flee the fight.\n");
        this.endCombat();
    }

    public void combatTurn() {
        this.combat.getEnemies().values().stream().filter(Character::isAlive).forEach(enemy -> {
            int damage = this.combat.enemyTurn(hero, enemy);
            String text = enemy.getName() + "attacks you.\n";
            if (damage == 0)
                text = text + "Miss !\nYou take no damage.";
            else {
                text = text + "He hits you.\nYou take " + damage + " points of damage.\n";
                if (!this.hero.isAlive())
                    text = text + "You died.";
            }
            this.textArea.appendText(text);
            this.combatSceneryPaneController.getHeroView().updateHp();
        });
    }

    public void endCombat() {
        Alert alert = new Alert(AlertType.NONE, "Fin du combat", ButtonType.OK);
        alert.showAndWait();
        this.hero.setOngoingCombat(null);
        this.combat = null;
        this.hero = null;
        this.morlynnCastleController.updateHp();
        this.combatCommandPane.getScene().setRoot(this.morlynnCastleController.getBorderPaneRoot());
        this.morlynnCastleController.checkEnd();
    }

}
