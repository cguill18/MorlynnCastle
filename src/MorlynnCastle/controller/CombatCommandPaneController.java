package MorlynnCastle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CombatCommandPaneController {

    @FXML
    private Button attackButton;

    @FXML
    private Button fleeButton;

    private CombatPaneController combatPaneController;

    private Command command;

    public Command getCommand() {
        return command;
    }

    public void setCombatPaneController(CombatPaneController combatPaneController) {
        this.combatPaneController = combatPaneController;
    }

    @FXML
    public void attack(){
        this.command = Command.ATTACK;
    }

    @FXML
    public void flee(){
        this.combatPaneController.flee();
    }
}
