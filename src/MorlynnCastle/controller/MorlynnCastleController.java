package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class MorlynnCastleController {

    @FXML
    private GridPane gridPaneRoot;

    @FXML
    private GridPane sceneryPane;

    @FXML
    private SceneryPaneController sceneryPaneController;

    @FXML
    private GridPane commandPane;

    @FXML
    private VBox characterPane;

    @FXML
    private GridPane directionPane;

    private Game game;

    @FXML
    public void initialize() {
        this.game = new Game();
        this.game.initGame();
        this.sceneryPaneController.setGame(this.game);
        this.sceneryPaneController.initScenery();
        gridPaneRoot.styleProperty().bind(Bindings.concat("-fx-font-size:",gridPaneRoot.widthProperty().divide(60).asString(),";",gridPaneRoot.getStyle()));
    }

    public Game getGame() { return this.game; }

    public void setGame(Game game){ this.game = game; }



}
