package MorlynnCastle.view;

import MorlynnCastle.model.characters.Character;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.TilePane;

public class CharacterView extends TilePane {

    private InteractionView<Character> interactionView;

    private DoubleProperty currentHp = new SimpleDoubleProperty();

    private DoubleProperty maxHp = new SimpleDoubleProperty();

    private ProgressBar hpBar;

    public CharacterView(Character character) {
        super();
        this.interactionView = new InteractionView(character);
        this.currentHp.set(character.getCurrentHealthPoints());
        this.maxHp.set(character.getMaxHealthPoints());
        this.setAlignment(Pos.CENTER);
        this.setOrientation(Orientation.VERTICAL);
        this.setVgap(2);
        Label label = new Label(character.getName());
        this.hpBar = new ProgressBar();
        this.hpBar.progressProperty().bind(Bindings.divide(currentHp,maxHp));
        this.getChildren().addAll(label,hpBar,interactionView);
    }

    public InteractionView<Character> getInteractionView() {
        return interactionView;
    }

    public void updateHp(){
        this.currentHp.set(this.interactionView.getInteraction().getCurrentHealthPoints());
        this.maxHp.set(this.interactionView.getInteraction().getMaxHealthPoints());
    }
}
