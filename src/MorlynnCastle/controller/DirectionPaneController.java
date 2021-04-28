package MorlynnCastle.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class DirectionPaneController {


    private MorlynnCastleController morlynnCastleController;

    /***************************/
    /*********methods************/
    /****************************/


    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }


    //actions des boutons
    @FXML
    private void upAction() {
        this.morlynnCastleController.moveHero("north");
    }

    @FXML
    private void leftAction() {
        this.morlynnCastleController.moveHero("west");
    }

    @FXML
    private void rightAction() {
        this.morlynnCastleController.moveHero("east");
    }

    @FXML
    private void bottomAction() {
        this.morlynnCastleController.moveHero("south");
    }

}
