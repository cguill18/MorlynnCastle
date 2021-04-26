package MorlynnCastle.controller;

import MorlynnCastle.model.game.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPaneController {

    @FXML
    private VBox vBoxRoot;

    public VBox getvBoxRoot() {
        return vBoxRoot;
    }

    @FXML
    public void start() {
        Game game = new Game();
        game.initGame();
        this.launchGame(game);
    }

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
                    this.launchGame(game);
                    saveStage.close();
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

    public void launchGame(Game game) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MorlynnCastle.fxml"));
            Parent root = loader.load();
            MorlynnCastleController controller = loader.getController();
            controller.initGame(game);
            controller.setMenuPaneController(this);
            controller.updateView();
            this.vBoxRoot.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
