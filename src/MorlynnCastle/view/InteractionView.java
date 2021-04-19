package MorlynnCastle.view;

import MorlynnCastle.model.space.Interaction;
import javafx.scene.layout.FlowPane;


public class InteractionView extends FlowPane {

    private Interaction interaction;


    //deux constructeurs
    public InteractionView(Interaction interaction){
        super();
        this.interaction = interaction;
        this.setStyle("-fx-background-position: center; -fx-background-size: contain ; -fx-background-repeat: no-repeat;");
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
