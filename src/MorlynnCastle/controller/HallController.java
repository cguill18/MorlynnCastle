package MorlynnCastle.controller;

import MorlynnCastle.model.item.Item;
import MorlynnCastle.model.space.World;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HallController {

    private World world;

    @FXML
    private ImageView chest;

    @FXML
    private ImageView armor;

    @FXML
    private ImageView old_man;

    @FXML
    private ImageView weapon;

    @FXML
    public void chooseObject(MouseEvent event) {
        ImageView img = (ImageView) event.getTarget();
    }

    public void setWorld(World world){
        this.world = world;
    }

    @FXML
    public void initialize(){

    }

}
