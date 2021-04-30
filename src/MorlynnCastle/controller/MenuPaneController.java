package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MenuPaneController {

    @FXML
    private VBox vBoxRoot;

    public VBox getvBoxRoot() {
        return vBoxRoot;
    }

    /**
     * Lance le jeu avec l'introduction
     */
    @FXML
    public void start() {
        Game game = new Game();
        String text ="\n"+ game.historyText() + "\n\n\n\n\n\n\n\n";
        TextArea textArea = new TextArea(text);
        textArea.setWrapText(true);
        textArea.styleProperty().bind(Bindings.concat("-fx-font-size: 28; -fx-font-family: 'Book Antiqua';"));
        this.vBoxRoot.getScene().setRoot(textArea);
        game.initGame();
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(10),
                        new KeyValue(textArea.scrollTopProperty(),300)));
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(3));
        fade.setToValue(0);
        fade.setNode(textArea);
        animation.setOnFinished(actionEvent -> fade.play());
        fade.setOnFinished(actionEvent -> this.launchGame(game,textArea.getScene()));
        animation.play();
    }

    /**
     * Génère l'interface de sauvegarde puis lance le jeu à partir de la sauvegarde sélectionnée
     */
    @FXML
    public void load() {
        Game game = new Game();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SavePane.fxml"));
            Parent root = loader.load();
            SavePaneController savePaneController = loader.getController();
            savePaneController.fillList(game.getSaveFiles());
            savePaneController.getBottomButton().setText("Load");
            Stage saveStage = new Stage();
            savePaneController.getBottomButton().setOnAction(event -> {
                String savename = savePaneController.getSelection();
                if (savename != null) {
                    game.load(savename);
                    saveStage.close();
                    this.launchGame(game,this.vBoxRoot.getScene());
                }
            });
            saveStage.setTitle("Load");
            saveStage.initOwner(this.vBoxRoot.getScene().getWindow());
            saveStage.setAlwaysOnTop(true);
            saveStage.initModality(Modality.WINDOW_MODAL);
            saveStage.setScene(new Scene(root));
            saveStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void quit() {
        Platform.exit();
    }

    /**
     * Lance le jeu à partir du Game en paramètres avec une animation de fondu
     * @param game
     * @param scene
     */
    public void launchGame(Game game, Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MorlynnCastle.fxml"));
            Parent root = loader.load();
            MorlynnCastleController controller = loader.getController();
            controller.initGame(game);
            controller.setMenuPaneController(this);
            controller.updateView();
            scene.setRoot(root);
            root.setOpacity(0);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), root);
            fadeTransition.setFromValue(0.);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** On bind la police pour qu'elle se redimensionne avec la fenêtre */
    @FXML
    public void initialize() {
        this.vBoxRoot.styleProperty().bind(Bindings.concat("-fx-font-size:", vBoxRoot.widthProperty().divide(60).asString(), ";", vBoxRoot.getStyle()));
    }
}
