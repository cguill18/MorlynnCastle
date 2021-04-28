package MorlynnCastle.controller;

import javafx.fxml.FXML;

public class CommandPaneController {

    private Command command;

    public Command getCommand() {
        return command;
    }

    public void resetCommand() {
        this.command = null;
    }

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
