package MorlynnCastle.view;

import MorlynnCastle.model.space.Interaction;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;


public class InteractionView<I extends Interaction> extends FlowPane {

    private I interaction;

    private Tooltip tooltip;


    //deux constructeurs
    public InteractionView(I interaction){
        super();
        this.interaction = interaction;
        this.setStyle("-fx-background-position: center; -fx-background-size: contain ; -fx-background-repeat: no-repeat;-fx-background-image: url(\"/res/"+ interaction.getImage() +"\");");
        this.tooltip = new Tooltip();
        tooltip.setText(this.getInteraction().getDescription());
        Tooltip.install(this, tooltip);
    }

    public InteractionView(String url, I interaction) {
        super();
        this.interaction = interaction;
        this.setStyle(url);
    }

    //methodes
    public I getInteraction() { return this.interaction; }

    public void setInteraction(I interaction) { this.interaction = interaction; }

    public void setImage(String url) { this.setStyle(url);}
    
}
