package MorlynnCastle.controller;

import javafx.fxml.FXML;

public class CommandPaneController {

    private Command command;

    /*récupère la commande*/
    public Command getCommand() {
        return command;
    }

    /*remet à null command*/
    public void resetCommand() {
        this.command = null;
    }

    /*sauvegarde la commande effectuée*/
    @FXML
    public void take() {
        this.command = Command.TAKE;
    }

    @FXML
    public void look() {
        this.command = Command.LOOK;
    }

    @FXML
    public void use() {
        this.command = Command.USE;
    }

    @FXML
    public void equip() {
        this.command = Command.EQUIP;
    }

    @FXML
    public void attack() {
        this.command = Command.ATTACK;
    }

    @FXML
    public void talk() {
        this.command = Command.TALK;
    }
}
