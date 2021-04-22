package MorlynnCastle.view;

import MorlynnCastle.model.space.Interaction;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


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
    
     public void lookTooltip(InteractionView interactionView, GridPane gridPane) {
        Tooltip tooltip = new Tooltip();
        tooltip.styleProperty().bind(gridPane.styleProperty());
        tooltip.setText(interactionView.getInteraction().getDescription());   
        Tooltip.install(interactionView, tooltip);
    }
    
    public void lookContainer(Interaction container){        
    /*    stage.setTitle("Contents of the chest");
        container.s(this);
*/
    }
}
