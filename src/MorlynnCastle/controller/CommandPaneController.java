package MorlynnCastle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CommandPaneController {

    @FXML
    private Button takeButtonCommand;

    @FXML
    private Button attackButtonCommand;

    @FXML
    private Button lookButtonCommand;

    @FXML
    private Button useButtonCommand;

    @FXML
    private Button equipButtonCommand;

    @FXML
    private Button talkButtonCommand;

    private Command command;

    public Command getCommand() {
        return command;
    }

    @FXML
    public void take(){
        this.command = Command.TAKE;
    }

    @FXML
    public void look(){
        this.command = Command.LOOK;
    }

    @FXML
    public void use(){
        this.command = Command.USE;
    }

    @FXML
    public void equip(){
        this.command = Command.EQUIP;
    }

    @FXML
    public void attack(){
        this.command = Command.ATTACK;
    }

    @FXML
    public void talk(){
        this.command = Command.TALK;
    }
}
