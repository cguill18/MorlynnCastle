
package MorlynnCastle.controller;

import MorlynnCastle.model.item.Container;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ContainerPaneController {

    /** vue du coffre */
    @FXML
    private GridPane gridPane;

    private MorlynnCastleController morlynnCastleController;

    /** coffre du modèle */
    private Container container;

    /** initialisation du coffre */
    @FXML
    public void initialize() {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: contain ;";
        this.gridPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/wood.jpg\")");
    }

    /** prendre les objets du coffre avec le bouton TAKE de la vue principale */
    public void launchCommand(InteractionView<Item> interactionView) {
        if (this.morlynnCastleController.getCommand() == Command.TAKE) {
            this.morlynnCastleController.takeFromContainer(this.container, interactionView.getInteraction());
            this.removeInteractionView(interactionView);
        }
        this.morlynnCastleController.resetCommand();
    }

    /** permet de prendre tout le contenu du coffre */
    @FXML
    public void takeAll() {
        this.morlynnCastleController.takeAllFromContainer(this.container);
        this.gridPane.getChildren().clear();
    }

    /** sélectionner le coffre observé dans le model */
    public void setContainerLooking(Container container) {
        this.container = container;
    }

    /** Supprimer les images représentant les objets dans le coffre */
    public void removeInteractionView(InteractionView<Item> interactionView) {
        this.gridPane.getChildren().remove(interactionView);
    }


    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }

    /** afficher le contenu du coffre et ajoute les évènements*/
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
