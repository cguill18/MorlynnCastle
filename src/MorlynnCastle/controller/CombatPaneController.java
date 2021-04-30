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

    /* --------------------------- attributs ---------------------------------- */
    /** elements de la vue */

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

    /** Attribut pour la communication avec le contrôleur général  */
    private MorlynnCastleController morlynnCastleController;

    /** Attributs du modèle */

    public Combat combat;

    public Hero hero;

    /* --------------------------------------- methodes -------------------------------------------*/

    /** On bind la police pour qu'elle se redimensionne avec la fenêtre */
    @FXML
    public void initialize() {
        gridPaneRoot.styleProperty().bind(Bindings.concat("-fx-font-size:", gridPaneRoot.widthProperty().divide(60).asString(), ";", gridPaneRoot.getStyle()));

    }

    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    /** Initialise les attributs nécessaires au déroulement du combat ainsi que la communication entre les différents contrôleurs. */
    public void initCombat(Hero hero) {
        this.hero = hero;
        this.combat = hero.getOngoingCombat();
        this.combatSceneryPaneController.setCombatPaneController(this);
        this.combatCommandPaneController.setCombatPaneController(this);
        this.combatSceneryPaneController.displayCharacters(this.hero, this.combat.getEnemies());
    }

    /**
     * Méthode appelé lorsqu'on clique sur un CharacterView.
     * Lance la commande retenu par le commandpanecontroller, s'il y en a une puis la remet à null.
     * Pour l'instant, seulement la commande ATTACK fonctionne comme cela.
     * */
    public void launchCommand(CharacterView characterView) {
        if (this.combatCommandPaneController.getCommand() != null) {
            if (this.combatCommandPaneController.getCommand() == Command.ATTACK) {
                this.attackCharacter(characterView);
            }
            this.combatCommandPaneController.resetCommand();
        }
    }

    /**
     * Attaque un personnage non joueur
     * Réalise l'affichage correspondant dans le textArea
     * Puis lance le tour des ennemis et vérfie si le combat est fini
     * @param characterView
     */
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

    /**
     *  Mets fin au combat si la fuite a fonctionné
     *  Sinon, lance le tour des ennemis
     */
    public void flee() {
        if (this.hero.flee()) {
            this.textArea.appendText("You flee the fight.\n");
            this.endCombat();
        }
        else {
            this.textArea.appendText("You did not managed to run away.\n");
            this.combatTurn();
            if (!(this.combat.enemiesStillAlive() && this.hero.isAlive()))
                this.endCombat();
        }
    }

    /**
     * Tour des ennemis
     * Chaque personnage vivant attaque le héros
     */
    public void combatTurn() {
        this.combat.getEnemies().values().stream().filter(Character::isAlive).forEach(enemy -> {
            int damage = this.combat.enemyTurn(hero, enemy);
            String text = enemy.getName() + " attacks you.\n";
            if (damage == 0)
                text = text + "Miss !\nYou take no damage.";
            else {
                text = text + "He hits you.\nYou take " + damage + " points of damage.\n";
                if (!this.hero.isAlive())
                    text = text + "You died.";
            }
            this.textArea.appendText(text+"\n");
            this.combatSceneryPaneController.getHeroView().updateHp();
        });
    }

    /**
     * Sort du combat après avoir fermé un Alert
     */
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
