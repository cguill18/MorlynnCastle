package MorlynnCastle.view;

import MorlynnCastle.model.space.Interaction;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


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
    
    public void lookContainer(Interaction container){        
    /*    stage.setTitle("Contents of the chest");
        container.s(this);
*/
    }
}
