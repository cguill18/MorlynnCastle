package MorlynnCastle.view;

import MorlynnCastle.model.characters.Character;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CharacterView extends VBox {

    private InteractionView<Character> interactionView;

    private DoubleProperty currentHp = new SimpleDoubleProperty();

    private DoubleProperty maxHp = new SimpleDoubleProperty();


    public CharacterView(Character character) {
        super();
        this.interactionView = new InteractionView<>(character);
        this.currentHp.set(character.getCurrentHealthPoints());
        this.maxHp.set(character.getMaxHealthPoints());
        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
        Label label = new Label(character.getName());
        label.setStyle("-fx-font-size: 0.7em");
        ProgressBar hpBar = new ProgressBar();
        hpBar.getStylesheets().add("MorlynnCastle/view/hpbar.css");
        hpBar.progressProperty().bind(Bindings.divide(currentHp, maxHp));
        hpBar.prefWidthProperty().bind(this.widthProperty().multiply(0.6));
        hpBar.prefHeightProperty().bind(this.heightProperty().multiply(0.1));
        VBox.setVgrow(this.interactionView, Priority.ALWAYS);
        this.getChildren().addAll(label, hpBar, interactionView);
    }

    public InteractionView<Character> getInteractionView() {
        return interactionView;
    }

    public void updateHp() {
        this.currentHp.set(this.interactionView.getInteraction().getCurrentHealthPoints());
        this.maxHp.set(this.interactionView.getInteraction().getMaxHealthPoints());
    }
}
