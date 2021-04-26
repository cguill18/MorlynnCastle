
package MorlynnCastle.controller;

import MorlynnCastle.model.item.Container;
import MorlynnCastle.model.item.Item;
import MorlynnCastle.view.InteractionView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ContainerPaneController implements Initializable {

    @FXML
    private GridPane gridPane;


    private MorlynnCastleController morlynnCastleController;

    private Container container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String styleGeneral = "-fx-background-position: center; -fx-background-size: contain ;";
        this.gridPane.setStyle(styleGeneral + "-fx-background-image:url(\"/res/wood.jpg\")");
    }

    public void launchCommand(InteractionView interactionView){
        switch (this.morlynnCastleController.getCommand()){
            case TAKE:
                this.morlynnCastleController.takeFromContainer(this.container, (Item) interactionView.getInteraction());
                    this.removeInteractionView(interactionView);
                break;
        }

    }

    @FXML
    public void handleClick(MouseEvent event)  {
        EventTarget eventTarget = event.getTarget();
        if (eventTarget instanceof InteractionView) {
            this.launchCommand((InteractionView) eventTarget);
        }
    }

    @FXML
    public void takeAll(){
        this.morlynnCastleController.takeAllFromContainer(this.container);
        this.gridPane.getChildren().clear();
    }

    public void setContainerLooking(Container container) {
        this.container = container;
    }

    public void removeInteractionView(InteractionView interactionView) {
        this.gridPane.getChildren().remove(interactionView);
    }


    public void setMorlynnCastleController(MorlynnCastleController morlynnCastleController) {
        this.morlynnCastleController = morlynnCastleController;
    }


    public void displayContainer(Map<String, Item> container) {
        gridPane.getChildren().clear();
        final int[] i = {0};
        container.forEach((name, item) -> {
            InteractionView interactionView = new InteractionView(item);
            interactionView.setStyle(interactionView.getStyle() + "-fx-background-image:url(\"/res/" + item.getImage() + "\")");
            gridPane.add(interactionView, i[0] % this.gridPane.getColumnConstraints().size(), i[0] / this.gridPane.getColumnConstraints().size());
            i[0]++;
        });
    }


}
