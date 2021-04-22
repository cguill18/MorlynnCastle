package MorlynnCastle.view;

import MorlynnCastle.controller.ContainerPaneController;
import MorlynnCastle.model.space.Interaction;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class InteractionView extends FlowPane {

    private Interaction interaction;
    
    private Tooltip tooltip;


    //deux constructeurs
    public InteractionView(Interaction interaction){
        super();
        this.interaction = interaction;
        this.setStyle("-fx-background-position: center; -fx-background-size: contain ; -fx-background-repeat: no-repeat;");
        this.tooltip = new Tooltip();
        tooltip.setText(this.getInteraction().getDescription());
        Tooltip.install(this, tooltip);
    }

    public InteractionView(String url, Interaction interaction) {
        super();
        this.interaction = interaction;
        this.setStyle(url);
    }

    //methodes
    public Interaction getInteraction() { return this.interaction; }

    public void setInteraction(Interaction interaction) { this.interaction = interaction; }

    public void setImage(String url) { this.setStyle(url);}
    
}
