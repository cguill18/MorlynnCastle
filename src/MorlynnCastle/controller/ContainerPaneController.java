
package MorlynnCastle.controller;

import MorlynnCastle.model.item.Container;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ContainerPaneController {

    @FXML
    private GridPane gridPane;

    private MorlynnCastleController morlynnCastleController;

    private Container container;

    @FXML
    public void initialize() {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: contain ;";
        this.gridPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/wood.jpg\")");
    }

    public void launchCommand(InteractionView<Item> interactionView) {
        if (this.morlynnCastleController.getCommand() == Command.TAKE) {
            this.morlynnCastleController.takeFromContainer(this.container, interactionView.getInteraction());
            this.removeInteractionView(interactionView);
        }
        this.morlynnCastleController.resetCommand();
    }

    @FXML
    public void takeAll() {
        this.morlynnCastleController.takeAllFromContainer(this.container);
        this.gridPane.getChildren().clear();
    }

    public void setContainerLooking(Container container) {
        this.container = container;
    }

    public void removeInteractionView(InteractionView<Item> interactionView) {
        this.gridPane.getChildren().remove(interactionView);
    }


    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }


    public void displayContainer(Map<String, Item> container) {
        gridPane.getChildren().clear();
        final int[] i = {0};
        container.forEach((name, item) -> {
            InteractionView<Item> interactionView = new InteractionView<>(item);
            interactionView.setOnMouseClicked(event -> this.launchCommand(interactionView));
            gridPane.add(interactionView, i[0] % this.gridPane.getColumnConstraints().size(), i[0] / this.gridPane.getColumnConstraints().size());
            i[0]++;
        });
    }


}
