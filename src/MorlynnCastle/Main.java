package MorlynnCastle;

import MorlynnCastle.controller.MorlynnCastleController;
import MorlynnCastle.model.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MenuPane.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root,750,500));
        stage.setTitle("Morlynn Castle");
        stage.show();
    }

    public static void main(String[] args){ launch(args); }
}
