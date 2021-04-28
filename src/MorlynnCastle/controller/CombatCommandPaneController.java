package MorlynnCastle.controller;

import javafx.fxml.FXML;

public class CombatCommandPaneController {

    private CombatPaneController combatPaneController;

    private Command command;

    public Command getCommand() {
        return this.command;
    }

    public void resetCommand() {
        this.command = null;
    }

    public void setCombatPaneController(CombatPaneController combatPaneController) {
        this.combatPaneController = combatPaneController;
    }

    @FXML
    public void attack() {
        this.command = Command.ATTACK;
    }

    @FXML
    public void flee() {
        this.combatPaneController.flee();
    }
}
