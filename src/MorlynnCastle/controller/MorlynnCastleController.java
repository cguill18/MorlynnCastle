package MorlynnCastle.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class MorlynnCastleController {

    @FXML
    private GridPane gridPaneRoot;

    @FXML
    private GridPane sceneryPane;

    @FXML
    private GridPane commandPane;

    @FXML
    private VBox characterPane;

    @FXML
    private GridPane directionPane;


    @FXML
    public void initialize() {
        sceneryPane.setStyle("-fx-background-image:url(\"/res/background.png\")");
        gridPaneRoot.styleProperty().bind(Bindings.concat("-fx-font-size:",gridPaneRoot.widthProperty().divide(60).asString(),";",gridPaneRoot.getStyle()));
    }
}
